package core;

import objects.Player;
import objects.exceptions.MessageStackException;
import objects.map.FieldCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.Street;
import objects.value.action.*;
import ui.Menu;
import ui.cui.ConsoleMenu;

import java.util.ArrayList;

/**
 * Monopoly is the main Class. It initialises the game and hold pieces of the mechanic.
 */
//JAVADOC
public class Monopoly {
	private final int STARTMONEY = 30000;
	private final Thread         turnThread;
	private final ServerOperator serverOperator;
	private final boolean gameOver = false;
	private final FieldCircularList go;
	private final FieldCircularList jail;
	private final ArrayList<Player> playerArrayList;

	/**
	 * @param loader The value determines the loader that holds the information for the game
	 */
	private Monopoly(Loader loader) {
		go = loader.getGo();
		jail = loader.getJail();
		playerArrayList = loader.getPlayerArrayList();
		serverOperator = new ServerOperator(this);

		//The thread will set the next player as active and will then wait for him to finish his turn and sets the
		//next player active and waits ...
		//TODO Specify a timeout for a turn
		turnThread = new Thread(new Runnable() {
			@Override public void run() {
				try {
					synchronized(turnThread) {

						while(!gameOver) {
							for(Player player : playerArrayList) {
								player.setTurnEnd(false);

								System.out.println(player.getName());

								turnThread.wait();  //TODO possible replace with "Condition"

								if(player.isGiveUp()) {
									break;
								}
							}
						}
					}
				} catch(InterruptedException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		});
	}

	public static void main(String[] args) {
		try {
			Menu menu = new ConsoleMenu();  //TODO remove
			Monopoly m = new Monopoly(new Loader(menu));

			//		for(int i = menu.playerAmount(); i > 0; i--) {
			//			m.addPlayer(new Player(menu.getName(), m.STARTMONEY));
			//		}

			m.addPlayer(new Player("Julia", m.STARTMONEY));
			m.addPlayer(new Player("Markus", m.STARTMONEY));
			m.startMonopoly();
		} catch(MessageStackException e) {
			System.err.println(e.getMessageStack());
		}
	}

	/**
	 * The method will initialise all Clients with the PlayerData and then start the turnThread which will set the next
	 * player as active and will then wait for him to finish his turn
	 */
	private void startMonopoly() {
		serverOperator.initializePlayer();
		turnThread.start();
	}

	/**
	 * @param player The value determines the player to be added to the game
	 */
	private void addPlayer(Player player) {
		playerArrayList.add(player);
		player.setPosition(go);
		ClientOperator clientOperator = new ClientOperator(serverOperator);

		serverOperator.addDestination(player, clientOperator);
	}

	/**
	 * @param actionData The value determines the action to be done
	 */
	//TODO chop the method down
	public void doAction(ActionData actionData) {
		synchronized(turnThread) {
			Player player = playerArrayList.get(actionData.getPlayerId());

			if(actionData instanceof TurnData) {
				if(((TurnData) actionData).isTurnAction()) {
					if(player.getThrewDice() < 3) {
						player.move();
					} else {
						player.setPosition(jail);
						player.setInJail(true);
						player.setTurnEnd(true);
						turnThread.notify();
					}
				} else {
					player.setTurnEnd(true);
					turnThread.notify();
				}
			} else if(actionData instanceof BuyData) {
				if(player.getPosition() instanceof PurchasableCircularList) { //TODO check if this is enough
					if(((BuyData) actionData).isUpgrade()) {
						((Street) player.getPosition()).nextStage();
					} else {
						player.buyPurchasable((PurchasableCircularList) player.getPosition());
					}
				}
			} else {
				if(actionData instanceof HaggleData) {
					HaggleData haggleData = (HaggleData) actionData;

					System.out.println(haggleData.getHaggleState());

					Player seller;
					switch(haggleData.getHaggleState()) {
						case ESTABLISH:
							seller = playerArrayList.get(haggleData.getSellerId());
							if(player.getTrading() == -1 && seller.getTrading() == -1) {
								player.setTrading(seller.getPlayerId());
								seller.setTrading(player.getPlayerId());
								haggleData.setHaggleState(HaggleData.HaggleState.ESTABLISHED);
							} else {
								haggleData.setHaggleState(HaggleData.HaggleState.DECLINE);
							}
							serverOperator.sendHaggleData(haggleData);
							break;

						// case ESTABLISHED: //Will never happen - this state will just appear on the client
						// break;

						case REQUEST:
							serverOperator.sendHaggleData(haggleData);
							break;

						case OFFER:
							serverOperator.sendHaggleData(haggleData);
							break;

						case ACCEPT:
							seller = playerArrayList.get(haggleData.getSellerId());
							// trade money
							int playerPay = haggleData.getSellerMoney() - haggleData.getPlayerMoney();
							if(playerPay != 0) {
								if(playerPay < 0) {
									playerPay *= -1;
									seller.pay(playerPay);
									player.addMoney(playerPay);
								} else {
									seller.addMoney(playerPay);
									player.pay(playerPay);
								}
							}
							// trade purchasables
							for(int id : haggleData.getSellerFieldIds()) {
								for(PurchasableCircularList purchasable : player.getProperties()) {
									if(purchasable.getFieldNumber() == id) {
										purchasable.setOwner(seller);
										break;
									}
								}
							}
							for(int id : haggleData.getPlayerFieldIds()) {
								for(PurchasableCircularList purchasable : seller.getProperties()) {
									if(purchasable.getFieldNumber() == id) {
										purchasable.setOwner(player);
										break;
									}
								}
							}
							serverOperator.sendHaggleData(haggleData);
							player.setTrading(-1);
							seller.setTrading(-1);

							break;

						case DECLINE:
							seller = playerArrayList.get(haggleData.getSellerId());

							player.setTrading(-1);
							seller.setTrading(-1);

							serverOperator.sendHaggleData(haggleData);
							break;
					}
				} else if(actionData instanceof MortgageData) {
					ArrayList<PurchasableCircularList> purchasableCircularListArrayList = player.getProperties();
					for(PurchasableCircularList purchasable : purchasableCircularListArrayList) {
						if(purchasable.getFieldNumber() == ((MortgageData) actionData).getFieldId()) {
							purchasable.setInMortgage(!purchasable.isInMortgage());
							break;
						}
					}
				} else if(actionData instanceof GiveUpData) {

				}
			}
		}
	}

	/**
	 * @return The return value is the Go Field
	 */
	public FieldCircularList getGo() {
		return go;
	}

	/**
	 * @return the return value is the playerArrayList
	 */
	public ArrayList<Player> getPlayerArrayList() {
		return playerArrayList;
	}
}