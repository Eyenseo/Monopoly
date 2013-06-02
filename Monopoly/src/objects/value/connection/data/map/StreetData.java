package objects.value.connection.data.map;

import objects.value.connection.data.PlayerData;

//JAVADOC
public class StreetData extends PurchasableData {
	private final int[] COLOR;
	private final int   UPGRADE;

	//JAVADOC
	public StreetData(String className, int fieldNumber, String name, int[] INCOME, int MORTGAGE, int PRICE,
	                  boolean inMortgage, int stage, PlayerData owner, int[] COLOR, int UPGRADE) {
		super(className, fieldNumber, name, INCOME, MORTGAGE, PRICE, inMortgage, stage, owner);
		this.COLOR = COLOR;
		this.UPGRADE = UPGRADE;
	}

	//JAVADOC
	public int[] getCOLOR() {
		return COLOR;
	}

	//JAVADOC
	public int getUPGRADE() {
		return UPGRADE;
	}
}
