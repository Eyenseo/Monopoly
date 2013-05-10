package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The JailCreationException will be thrown if there was a problem while creating a Jail object.
 *
 * @author Eyenseo
 * @version 1
 */
public class JailCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public JailCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Jail:\n\t" + name :
				      "There should have been data for Jail but there wasn't!", cause);
	}
}
