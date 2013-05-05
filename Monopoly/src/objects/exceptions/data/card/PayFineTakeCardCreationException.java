package objects.exceptions.data.card;

import objects.exceptions.data.StorageReaderException;

/**
 * The PayFineTakeCardCreationException will be thrown if there was a problem while creating a PayFineTakeCard object.
 *
 * @author Eyenseo
 * @version 1
 */
public class PayFineTakeCardCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the Card.
	 * @param cause The value determines the cause of the Exception.
	 */
	public PayFineTakeCardCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a PayFineTakeCardCard:\n\t" + name :
				      "There should have been data for a card but there wasn't!", cause);
	}
}
