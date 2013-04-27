package objects.exceptions.data;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The StorageReaderException is the basic Exception for all Exceptions thrown while reading out of the storage.
 *
 * @author Eyenseo
 * @version 1
 */
public class StorageReaderException extends Exception {

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

	/**
	 * @return The return value is the Message of this and all the previous Exceptions and the StackTrace of the
	 *         last cause.
	 */
	public String getMessageStack() {
		String message = getMessage() + '\n';
		if(this.getCause() != null) {
			message += getCauseMessage();
		}
		return message;
	}

	/**
	 * @return The return value is the Message of all the previous Exceptions and the StackTrace of the last cause.
	 */
	private String getCauseMessage() {
		return getCauseMessage((StorageReaderException) this.getCause());
	}

	/**
	 * @param cause The value determines the next current Exception.
	 * @return The return value is the Message of all the previous Exceptions and the StackTrace of the last cause.
	 */
	private String getCauseMessage(StorageReaderException cause) {
		String message = null;
		//if(cause.getCause() != null && cause.getCause() instanceof StorageReaderException) {
		if(cause.getCause() != null) {
			message = cause.getMessage() + '\n';
			message += cause.getCauseMessage((StorageReaderException) cause.getCause());
		} else {
			//TODO understand StringWriter and PrintWriter
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			cause.printStackTrace(pw);
			message += sw.toString();
		}
		return message + "\n";
	}
}
