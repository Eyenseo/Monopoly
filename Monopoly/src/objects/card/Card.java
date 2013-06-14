package objects.card;

import objects.Player;
import ui.Menu;

import java.io.Serializable;

/**
 * Card is the superclass of every card object.
 */
public abstract class Card implements Serializable {
	private static final long serialVersionUID = 4057299799394754933L;
	private final String NAME;
	private final String TEXT;
	int  index;
	Menu menu;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	Card(String name, String text) {
		NAME = name;
		TEXT = text;
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

	/**
	 * @param menu The value determines what menu will be used.
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
