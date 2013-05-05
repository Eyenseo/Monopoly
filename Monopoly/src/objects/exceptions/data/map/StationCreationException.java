package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The StationCreationException will be thrown if there was a problem while creating a Station object.
 *
 * @author Eyenseo
 * @version 1
 */
public class StationCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public StationCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Station:\n\t" + name :
				      "There should have been data for a Station but there wasn't!", cause);
	}
}
