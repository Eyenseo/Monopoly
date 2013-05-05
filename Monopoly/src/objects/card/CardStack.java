package objects.card;

import core.data.CardCreator;
import objects.exceptions.data.StorageReaderException;

import java.util.Vector;

/**
 * The CardStack represents the stack of Cards and is responsible for the shuffling of the Card objects.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class CardStack {
	private Vector<Card> stack;
	private int          top;

	/**
	 * @param file The value determines the file located in the storage package to be read of.
	 * @param name The value determines the name of the Card objects.
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	public CardStack(String file, String name) throws StorageReaderException {
		//TODO randomise the Card objects
		this.top = 0;
		this.stack = new CardCreator(file, name).cardArray();
		for(int i = 0; i < stack.size(); i++) {
			stack.get(i).setIndex(i);
		}
	}

	/**
	 * @return The return value is a Vector of Cards.
	 */
	public Vector<Card> getStack() {
		return stack;
	}

	/**
	 * @return The return value is the next Card object of the stack.
	 */
	public Card nextCard() {
		top++;
		return stack.get(top - 1);
	}

	/**
	 * @param index The value determines the index of the Card object to be removed.
	 */
	public void removeCard(int index) {
		stack.remove(index);
	}

	/**
	 * @param card  The value determines the Card object to be added to the stack.
	 * @param index The value is the position at which the Card object will be inserted at.
	 */
	public void addCard(Card card, int index) {
		stack.add(index, card);
	}
}
