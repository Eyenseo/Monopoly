package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The GoToJailCreationException will be thrown if there was a problem while creating a GoToJail object.
 *
 * @author Eyenseo
 * @version 1
 */
public class GoToJailCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public GoToJailCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of GoToJail:\n\t" + name :
				      "There should have been data for GoToJail but there wasn't!", cause);
	}
}
