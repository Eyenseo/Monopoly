package objects.card;

import objects.Player;
//JAVADOC

/**
 * Card is the superclass of every card object.
 */
public abstract class Card {
	private final String NAME;
	private final String TEXT;
	int index;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	Card(String name, String text) {
		this.NAME = name;
		this.TEXT = text;
	}

	/**
	 * @return The return value is the name of a Card.
	 */
	public String getName() {
		return this.NAME;
	}

	/**
	 * @return The return value is the text field of a Card.
	 */
	public String getText() {
		return this.TEXT;
	}

	//	public abstract void action();
	//TODO this has to be replaced in each individual Method and set to abstract
	public void action(Player player) {
		System.err.println("This is a placeholder lalala~lalaLA");
	}

	/**
	 * @param index The value determines the index of the Card in the CardStack
	 * @see CardStack
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
