package objects.exceptions.data;

/**
 * The StartOfBlockException is the Exception to be thrown if the start of a data block of the storage is missing.
 *
 * @author Eyenseo
 * @version 1
 */
public class StartOfBlockException extends StorageReaderException {
	/**
	 * @param path The value determines the path to the file that caused the problem.
	 */
	public StartOfBlockException(String path) {
		super("The start statement of a data block was missing in the file:\n\t" + path);
	}
}
