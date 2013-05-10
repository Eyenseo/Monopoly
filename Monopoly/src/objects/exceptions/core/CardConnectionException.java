package objects.exceptions.core;

import objects.card.Card;
import objects.card.GoTo;

/**
 * The CardConnectionException will be thrown if the needed Street object is not found for a GoTo object.
 *
 * @author Eyenseo
 * @version 1
 */
public class CardConnectionException extends Exception {
	/**
	 * @param card The value determines the Card that had the problem.
	 */
	public CardConnectionException(Card card) {
		super("The card \"" + card.getName() + "\" with the text \"" + card.getText() +
		      "\" has to have a FieldCircularList called\"" + ((GoTo) card).getFieldName() + "\" but there wasn't one");
	}
}
