package objects.exceptions.data.card;

import objects.exceptions.data.StorageReaderException;

/**
 * The GoBackCreationException will be thrown if there was a problem while creating a GoBack object.
 *
 * @author Eyenseo
 * @version 1
 */
public class GoToStationCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the Card.
	 * @param cause The value determines the cause of the Exception.
	 */
	public GoToStationCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a GoToStationCard:\n\t" + name :
				      "There should have been data for a card but there wasn't!", cause);
	}
}
