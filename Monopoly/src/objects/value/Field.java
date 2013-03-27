package objects.value;

/**
 * The Field class is the superclass of all fields.
 *
 * @author Eyenseo
 * @version 0.1
 */
abstract class Field {
	final String name;

	/**
	 * @param name The value determines the name of the field.
	 */
	Field(String name) {
		this.name = name;
	}

	/**
	 * @return The return value is the name of the field.
	 */
	String getName() {
		return this.name;
	}
}
