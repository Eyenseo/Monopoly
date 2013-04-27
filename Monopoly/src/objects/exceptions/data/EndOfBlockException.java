package objects.exceptions.data;

/**
 * The EndOfBlockException is the Exception to be thrown if the end of a data block of the storage should have been
 * reached but weren't.
 *
 * @author Eyenseo
 * @version 1
 */
public class EndOfBlockException extends StorageReaderException {
	/**
	 * @param path The value determines the path to the file that caused the problem.
	 */
	public EndOfBlockException(String path) {
		super("The end statement of a data block was missing in the file:\n\t" + path);
	}
}
