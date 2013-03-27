package objects.value;

/**
 * The Field class is the superclass of all fields.
 *
 * @author Eyenseo
 * @version 0.1
 */
abstract class Field {
	final int    position;
	final String name;
	Field next;

	/**
	 * @param position The value determines the numeric position of the field.
	 * @param name     The value determines the name of the field.
	 */
	Field(int position, String name) {
		this.position = position;
		this.name = name;
		this.next = this;
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

	/**
	 * @param field The value determines the next field.
	 */
	void add(Field field) {
		field.next = this.next;
		this.next = field;
	}

	/**
	 * @return The return value is the name of the next field.
	 */
	Field getNext() {
		return this.next;
	}
}
