package objects.value.connection.action;

//JAVADOC
public class BuyData extends ActionData {
	private final int     fieldId;
	private final boolean upgrade;

	//JAVADOC
	public BuyData(int playerId, int fieldId, boolean upgrade) {
		super(playerId);
		this.fieldId = fieldId;
		this.upgrade = upgrade;
	}

	//JAVADOC
	public int getFieldId() {
		return fieldId;
	}

	//JAVADOC
	public boolean isUpgrade() {
		return upgrade;
	}
}
