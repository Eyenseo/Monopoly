package objects.exceptions.data;

/**
 * The ControlWordNotFoundException will be thrown if a control word is not found.
 */
public class ControlWordNotFoundException extends StorageReaderException {
	/**
	 * @param word The value determines the control world that wasn't found.
	 */
	public ControlWordNotFoundException(String word) {
		super("There was an undefined control word:\n\t" + word);
	}
}
