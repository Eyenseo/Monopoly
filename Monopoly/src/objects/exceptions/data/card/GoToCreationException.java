package objects.exceptions.data.card;

import objects.exceptions.data.StorageReaderException;
//JAVADOC

public class GoToCreationException extends StorageReaderException {
	//JAVADOC
	public GoToCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of GoToCard:\n\t" + name
		                     : "There should have been data for a card but there wasn't!", cause);
	}
}
