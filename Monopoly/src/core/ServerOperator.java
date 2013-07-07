package core;

import objects.Player;
import objects.card.Card;
import objects.card.CardStack;
import objects.events.MessageEvent;
import objects.events.PlayerEvent;
import objects.events.PurchasableEvent;
import objects.listeners.MessageEventListener;
import objects.listeners.PlayerEventListener;
import objects.listeners.PurchasableEventListener;
import objects.map.FieldCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.value.InitializeMapData;
import objects.value.InitializePlayer;
import objects.value.MessageData;
import objects.value.PlayerData;
import objects.value.action.ActionData;
import objects.value.action.HaggleData;
import objects.value.map.FieldData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The ServerOperator is the class that communicates with the clients
 */
public class ServerOperator {
	private final HashMap<Integer, ClientOperator> destination;
	private final Monopoly                         monopoly;

	/**
	 * @param monopoly the value determines the Monopoly object at which the event listeners will be registered
	 */
	public ServerOperator(Monopoly monopoly, Loader loader) {
		this.monopoly = monopoly;
		destination = new HashMap<Integer, ClientOperator>();

		FieldCircularList go = monopoly.getGo();
		FieldCircularList currentField = go;
		do {
			//adds eventlisteners to every purchasable field and performs actions if needed
			if(currentField instanceof PurchasableCircularList) {
				final PurchasableCircularList purchasable = (PurchasableCircularList) currentField;
				purchasable.addPurchasableEventListener(new PurchasableEventListener() {
					@Override public void actionPerformed(PurchasableEvent event) {
						//synchronizes new purchasable data with every client
						for(ClientOperator client : destination.values()) {
							client.updateFieldData(purchasable.toFieldData());
						}
					}
				});
			}
			currentField = currentField.getNext();
		} while(!currentField.equals(go));

		Card start;
		Card currentCard;
		CardStack stack;

		//Register events at the event CardStack
		stack = loader.getEvent();
		start = stack.nextCard();
		currentCard = start;
		do {
			currentCard.addMessageEventListener(new MessageEventListener() {
				@Override public void actionPerformed(MessageEvent event) {
					sendCardData(event.getMessageData());
				}
			});
			currentCard = stack.nextCard();
		} while(!start.equals(currentCard));

		//Register Events at the community CardStack
		stack = loader.getCommunity();
		start = stack.nextCard();
		currentCard = start;
		do {
			currentCard.addMessageEventListener(new MessageEventListener() {
				@Override public void actionPerformed(MessageEvent event) {
					sendCardData(event.getMessageData());
				}
			});
			currentCard = stack.nextCard();
		} while(!start.equals(currentCard));
	}

	/**
	 * the methods sends a message data object to each client.
	 *
	 * @param messageData the value determines either the MessageType COMMUNITY or CHANCE and the text of the card.
	 */
	public void sendCardData(MessageData messageData) {
		for(ClientOperator client : destination.values()) {
			client.updateMessageData(messageData);
		}
	}

	/**
	 * The method will send the haggleData to the server.
	 *
	 * @param haggleData The value determines the HaggleData to the server
	 */
	public void sendHaggleData(HaggleData haggleData) {
		destination.get(haggleData.getUserId()).updateHaggleData(haggleData);
		if(destination.containsKey(haggleData.getSellerId())) {
			destination.get(haggleData.getSellerId()).updateHaggleData(haggleData);
		}
	}

	/**
	 * The method will add a new player with his destination.
	 *
	 * @param player         the value determines the player of the new destination
	 * @param clientOperator the value determines the new destination
	 */
	public void addDestination(Player player, ClientOperator clientOperator) {
		destination.put(player.getPlayerId(), clientOperator);

		initializeMap(clientOperator);

		player.addPlayerEventListener(new PlayerEventListener() {
			@Override public void actionPerformed(PlayerEvent event) {
				for(ClientOperator client : destination.values()) {
					client.updatePlayerData(((Player) event.getSource()).toPlayerData());
				}
			}
		});
	}
	
	/**
	 * Removes a player from the destination HashMap.
	 *
	 * @param playerId The value determines the ID of the player to be removed
	 */
	public void removeDestination(int playerId) {
		destination.remove(playerId);
	}

	/**
	 * The method initialised sends the map initialization data
	 *
	 * @param client the value determines the destination of the client
	 */
	private void initializeMap(ClientOperator client) {
		ArrayList<FieldData> map = new ArrayList<FieldData>();

		FieldCircularList go = monopoly.getGo();
		FieldCircularList current = go;
		do {
			map.add(current.toFieldData());
			current = current.getNext();
		} while(!current.equals(go));

		client.initializeMap(new InitializeMapData(map));
	}

	/**
	 * The method will send the PlayerData of all Players to all clients
	 */
	public void initializePlayer() {
		HashMap<Integer, PlayerData> playerData = new HashMap<Integer, PlayerData>();

		for(Player player : monopoly.getPlayerHashMap().values()) {
			playerData.put(player.getPlayerId(), player.toPlayerData());
		}

		for(int i = 0; i < destination.size(); i++) {
			destination.get(i).initializePlayer(new InitializePlayer(new HashMap<Integer, PlayerData>(playerData), i));
		}
	}

	/**
	 * The method is called to process action data received from the clients
	 *
	 * @param actionData the value determines the action data to processed
	 */
	public void processJob(ActionData actionData) {
		monopoly.doAction(actionData);
	}
}
