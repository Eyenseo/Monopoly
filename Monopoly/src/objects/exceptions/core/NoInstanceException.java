package objects.exceptions.core;

/**
 * The NoInstanceException is thrown if either no Jail, Parking, Go object exists.
 */
public class NoInstanceException extends Exception {
	/**
	 * @param object The value determines the name of the missing Object.
	 */
	public NoInstanceException(String object) {
		super("There was no object of:\n\t" + object);
	}
}
