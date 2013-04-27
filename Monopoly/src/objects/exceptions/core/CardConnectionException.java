package objects.exceptions.core;

import objects.card.Card;
import objects.card.GoTo;

/**
 * Created with IntelliJ IDEA.
 * User: eyenseo
 * Date: 4/27/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardConnectionException extends Exception {
	public CardConnectionException(Card card) {
		super("The card \"" + card.getName() + "\" with the text \"" + card.getText() +
		      "\" has to have a Field called\"" + ((GoTo) card).getFieldName() + "\" but there wasn't one");
	}
}
