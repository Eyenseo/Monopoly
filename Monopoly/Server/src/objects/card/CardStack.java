package objects.card;

import core.data.CardCreator;
import objects.exceptions.data.StorageReaderException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * The CardStack represents the stack of Cards and is responsible for the shuffling of the Card objects.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class CardStack implements Serializable {
	private static final long serialVersionUID = 5367102863013409270L;
	private ArrayList<Card> stack;
	private int             top;

	/**
	 * @param file The value determines the file located in the storage package to be read of.
	 * @param name The value determines the name of the Card objects.
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be
	 *                                read out with getMessageStack.
	 */
	public CardStack(String file, String name) throws StorageReaderException {
		stack = new ArrayList<Card>();
		top = 0;
		ArrayList<Card> temp;
		Random random = new Random();
		int index;
		temp = new CardCreator(file, name).cardArray();
		while(!temp.isEmpty()) {
			index = random.nextInt(temp.size());
			index = index - (index == 0 ? 0 : 1);
			stack.add(temp.get(index));
			temp.remove(index);
		}
		//TODO Check for notOneInstance
		for(int i = 0; i < stack.size(); i++) {
			stack.get(i).setIndex(i);
		}
	}

	/**
	 * @return The return value is a ArrayList of Cards.
	 */
	public ArrayList<Card> getStack() {
		return stack;
	}

	/**
	 * @return The return value is the next Card object of the stack.
	 */
	public Card nextCard() {
		if(stack.size() > top) {
			top++;
		} else {
			top = 1;
		}
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
