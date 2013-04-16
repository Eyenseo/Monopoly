package objects.card;

/**
 * Jail is the jail card (Gehe ins Gefaengnis Karte).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class Jail extends Card {
	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public Jail(String name, String text) {
		super(name, text);
	}
}
