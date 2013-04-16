package objects.card;

/**
 * GoBack is the back card (Gehe x Felder zurück).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class GoBack extends Card {
	private int fields;

	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public GoBack(String name, String text, int fields) {
		super(name, text);
		this.fields = fields;
	}
}
