package objects.value.card;

/**
 * JailbaitQueue is the jailbait card (Gefängnisfreikarte).
 */
public class JailbaitQueue extends CardQueue {
	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public JailbaitQueue(String name, String text) {
		super(name, text);
	}

	@Override
	void action() {
	}
}
