package objects.exceptions.card;

import objects.exceptions.StorageReaderException;
//TODO Doc

public class PaymentCreationException extends StorageReaderException {
	//TODO Doc
	public PaymentCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a PaymentCard:\n\t" + name
				      : "There should have been data for a card but there wasn't!", cause);
	}
}
