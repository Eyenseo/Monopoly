package objects.exceptions.data.card;

import objects.exceptions.data.StorageReaderException;
//JAVADOC

public class PaymentCreationException extends StorageReaderException {
	//JAVADOC
	public PaymentCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a PaymentCard:\n\t" + name
		                     : "There should have been data for a card but there wasn't!", cause);
	}
}