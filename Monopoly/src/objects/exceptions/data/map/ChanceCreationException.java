package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The ChanceCreationException will be thrown if there was a problem while creating a Chance object.
 *
 * @author Eyenseo
 * @version 1
 */
public class ChanceCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public ChanceCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Chance:\n\t" + name :
				      "There should have been data for Chance but there wasn't!", cause);
	}
}
