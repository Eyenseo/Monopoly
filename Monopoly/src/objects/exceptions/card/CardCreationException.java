package objects.exceptions.card;

import objects.exceptions.StorageReaderException;

//JAVADOC
public class CardCreationException extends StorageReaderException {
	//JAVADOC
	public CardCreationException(String word) {
		super("There was an undefined control word:\n\t" + word);
	}
}
