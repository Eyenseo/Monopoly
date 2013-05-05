package objects.card;
//JAVADOC

/**
 * PayFineTakeCard is special card (pay fine or draw a card).
 *
 * @version 1
 */
public class PayFineTakeCard extends Card {
	CardStack community;

	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public PayFineTakeCard(String name, String text) {
		super(name, text);
	}

	/**
	 * @return The return value is the community CardStack.
	 */
	public CardStack getCommunity() {
		return community;
	}

	/**
	 * @param community The value determines the community Card Stack.
	 */
	public void setCommunity(CardStack community) {
		this.community = community;
	}
}
