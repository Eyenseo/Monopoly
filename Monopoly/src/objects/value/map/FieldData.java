package objects.value.map;

/**
 * The FieldData is a class for the network communication that holds all needed information
 */
public abstract class FieldData {
	private final int    fieldNumber;
	private final String name;

	/**
	 * @param fieldNumber the value determines the id
	 * @param name        the value determines the name
	 */
	FieldData(int fieldNumber, String name) {
		this.fieldNumber = fieldNumber;
		this.name = name;
	}

	/**
	 * two FieldData objects are the same if their fieldNumbers and names are the same
	 *
	 * @param o the value determines the object to be checked against
	 * @return the return value is true if the objects are the same
	 */
	@Override public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}

		FieldData fieldData = (FieldData) o;

		if(fieldNumber != fieldData.fieldNumber) {
			return false;
		}
		if(!name.equals(fieldData.name)) {
			return false;
		}

		return true;
	}

	/**
	 * @return the return value is the field number
	 */
	public int getFieldNumber() {
		return fieldNumber;
	}

	/**
	 * @return the return value is the field name
	 */
	public String getName() {
		return name;
	}
}
