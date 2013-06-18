package core;

import objects.Player;
import objects.card.Card;
import objects.card.CardStack;
import objects.events.CardEvent;
import objects.events.PlayerEvent;
import objects.events.PurchasableEvent;
import objects.listeners.CardEventListener;
import objects.listeners.PlayerEventListener;
import objects.listeners.PurchasableEventListener;
import objects.map.FieldCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.value.CardData;
import objects.value.InitializeMapData;
import objects.value.InitializePlayer;
import objects.value.PlayerData;
import objects.value.action.ActionData;
import objects.value.action.HaggleData;
import objects.value.map.FieldData;

import java.util.ArrayList;

/**
 * The ServerOperator is the class that communicates with the clients
 */
public class ServerOperator {
	private final ArrayList<ClientOperator> destination;
	private final Monopoly                  monopoly;

	/**
	 * @param monopoly the value determines the Monopoly object at which the event listeners will be registered
	 */
	public ServerOperator(Monopoly monopoly, Loader loader) {
		this.monopoly = monopoly;
		destination = new ArrayList<ClientOperator>();

		FieldCircularList go = monopoly.getGo();
		FieldCircularList currentField = go;
		do {
			if(currentField instanceof PurchasableCircularList) {
				final PurchasableCircularList purchasable = (PurchasableCircularList) currentField;
				purchasable.addPurchasableEventListener(new PurchasableEventListener() {
					@Override public void actionPerformed(PurchasableEvent event) {
						for(ClientOperator client : destination) {
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
			currentCard.addCardEventListener(new CardEventListener() {
				@Override public void actionPerformed(CardEvent event) {
					sendCardData(event.getCardData());
				}
			});
			currentCard = stack.nextCard();
		} while(!start.equals(currentCard));

		//Register Events at the community CardStack
		stack = loader.getCommunity();
		start = stack.nextCard();
		currentCard = start;
		do {
			currentCard.addCardEventListener(new CardEventListener() {
				@Override public void actionPerformed(CardEvent event) {
					sendCardData(event.getCardData());
				}
			});
			currentCard = stack.nextCard();
		} while(!start.equals(currentCard));
	}

	public void sendCardData(CardData cardData) {
		for(ClientOperator client : destination) {
			client.updateCardData(cardData);
		}
	}

	/**
	 * The method will send the haggleData to the server.
	 *
	 * @param haggleData The value determines the HaggleData to the server
	 */
	public void sendHaggleData(HaggleData haggleData) {
		//TODO improve that only the participating player will get the data

		System.out.println(haggleData.getHaggleState());
		for(ClientOperator client : destination) {
			client.updateHaggleData(haggleData);
		}
	}

	/**
	 * The method will add a new player with his destination.
	 *
	 * @param player         the value determines the player of the new destination
	 * @param clientOperator the value determines the new destination
	 */
	public void addDestination(Player player, ClientOperator clientOperator) {
		destination.add(clientOperator);

		initializeMap(clientOperator);

		player.addPlayerEventListener(new PlayerEventListener() {
			@Override public void actionPerformed(PlayerEvent event) {
				for(ClientOperator client : destination) {
					client.updatePlayerData(((Player) event.getSource()).toPlayerData());
				}
			}
		});
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
		ArrayList<PlayerData> playerData = new ArrayList<PlayerData>();

		for(Player player : monopoly.getPlayerArrayList()) {
			playerData.add(player.toPlayerData());
		}

		for(int i = 0; i < destination.size(); i++) {
			destination.get(i).initializePlayer(new InitializePlayer(playerData, i));
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
