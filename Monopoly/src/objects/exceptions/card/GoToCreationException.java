package objects.exceptions.card;

import objects.exceptions.StorageReaderException;

public class GoToCreationException extends StorageReaderException {
	public GoToCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of GoToCard:\n\t" + name
		                     : "There should have been data for a card but there wasn't!", cause);
	}
}
