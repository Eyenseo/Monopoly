package objects.value.connection.data.map;

//JAVADOC
public abstract class FieldData {
	private String className; //TODO is this needed?
	private int    fieldNumber;
	private String name;

	//JAVADOC
	protected FieldData(String className, int fieldNumber, String name) {
		this.className = className;
		this.fieldNumber = fieldNumber;
		this.name = name;
	}

	//JAVADOC
	@Override
	public boolean equals(Object o) {
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
		if(!className.equals(fieldData.className)) {
			return false;
		}
		if(!name.equals(fieldData.name)) {
			return false;
		}

		return true;
	}

	//JAVADOC
	@Override
	public int hashCode() {
		int result = className.hashCode();
		result = 31 * result + fieldNumber;
		result = 31 * result + name.hashCode();
		return result;
	}

	//JAVADOC
	public String getClassName() {
		return className;
	}

	//JAVADOC
	public int getFieldNumber() {
		return fieldNumber;
	}

	//JAVADOC
	public String getName() {
		return name;
	}
}
