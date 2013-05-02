package objects.card;

import core.data.CardCreator;
import objects.exceptions.data.StorageReaderException;

import java.util.Vector;

/**
 * The CardStock represents the stock of cards and is responsible for the shuffeling of the cards.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class CardStack {
	private Vector<Card> stack;
	private int          top;

	//JAVADOC
	public CardStack(String file, String path) throws StorageReaderException {
		//TODO randomise the Card objects
		this.top = 0;
		this.stack = new CardCreator(file, path).cardArray();
		for(int i = 0; i < stack.size(); i++) {
			stack.get(i).setIndex(i);
		}
	}

	//JAVADOC
	public Vector<Card> getStack() {
		return stack;
	}

	//JAVADOC
	public Card nextCard() {
		top++;
		return stack.get(top - 1);
	}

	public void removeCard(int index) {
		stack.remove(index);
	}

	public void addCard(Card card, int index) {
		stack.add(index, card);
	}
}
