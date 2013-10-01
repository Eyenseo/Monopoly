package objects.exceptions.data;

/**
 * The MoreThanOneDataSetException will be thrown if there was more than one instance of Go, Parking or Jail.
 */
public class MoreThanOneDataSetException extends StorageReaderException {
	/**
	 * @param name The value determines the name of the FieldCircularList object that has a second instance.
	 */
	public MoreThanOneDataSetException(String name) {
		//TODO Name of exception and exception text has to be self explaining
		super("There was a second data set for a FieldCircularList, but there can be only one" +
		      ((name != null) ? ":\n\t" + name : ""));
	}
}
