package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The TaxCreationException will be thrown if there was a problem while creating a Tax object.
 *
 * @author Eyenseo
 * @version 1
 */
public class TaxCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public TaxCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Tax:\n\t" + name :
				      "There should have been data for a Tax but there wasn't!", cause);
	}
}
