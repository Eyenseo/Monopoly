package objects.value.card;

/**
 * The CardStock represents the stock of cards and is responsible for the shifting of the cards.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class CardStack {
	private final Card[] STACK;
	private       int    top;

	//TODO Doc
	CardStack(Card[] cards, int top) {
		//TODO randomise the Card objects
		this.top = top;
		this.STACK = cards;
	}
	//TODO everything
}
