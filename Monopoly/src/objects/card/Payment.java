package objects.card;
//JAVADOC

/**
 * Payment is a transaction card (gain or loss).
 *
 * @version 1
 */
public class Payment extends Card {
	private int dm;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public Payment(String name, String text, int dm) {
		super(name, text);
		this.dm = dm;
	}
}
