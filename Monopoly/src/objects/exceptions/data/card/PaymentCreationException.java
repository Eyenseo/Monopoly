package objects.exceptions.data.card;

import objects.exceptions.data.StorageReaderException;

/**
 * The PaymentCreationException will be thrown if there was a problem while creating a Payment object.
 *
 * @author Eyenseo
 * @version 1
 */
public class PaymentCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the Card.
	 * @param cause The value determines the cause of the Exception.
	 */
	public PaymentCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a PaymentCard:\n\t" + name :
				      "There should have been data for a card but there wasn't!", cause);
	}
}
