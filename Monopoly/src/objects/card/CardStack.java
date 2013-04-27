package objects.card;

import core.data.CardCreator;
import objects.exceptions.data.StorageReaderException;

/**
 * The CardStock represents the stock of cards and is responsible for the shuffeling of the cards.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class CardStack {
	private final Card[] STACK;
	private       int    top;

	//JAVADOC
	public CardStack(String file, String path) throws StorageReaderException {
		//TODO randomise the Card objects
		this.top = 0;
		this.STACK = new CardCreator(file, path).cardArray();
	}

	//JAVADOC
	public Card[] toArray() {
		return STACK;
	}

	//JAVADOC
	public Card nextCard() {
		return STACK[top++];
	}
}
