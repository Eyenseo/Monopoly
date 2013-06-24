package objects.value.action;

/**
 * The MortgageData is an object with all information regarding the mortgage of a a field
 */
public class MortgageData extends ActionData {
	private final int fieldId;

	/**
	 * @param userId  the value determines the player Id the data original from
	 * @param fieldId the value determines the id of the field that will change the mortgage stage
	 */
	public MortgageData(int userId, int fieldId) {
		super(userId);
		this.fieldId = fieldId;
	}

	/**
	 * @return the return value is the id of the field
	 */
	public int getFieldId() {
		return fieldId;
	}
}
