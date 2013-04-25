package objects.exceptions.card;

import objects.exceptions.StorageReaderException;
//JAVADOC

public class CardArrayException extends StorageReaderException {
	//JAVADOC
	public CardArrayException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data for an array:\n\t" + name
		                     : "There should have been data for an array but there wasn't!", cause);
	}
}
