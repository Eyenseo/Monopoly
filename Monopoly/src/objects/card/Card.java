package objects.card;

import objects.Player;
import objects.events.MessageEvent;
import objects.listeners.MessageEventListener;
import objects.value.MessageData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Card is the superclass of every card object.
 */
public abstract class Card implements Serializable {
	private static final long serialVersionUID = 4057299799394754933L;
	private final String                          NAME;
	private final String                          TEXT;
	private       ArrayList<MessageEventListener> listener;
	int index;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	Card(String name, String text) {
		NAME = name;
		TEXT = text;
		listener = new ArrayList<MessageEventListener>();
	}

	/**
	 * @param listener the value determines the messageEventListener to be added
	 */
	public void addMessageEventListener(MessageEventListener listener) {
		this.listener.add(listener);
	}

	/**
	 * @param listener the value determines the messageEventListener to be removed
	 */
	public void removeMessageEventListener(MessageEventListener listener) {
		this.listener.remove(listener);
	}

	/**
	 * The method will fire a MessageDataEvent for all listeners.
	 * If the Name of the CardStack is "Event Karte" the type of the MessageData is CHANCE
	 */
	//TODO replace the String comparison with a enum value
	public void fireMessageEvent(int playerid) {
		MessageData.MessageType type;
		//TODO better implementation
		if(NAME.equals("Event Karte")) {
			type = MessageData.MessageType.CHANCE;
		} else {
			type = MessageData.MessageType.COMMUNITY;
		}

		MessageEvent event = new MessageEvent(this, new MessageData(playerid, type, TEXT));
		for(MessageEventListener listener : this.listener) {
			listener.actionPerformed(event);
		}
	}

	/**
	 * @return The return value is the name of a Card.
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * @return The return value is the text field of a Card.
	 */
	public String getText() {
		return TEXT;
	}

	/**
	 * The method will do what ever the objects propose is
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	public abstract void action(Player player);

	/**
	 * @param index The value determines the index of the Card in the CardStack
	 * @see CardStack
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
