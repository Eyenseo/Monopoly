package objects.exceptions.core;

import objects.exceptions.MessageStackException;

/**
 * The LoaderException will be thrown if there is a problem while loading files from the Loader
 */
public class LoaderException extends MessageStackException {
	/**
	 * @param message the value determines the message to regarding the error
	 * @param cause   the value determines the cause of the error
	 */
	public LoaderException(String message, Throwable cause) {
		super(message, cause);
	}
}
