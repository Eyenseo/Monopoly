package objects.card;

import objects.Player;
import objects.events.MessageEvent;
import objects.listeners.MessageEventListener;
import objects.value.MassageData;

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

	//JAVADOC
	public void addMessageEventListener(MessageEventListener listener) {
		this.listener.add(listener);
	}

	//JAVADOC
	public void removeMessageEventListener(MessageEventListener listener) {
		this.listener.remove(listener);
	}

	//JAVADOC
	public void fireMessageEvent(int playerid) {
		MassageData.MassageType type;
		//TODO better implementation
		if(NAME.equals("Event Karte")) {
			type = MassageData.MassageType.EVENT;
		} else {
			type = MassageData.MassageType.COMMUNITY;
		}

		MessageEvent event = new MessageEvent(this, new MassageData(playerid, type, TEXT));
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
