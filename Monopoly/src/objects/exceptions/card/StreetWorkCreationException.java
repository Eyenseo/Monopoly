package objects.exceptions.card;

import objects.exceptions.StorageReaderException;
//TODO Doc

public class StreetWorkCreationException extends StorageReaderException {
	//TODO Doc
	public StreetWorkCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a StreetWorkCard:\n\t" + name
				      : "There should have been data for a card but there wasn't!", cause);
	}
}

