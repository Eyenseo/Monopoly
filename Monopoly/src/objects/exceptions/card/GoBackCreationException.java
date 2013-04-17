package objects.exceptions.card;

import objects.exceptions.StorageReaderException;

public class GoBackCreationException extends StorageReaderException {
	public GoBackCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a GoBackCard:\n\t" + name
		                     : "There should have been data for a card but there wasn't!", cause);
	}
}
