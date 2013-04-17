package objects.exceptions.card;

import objects.exceptions.StorageReaderException;

public class JailCreationException extends StorageReaderException {
	public JailCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a JailCard:\n\t" + name
		                     : "There should have been data for a card but there wasn't!", cause);
	}
}
