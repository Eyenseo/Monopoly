package core;

import objects.Player;
import objects.card.Jailbait;
import objects.map.FieldCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.Street;
import objects.value.action.*;

import java.util.ArrayList;
import java.util.HashMap;

//JAVADOC
//TODO find a better / less costly way to have "something like" a Thread
public class ActionThread extends Thread {
	private final Player                                          currentPlayer;
	private       HashMap<Integer, Player>                        playerHashMap;
	private       ActionData                                      actionData;
	private       ServerOperator                                  serverOperator;
	private       FieldCircularList                               jail;
	private       Thread                                          turnThread;
	private       HashMap<Integer, HashMap<String, ActionThread>> actions;

	//JAVADOC
	public ActionThread(HashMap<Integer, Player> playerHashMap, Player currentPlayer, ActionData actionData,
	                    ServerOperator serverOperator, FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		this.playerHashMap = playerHashMap;
		this.currentPlayer = currentPlayer;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	@Override public void run() {
		Player user = playerHashMap.get(actionData.getUserId());

		if(actionData instanceof TurnData) {
			turnAction(user, (TurnData) actionData);
		} else if(actionData instanceof BuyData) {
			buyAction(user, (BuyData) actionData);
		} else if(actionData instanceof HaggleData) {
			haggleAction(user, (HaggleData) actionData);
		} else if(actionData instanceof MortgageData) {
			mortgageAction(user, (MortgageData) actionData);
		} else if(actionData instanceof PlayerStatusData) {
			playerStatusAction(user, (PlayerStatusData) actionData);
		}

		if(actions.containsKey(actionData.getUserId())) {
			actions.get(actionData.getUserId()).remove(actionData.getId());
		}
	}

	//JAVADOC
	private void turnAction(Player user, TurnData data) {
		synchronized(turnThread) {
			if(data.isTurnAction()) {
				if(user.getThrewDice() < 3) {
					user.move();
				} else {
					user.setPosition(jail);
					user.setInJail(true);
					user.setTurnEnd(true);
					turnThread.notify();
				}
			} else {
				user.setTurnEnd(true);
				turnThread.notify();
			}
		}
	}

	//JAVADOC
	private void buyAction(Player user, BuyData actionData) {
		if(user.getPosition() instanceof PurchasableCircularList) { //TODO check if this is enough
			if(actionData.isUpgrade()) {
				((Street) user.getPosition()).nextStage();
			} else {
				user.buyPurchasable((PurchasableCircularList) user.getPosition());
			}
		}
	}

	//JAVADOC
	private void haggleAction(Player user, HaggleData actionData) {
		Player seller;

		System.out.println("User Id: " + actionData.getUserId() + " with Seller Id: " + actionData.getSellerId());

		switch(actionData.getHaggleState()) {
			case ESTABLISH:
				seller = playerHashMap.get(actionData.getSellerId());
				if(user.getTrading() == -1 && seller.getTrading() == -1) {
					user.setTrading(seller.getPlayerId());
					seller.setTrading(user.getPlayerId());
					actionData.setHaggleState(HaggleData.HaggleState.ESTABLISHED);
				} else {
					actionData.setHaggleState(HaggleData.HaggleState.DECLINE);
				}
				serverOperator.sendHaggleData(actionData);
				break;

			// case ESTABLISHED: //Will never happen - this state will just appear on the client
			// break;

			case REQUEST:
				serverOperator.sendHaggleData(actionData);
				break;

			case OFFER:
				serverOperator.sendHaggleData(actionData);
				break;

			case ACCEPT:
				seller = playerHashMap.get(actionData.getSellerId());
				// trade money
				int playerPay = actionData.getSellerMoney() - actionData.getUserMoney();
				if(playerPay != 0) {
					if(playerPay < 0) {
						playerPay *= -1;
						seller.pay(playerPay);
						user.addMoney(playerPay);
					} else {
						seller.addMoney(playerPay);
						user.pay(playerPay);
					}
				}
				// trade purchasables
				for(int id : actionData.getSellerFieldIds()) {
					for(PurchasableCircularList purchasable : user.getProperties()) {
						if(purchasable.getFieldNumber() == id) {
							purchasable.setOwner(seller);
							break;
						}
					}
				}
				for(int id : actionData.getUserFieldIds()) {
					for(PurchasableCircularList purchasable : seller.getProperties()) {
						if(purchasable.getFieldNumber() == id) {
							purchasable.setOwner(user);
							break;
						}
					}
				}
				serverOperator.sendHaggleData(actionData);
				user.setTrading(-1);
				seller.setTrading(-1);
				break;

			case DECLINE:
				seller = playerHashMap.get(actionData.getSellerId());

				user.setTrading(-1);
				if(seller != null) {
					seller.setTrading(-1);
				}
				serverOperator.sendHaggleData(actionData);
				break;
		}
	}

	//JAVADOC
	private void mortgageAction(Player user, MortgageData actionData) {
		ArrayList<PurchasableCircularList> purchasableCircularListArrayList = user.getProperties();
		for(PurchasableCircularList purchasable : purchasableCircularListArrayList) {
			if(purchasable.getFieldNumber() == actionData.getFieldId()) {
				purchasable.setInMortgage(!purchasable.isInMortgage());
				break;
			}
		}
	}

	//JAVADOC
	private void playerStatusAction(Player user, PlayerStatusData actionData) {
		switch(actionData.getStatus()) {
			case GIVEUP:
				removePlayer(user, actionData);
				break;
			case FINANCIAL:
				for(Thread thread : actions.get(actionData.getUserId()).values()) {
					synchronized(thread) {
						if(thread.getState() == Thread.State.WAITING) {
							thread.notify(); //notify all waiting threads
						}
					}
				}
				break;
		}
	}

	//JAVADOC
	private void removePlayer(Player user, PlayerStatusData actionData) {
		//Cancel all trades that the player had - upon receiving player give up the client will remove the player
		// anyway
		for(ActionThread thread : actions.get(actionData.getUserId()).values()) {
			synchronized(thread) {
				if(thread.getActionData() instanceof HaggleData) {
					HaggleData haggleData = (HaggleData) thread.getActionData();
					haggleData.setHaggleState(HaggleData.HaggleState.DECLINE);
					serverOperator.sendHaggleData(haggleData);
				}
			}
		}
		//Set player to giveUp - a player event will be fired an the clients will be informed
		user.setGiveUp(true);

		//Remove from Server
		serverOperator.removeDestination(actionData.getUserId());

		//Set purchasables to be buyable again
		while(user.getProperties().size() != 0) {
			user.getProperties().get(0).setOwner(null);
		}

		//Put all cards back on the Cardstack
		for(Jailbait card : user.getJailbait()) {
			card.putBack();
		}

		//remove Player
		playerHashMap.remove(user.getPlayerId());

		//remove action HashMap
		actions.remove(user.getPlayerId());

		//move to the next player if the removed player is currently at turn
		if(currentPlayer.equals(user)) {
			synchronized(turnThread) {
				turnThread.notify();
			}
		}
	}

	//JAVADOC
	public ActionData getActionData() {
		return actionData;
	}
}
