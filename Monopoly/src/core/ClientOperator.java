package core;

import objects.events.ClientOperatorHaggleDataEvent;
import objects.events.ClientOperatorMessageDataEvent;
import objects.events.ClientOperatorPlayerDataEvent;
import objects.events.ClientOperatorPurchasableDataEvent;
import objects.listeners.ClientOperatorHaggleDataEventListener;
import objects.listeners.ClientOperatorMessageDataEventListener;
import objects.listeners.ClientOperatorPlayerDataEventListener;
import objects.listeners.ClientOperatorPurchasableDataEventListener;
import objects.value.InitializeMapData;
import objects.value.InitializePlayer;
import objects.value.MassageData;
import objects.value.PlayerData;
import objects.value.action.ActionData;
import objects.value.action.HaggleData;
import objects.value.map.FieldData;
import objects.value.map.PurchasableData;
import ui.gui.MainFrame;
import ui.gui.Model;

import java.util.ArrayList;

/**
 * The ClientOperator is the class that communicates with the server. It provides three listeners <ol>
 * <li>PlayerData</li> <li>FieldData</li> <li>HaggleData</li> </ol> these are fired if the client receives new data from
 * the server
 */
public class ClientOperator {
	private final ServerOperator                                        server;
	private       Model                                                 model;
	private       MainFrame                                             mainFrame;
	// Listener
	private final ArrayList<ClientOperatorPlayerDataEventListener>      playerDataEventListeners;
	private final ArrayList<ClientOperatorPurchasableDataEventListener> fieldDataEventListeners;
	private final ArrayList<ClientOperatorHaggleDataEventListener>      haggleDataEventListeners;
	private final ArrayList<ClientOperatorMessageDataEventListener>     messageDataEventListeners;

	/**
	 * @param server the value determines the server to communicate with
	 */
	public ClientOperator(ServerOperator server) {
		this.server = server;
		playerDataEventListeners = new ArrayList<ClientOperatorPlayerDataEventListener>();
		fieldDataEventListeners = new ArrayList<ClientOperatorPurchasableDataEventListener>();
		haggleDataEventListeners = new ArrayList<ClientOperatorHaggleDataEventListener>();
		messageDataEventListeners = new ArrayList<ClientOperatorMessageDataEventListener>();
	}

	/**
	 * The method sends the ActionData to the server.
	 *
	 * @param actionData the value determines the ActionData superclass that will be send to the server
	 */
	public void sendActionData(ActionData actionData) {
		server.processJob(actionData);
	}

	/**
	 * The method is called if the client receives new HaggleData and will fire a ClientOperatorHaggleDataEvent if
	 * either
	 * the HaggleData playerId or sellerId equals the clientPlayerId.
	 *
	 * @param haggleData the value determines the HaggleData to be checked
	 */
	public void updateHaggleData(HaggleData haggleData) {
		if(haggleData.getUserId() == model.getUser().getId() || haggleData.getSellerId() == model.getUser().getId()) {
			fireHaggleDataEvent(haggleData);
		}
	}

	/**
	 * The method will fire ClientOperatorHaggleDataEvent with the haggleData as attribute of the event.
	 *
	 * @param haggleData the value determines the HaggleData to be set as attribute of the event
	 */
	private void fireHaggleDataEvent(HaggleData haggleData) {
		ClientOperatorHaggleDataEvent event = new ClientOperatorHaggleDataEvent(this, haggleData);
		for(ClientOperatorHaggleDataEventListener l : haggleDataEventListeners) {
			l.actionPerformed(event);
		}
	}

	/**
	 * The method is called if the client receives new PlayerData and will fire a new ClientOperatorPlayerDataEvent.
	 *
	 * @param playerData the value determines the PlayerData to be checked
	 */
	public void updatePlayerData(PlayerData playerData) {
		firePlayerDataEvent(playerData);
	}

	/**
	 * The method will fire ClientOperatorPlayerDataEvent with the playerData as attribute of the event.
	 *
	 * @param playerData the value determines the PlayerData to be set as attribute of the event
	 */
	private void firePlayerDataEvent(PlayerData playerData) {
		ClientOperatorPlayerDataEvent event = new ClientOperatorPlayerDataEvent(this, playerData);
		for(ClientOperatorPlayerDataEventListener l : playerDataEventListeners) {
			l.actionPerformed(event);
		}
	}

	/**
	 * The method is called if the client receives new FieldData and will fire a new
	 * ClientOperatorPurchasableDataEvent.
	 *
	 * @param fieldData the value determines the PlayData to be checked
	 */
	public void updateFieldData(FieldData fieldData) {
		firePurchasableDataEvent((PurchasableData) fieldData);
	}

	/**
	 * The method is called if the client receives new MessageData anf will fire a new ClientMessageDataEvent
	 * @param massageData
	 */
	public void updateMessageData(MassageData massageData) {
		fireMessageDataEvent(massageData);
	}

	/**
	 * The method will fire ClientOperatorPurchasableEvent with the purchasableData as attribute of the event.
	 *
	 * @param purchasableData the value determines the PurchasableData to be set as attribute of the event
	 */
	private void firePurchasableDataEvent(PurchasableData purchasableData) {
		ClientOperatorPurchasableDataEvent event = new ClientOperatorPurchasableDataEvent(this, purchasableData);
		for(ClientOperatorPurchasableDataEventListener l : fieldDataEventListeners) {
			l.actionPerformed(event);
		}
	}

	/**
	 * The method will fire a ClientMessageDataEvent with the messageData as the attribute of the event.
	 * @param massageData The value determines the MessageData to be set as attribute of the event
	 */
	private void fireMessageDataEvent(MassageData massageData) {
		ClientOperatorMessageDataEvent event = new ClientOperatorMessageDataEvent(this, massageData);
		for(ClientOperatorMessageDataEventListener l : messageDataEventListeners) {
			l.actionPerformed(event);
		}
	}

	/**
	 * @param listener the value determines the listener to be added
	 */
	public void addHaggleDataEventListener(ClientOperatorHaggleDataEventListener listener) {
		haggleDataEventListeners.add(listener);
	}

	/**
	 * @param listener the value determines the listener to be removed
	 */
	public void removeHaggleDataEventListener(ClientOperatorHaggleDataEventListener listener) {
		haggleDataEventListeners.remove(listener);
	}

	/**
	 * @param listener the value determines the listener to be added
	 */
	public void addPlayerDataEventListener(ClientOperatorPlayerDataEventListener listener) {
		playerDataEventListeners.add(listener);
	}

	/**
	 * @param listener the value determines the listener to be removed
	 */
	public void removePlayerDataEventListener(ClientOperatorPlayerDataEventListener listener) {
		playerDataEventListeners.remove(listener);
	}

	/**
	 * @param listener the value determines the listener to be added
	 */
	public void addFieldDataEventListener(ClientOperatorPurchasableDataEventListener listener) {
		fieldDataEventListeners.add(listener);
	}

	/**
	 * @param listener the value determines the listener to be removed
	 */
	public void removeFieldDataEventListener(ClientOperatorPurchasableDataEventListener listener) {
		fieldDataEventListeners.remove(listener);
	}

	/**
	 *
	 * @param listener The value determines the listener to be added
	 */
	public void addMessageDataEventListener(ClientOperatorMessageDataEventListener listener) {
		messageDataEventListeners.add(listener);
	}

	/**
	 *
	 * @param listener The value determines the listener to be removed
	 */
	public void removeMessageEventListener(ClientOperatorMessageDataEventListener listener) {
		messageDataEventListeners.remove(listener);
	}

	/**
	 * The method is called in the initialisation process and initialise data needed to draw the map.
	 *
	 * @param data the value determines the object that holds the data of the map
	 */
	public void initializeMap(InitializeMapData data) {
		model = new Model(this, data);
	}

	/**
	 * The method is called in the initialisation process and initialise all participate in the game.
	 *
	 * @param data the value determines the object that holds the PlayerDatas
	 */
	public void initializePlayer(InitializePlayer data) {
		model.setPlayer(data.getPlayerData(), data.getUserId());
		mainFrame = new MainFrame(model, this);
		model.fireAllEvents(); //TODO that can be done better
	}
}
