package objects.card;

/**
 * SpecialPayment is a special transaction card (player based transactions).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class SpecialPayment extends Card {
	private int dm;

	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public SpecialPayment(String name, String text, int dm) {
		super(name, text);
		this.dm = dm;
	}
}
