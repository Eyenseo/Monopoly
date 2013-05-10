package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The StreetCreationException will be thrown if there was a problem while creating a Street object.
 *
 * @author Eyenseo
 * @version 1
 */
public class StreetCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public StreetCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of street:\n\t" + name :
				      "There should have been data for a street but there wasn't!", cause);
	}
}
