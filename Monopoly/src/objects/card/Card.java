package objects.card;

/**
 * Card is the superclass of every card object
 */
public abstract class Card {
	private final String NAME;
	private final String TEXT;

	/**
	 * @param name Name of the card
	 * @param text Textfield of the card
	 */
	Card(String name, String text) {
		this.NAME = name;
		this.TEXT = text;
	}

	/**
	 * @return This returns the name of a card.
	 */
	public String getName() {
		return this.NAME;
	}

	/**
	 * @return This returns the textfield of a card.
	 */
	public String getText() {
		return this.TEXT;
	}

	public abstract void action();
}
