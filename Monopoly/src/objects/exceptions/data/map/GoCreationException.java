package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The GoCreationException will be thrown if there was a problem while creating a Go object.
 *
 * @author Eyenseo
 * @version 1
 */
public class GoCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public GoCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Go:\n\t" + name :
				      "There should have been data for Go but there wasn't!", cause);
	}
}
