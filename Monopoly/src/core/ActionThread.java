package core;

import objects.Player;
import objects.card.Jailbrake;
import objects.map.FieldCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.Street;
import objects.value.action.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The ActionThread class will execute the given request, in form of a ActionData object from a client
 */
//TODO replace / use with with threadpools
public class ActionThread extends Thread {
	private final Player                                          currentPlayer;
	private       HashMap<Integer, Player>                        playerHashMap;
	private       ActionData                                      actionData;
	private       ServerOperator                                  serverOperator;
	private       FieldCircularList                               jail;
	private       Thread                                          turnThread;
	private       HashMap<Integer, HashMap<String, ActionThread>> actions;

	/**
	 * @param playerHashMap  The value determines the HashMap from Monopoly where all players are stored
	 * @param currentPlayer  The value determines the player who is currently at turn
	 * @param actionData     The value determines the actionData that contain the request, that shall be done by the
	 *                       server
	 * @param serverOperator The value determines the ServerOperator, that is connected with all clients
	 * @param jail           The value determines the jail field
	 * @param turnThread     The value determines the turnThread, that sets the active player
	 * @param actions        The value determines the HashMap in which all running threads are stored in
	 */
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

	/**
	 * The method is executed by the thread upon start. Based on the type of the ActionData the respective method
	 * will be
	 * called
	 */
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

	/**
	 * The method will check if the player has stopped the turn or can still move forward
	 *
	 * @param user The value determines the Player the TurnData is from
	 * @param data The value determines the TurnData that needs to be processed
	 */
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

	/**
	 * The method will check if the player wants to update a Street or if he wants to buy a purchasable and executes the request
	 *
	 * @param user       The value determines the Player the BuyData is from
	 * @param actionData The value determines the BuyData that needs to be processed
	 */
	private void buyAction(Player user, BuyData actionData) {
		if(user.getPosition() instanceof PurchasableCircularList) { //TODO check if this is enough
			if(actionData.isUpgrade()) {
				((Street) user.getPosition()).nextStage();
			} else {
				user.buyPurchasable((PurchasableCircularList) user.getPosition());
			}
		}
	}

	/**
	 * The method will check at which state the trade is at, if at:
	 * <ul>
	 *     <li>ESTABLISH: Will check if one of the Player are currently trading and will send the HaggleData back with<br>ESTABLISHED if they are not trading (and sets the Player to be trading</br><br>DECLINE if one of them is trading</br></li>
	 *     <li>ESTABLISHED: Will never happen since that state is only set at the server</li>
	 *     <li>REQUEST: Will forward the HaggleData to all clients</li>
	 *     <li>OFFER: Will forward the HaggleData to all clients</li>
	 *     <li>ACCEPT: Will execute the trade eg. move the purchasable to its new respective owner and transfers the money between them and sets the player to be not trading</li>
	 *     <li>DECLINE: Will set the two player to be not trading</li>
	 * </ul>
	 *
	 * @param user
	 * @param actionData
	 */
	private void haggleAction(Player user, HaggleData actionData) {
		Player seller;

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

	/**
	 * The method call the right purchasable of the player to change its mortgage state
	 * @param user The value determines the Player the MortgageData is from
	 * @param actionData The value determines the MortgageData that needs to be processed
	 */
	private void mortgageAction(Player user, MortgageData actionData) {
		ArrayList<PurchasableCircularList> purchasableCircularListArrayList = user.getProperties();
		for(PurchasableCircularList purchasable : purchasableCircularListArrayList) {
			if(purchasable.getFieldNumber() == actionData.getFieldId()) {
				purchasable.setInMortgage(!purchasable.isInMortgage());
				break;
			}
		}
	}

	/**
	 * The method will check if the player wants to give up or wants to resolve a financial problem if a financial problem is said to be resolved all waiting threads are notified, if the player gives up he will be removed from the game
	 * @param user The value determines the Player the PlayerStatusData is from
	 * @param actionData The value determines the PlayerStatusData
	 */
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

	/**
	 * The method will remove the player from the game
	 * @param user The value determines the Player the PlayerStatusData is from
	 * @param actionData The value determines the PlayerStatusData to be processed
	 */
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
		for(Jailbrake card : user.getJailbrake()) {
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

	/**
	 * @return The return value is the ActionData the ActionThread was initialised with
	 */
	public ActionData getActionData() {
		return actionData;
	}
}
