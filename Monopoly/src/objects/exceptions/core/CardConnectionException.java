package objects.exceptions.core;

import objects.card.Card;
import objects.card.GoTo;

//JAVADOC
public class CardConnectionException extends Exception {
	//JAVADOC
	public CardConnectionException(Card card) {
		super("The card \"" + card.getName() + "\" with the text \"" + card.getText() +
				      "\" has to have a Field called\"" + ((GoTo) card).getFieldName() + "\" but there wasn't one");
	}
}
