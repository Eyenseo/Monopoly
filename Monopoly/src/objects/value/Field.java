package objects.value;

/**
 * @author Eyenseo
 * @version 0.1
 */
public abstract class Field {
	private final String NAME;

	/**
	 * @param name The value determines the name of the object.
	 */
	Field(String name) {
		this.NAME = name;
	}

	/**
	 * @return The return value is the name.
	 */
	public String getName() {
		return this.NAME;
	}
}
