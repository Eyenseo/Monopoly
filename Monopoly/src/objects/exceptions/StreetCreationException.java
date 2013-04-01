package objects.exceptions;

/**
 * The StreetCreationException is the Exception for all Exceptions thrown while creating a Street from storage data.
 *
 * @author Eyenseo
 * @version 1
 */
public class StreetCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name, if it exists, of the Street with the problem.
	 * @param cause The value determines the previous Exception.
	 */
	public StreetCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Street: \"" + name + "\""
				      : "There should have been data for a Street but there wasn't!", cause);
	}
}
