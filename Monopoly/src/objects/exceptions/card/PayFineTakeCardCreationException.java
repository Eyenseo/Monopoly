package objects.exceptions.card;

import objects.exceptions.StorageReaderException;

public class PayFineTakeCardCreationException extends StorageReaderException {
	public PayFineTakeCardCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a PayFineTakeCardCard:\n\t" + name
		                     : "There should have been data for a card but there wasn't!", cause);
	}
}
