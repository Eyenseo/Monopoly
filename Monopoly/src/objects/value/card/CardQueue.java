package objects.value.card;

/**
 * CardQueue is the superclass of every card object (No real queue for there is no pop that deletes a queue entry).
 */
public abstract class CardQueue {
	private final String    NAME;
	private final String    TEXT;
	private       CardQueue next;
	private       CardQueue last;

	/**
	 * @param name Name of the card
	 * @param text Textfield of the card
	 */
	CardQueue(String name, String text) {
		this.NAME = name;
		this.TEXT = text;
	}

	abstract void action();

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

	/**
	 * This methods adds a new card to the queue.
	 *
	 * @param nextCard The next card in the queue
	 * @param lastCard The last card in the queue viewed from the first position
	 */
	public void add(CardQueue nextCard, CardQueue lastCard) {
		this.next = nextCard;
		this.last = lastCard;
	}

	/**
	 * @return the return value is the new top card of the stack
	 */
	public CardQueue returnToEnd() {
		this.last.next = this;
		this.next = null;
		return this.next;
	}
}
