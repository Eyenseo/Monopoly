package objects.exceptions;

/**
 * The MapCreationException is the Exception for all Exceptions thrown while creating a map from storage data.
 *
 * @author Eyenseo
 * @version 1
 */
public class MapCreationException extends StorageReaderException {
	/**
	 * @param number The value determines the number of the filed that had the Problem.
	 * @param cause  The value determines the previous Exception.
	 */
	public MapCreationException(int number, Throwable cause) {
		super("There was an problem while creating Field " + number + " of the map.", cause);
	}
}
