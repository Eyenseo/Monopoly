package objects.value;

/**
 * The Field class is the superclass of all filds.
 *
 * @author Eyenseo
 * @version 0.1
 */
abstract class Field {
	final int    position;
	final String name;

	/**
	 * @param position The value determines the numeric position of the field.
	 * @param name     The value determines the name of the field.
	 */
	Field(int position, String name) {
		this.position = position;
		this.name = name;
	}

	/**
	 * @return The return value is the numeric position of the field.
	 */
	int getPosition() {
		return this.position;
	}

	/**
	 * @return The return value is the name of the field.
	 */
	String getName() {
		return this.name;
	}
}
