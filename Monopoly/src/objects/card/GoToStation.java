package objects.card;

/**
 * GoToStation is a special_movement card (Rücke vor bis zum nächsten Bahnhof).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class GoToStation extends Card {
	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public GoToStation(String name, String text) {
		super(name, text);
	}
}
