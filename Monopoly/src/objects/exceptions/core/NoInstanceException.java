package objects.exceptions.core;

import objects.exceptions.MessageStackException;

/**
 * The NoInstanceException is thrown if either no Jail, Parking, Go object exists.
 */
public class NoInstanceException extends MessageStackException {
	/**
	 * @param object The value determines the name of the missing Object.
	 */
	public NoInstanceException(String object) {
		super("There was no object of:\n\t" + object);
	}
}
