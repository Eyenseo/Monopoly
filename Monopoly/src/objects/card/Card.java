package objects.card;

import core.ServerOperator;
import objects.Player;
import objects.events.CardEvent;
import objects.listeners.CardEventListener;
import objects.value.CardData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Card is the superclass of every card object.
 */
public abstract class Card implements Serializable {
	private static final long serialVersionUID = 4057299799394754933L;
	private final String                       NAME;
	private final String                       TEXT;
	private       ArrayList<CardEventListener> listener;
	int            index;
	ServerOperator serverOperator;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	Card(String name, String text) {
		NAME = name;
		TEXT = text;
		listener = new ArrayList<CardEventListener>();
	}

	public void addCardEventListener(CardEventListener listener) {
		this.listener.add(listener);
	}

	public void removeCardEventListener(CardEventListener listener) {
		this.listener.remove(listener);
	}

	public void fireCardEvent(String playerName) {
		CardEvent event = new CardEvent(this, new CardData(playerName, NAME, TEXT));
		for(CardEventListener listener : this.listener) {
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
