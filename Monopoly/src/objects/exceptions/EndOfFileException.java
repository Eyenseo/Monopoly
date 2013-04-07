package objects.exceptions;

/**
 * The EndOfFileException is the Exception if a file ends unexpected..
 *
 * @author Eyenseo
 * @version 1
 */
public class EndOfFileException extends StorageReaderException {
	/**
	 * @param path The value determines the path to the file that caused the problem.
	 */
	public EndOfFileException(String path) {
		super("End of file:\n\t" + path);
	}
}
