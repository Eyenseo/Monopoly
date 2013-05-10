package objects.exceptions.data;

import objects.exceptions.MessageStackException;

/**
 * The StorageReaderException is the basic Exception for all Exceptions thrown while reading out of the storage.
 *
 * @author Eyenseo
 * @version 1
 */
public class StorageReaderException extends MessageStackException {
	/**
	 * @param message The value determines the message that will be displayed when viewing the error report.
	 */
	public StorageReaderException(String message) {
		super(message);
	}

	/**
	 * @param message The value determines the message that will be displayed when viewing the error report.
	 * @param cause   The value determines the previous Exceptions.
	 */
	public StorageReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause The value determines the previous Exceptions.
	 */
	public StorageReaderException(Throwable cause) {
		super("There was an IOException while reading a line of a file from the storage package!", cause);
	}
}
