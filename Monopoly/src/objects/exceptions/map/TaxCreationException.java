package objects.exceptions.map;

import objects.exceptions.StorageReaderException;

/**
 * The TaxCreationException is the Exception for all Exceptions thrown while creating a Tax from storage data.
 *
 * @author Eyenseo
 * @version 1
 */
public class TaxCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name, if it exists, of the Tax with the problem.
	 * @param cause The value determines the previous Exception.
	 */
	public TaxCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Tax:\n\t" + name
				      : "There should have been data for a Tax but there wasn't!", cause);
	}
}
