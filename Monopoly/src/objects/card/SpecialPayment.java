package objects.card;
//JAVADOC

/**
 * SpecialPayment is a special transaction card (player based transactions).
 *
 * @version 1
 */
public class SpecialPayment extends Card {
	private int dm;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public SpecialPayment(String name, String text, int dm) {
		super(name, text);
		this.dm = dm;
	}
}
