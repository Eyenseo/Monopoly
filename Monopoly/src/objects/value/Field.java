package objects.value;

/**
 * The Field class is the superclass of all fields.
 *
 * @author Eyenseo
 * @version 0.1
 */
public abstract class Field {
	final String NAME;

	/**
	 * @param name The value determines the name of the field.
	 */
	Field(String name) {
		this.NAME = name;
	}

	/**
	 * @return The return value is the name of the field.
	 */
	String getName() {
		return this.NAME;
	}
}
