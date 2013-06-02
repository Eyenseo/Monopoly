package objects.value.connection.action;

//JAVADOC
public class MortgageData extends ActionData {
	private final int     fieldId;
	private final boolean redeemMortgage;

	//JAVADOC
	public MortgageData(int playerId, int fieldId, boolean redeemMortgage) {
		super(playerId);
		this.fieldId = fieldId;
		this.redeemMortgage = redeemMortgage;
	}

	//JAVADOC
	public int getFieldId() {
		return fieldId;
	}

	//JAVADOC
	public boolean isRedeemMortgage() {
		return redeemMortgage;
	}
}
