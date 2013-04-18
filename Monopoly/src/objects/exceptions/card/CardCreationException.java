package objects.exceptions.card;

import objects.exceptions.StorageReaderException;

//TODO Doc
public class CardCreationException extends StorageReaderException {
	//TODO Doc
	public CardCreationException(String word) {
		super("There was an undefined control word:\n\t" + word);
	}
}
