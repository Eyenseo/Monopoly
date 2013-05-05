package objects.exceptions.data.card;

import objects.exceptions.data.StorageReaderException;

/**
 * The JailbaitCreationException will be thrown if there was a problem while creating a Jailbait object.
 *
 * @author Eyenseo
 * @version 1
 */
public class JailbaitCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the Card.
	 * @param cause The value determines the cause of the Exception.
	 */
	public JailbaitCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a JailbaitCard:\n\t" + name :
				      "There should have been data for a card but there wasn't!", cause);
	}
}

