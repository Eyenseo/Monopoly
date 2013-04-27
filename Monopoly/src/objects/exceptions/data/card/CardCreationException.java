package objects.exceptions.data.card;

import objects.exceptions.data.StorageReaderException;

//JAVADOC
public class CardCreationException extends StorageReaderException {
	//JAVADOC
	public CardCreationException(String word) {
		super("There was an undefined control word:\n\t" + word);
	}
}
