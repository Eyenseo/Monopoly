package objects.exceptions;

/**
 * The StationCreationException is the Exception for all Exceptions thrown while creating a Station from storage data.
 *
 * @author Eyenseo
 * @version 1
 */
public class StationCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name, if it exists, of the Station with the problem.
	 * @param cause The value determines the previous Exception.
	 */
	public StationCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Station:\n\t" + name
				      : "There should have been data for a Station but there wasn't!", cause);
	}
}
