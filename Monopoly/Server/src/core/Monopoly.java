package core;

import objects.Player;
import objects.exceptions.MessageStackException;
import objects.map.FieldCircularList;
import objects.value.action.ActionData;

import java.util.HashMap;

/**
 * Monopoly is the main Class. It initialises the game and hold pieces of the mechanic.
 */
//JAVADOC
public class Monopoly {
	private final int     STARTMONEY = 30000;
	private final boolean gameOver   = false;
	private final FieldCircularList                               go;
	private final FieldCircularList                               jail;
	private final HashMap<Integer, Player>                        playerHashMap;
	private final ServerOperator                                  serverOperator;
	private final Thread                                          turnThread;
	private final HashMap<Integer, HashMap<String, ActionThread>> actions; //TODO better structure

	/**
	 * @param loader The value determines the loader that holds the information for the game
	 */
	private Monopoly(Loader loader) {
		go = loader.getGo();
		jail = loader.getJail();
		playerHashMap = loader.getPlayerHashMap();
		serverOperator = new ServerOperator(this, loader);

		//The thread will set the next player as active and will then wait for him to finish his turn and sets the
		//next player active and waits ...
		//TODO Specify a timeout for a turn
		turnThread = new Thread(new Runnable() {
			@Override public void run() {
				try {
					synchronized(turnThread) {
						while(!gameOver) {
							for(Player player : playerHashMap.values()) {
								player.setTurnEnd(false);
								turnThread.wait();  //TODO possible replace with "Condition"
							}
						}
					}
				} catch(InterruptedException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		});
		turnThread.setName("Turn thread");

		actions = new HashMap<Integer, HashMap<String, ActionThread>>();
	}

	public static void main(String[] args) {
		try {
			Monopoly m = new Monopoly(new Loader());

			//		for(int i = menu.playerAmount(); i > 0; i--) {
			//			m.addPlayer(new Player(menu.getName(), m.STARTMONEY));
			//		}

			m.addPlayer(new Player("Julia", 0));
			m.addPlayer(new Player("Markus", m.STARTMONEY));
			m.addPlayer(new Player("Herbert", m.STARTMONEY));
			m.startMonopoly();
		} catch(MessageStackException e) {
			System.err.println(e.getMessageStack());
		}
	}

	/**
	 * @param player The value determines the player to be added to the game
	 */
	private void addPlayer(Player player) {
		playerHashMap.put(player.getPlayerId(), player);
		player.setPosition(go);

		ClientOperator clientOperator = new ClientOperator(serverOperator);
		serverOperator.addDestination(player, clientOperator);

		actions.put(player.getPlayerId(), new HashMap<String, ActionThread>());
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
	 * @param actionData The value determines the action to be done
	 */
	//TODO chop the method down
	public void doAction(final ActionData actionData) {
		//TODO check what happens if a player doesn't have the money and decides to give up - is deleting the thread
		// enough?
		actions.get(actionData.getUserId()).put(actionData.getId(),
		                                        new ActionThread(playerHashMap, actionData, serverOperator, jail,
		                                                         turnThread, actions));
		actions.get(actionData.getUserId()).get(actionData.getId()).setName(actionData.getId());
		actions.get(actionData.getUserId()).get(actionData.getId()).start();
	}

	/**
	 * @return The return value is the Go Field
	 */
	public FieldCircularList getGo() {
		return go;
	}

	/**
	 * @return the return value is the playerHashMap
	 */
	public HashMap<Integer, Player> getPlayerHashMap() {
		return playerHashMap;
	}
}