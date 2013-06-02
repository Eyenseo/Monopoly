package objects.value.connection.data.map;

import objects.value.connection.data.PlayerData;

//JAVADOC
public class PurchasableData extends FieldData {
	private final int[]      INCOME;
	private final int        MORTGAGE;
	private final int        PRICE;
	private       boolean    inMortgage;
	private       int        stage;
	private       PlayerData owner;

	//JAVADOC
	public PurchasableData(String className, int fieldNumber, String name, int[] INCOME, int MORTGAGE, int PRICE,
	                       boolean inMortgage, int stage, PlayerData owner) {
		super(className, fieldNumber, name);
		this.INCOME = INCOME;
		this.MORTGAGE = MORTGAGE;
		this.PRICE = PRICE;
		this.inMortgage = inMortgage;
		this.stage = stage;
		this.owner = owner;
	}

	//JAVADOC
	public int[] getINCOME() {
		return INCOME;
	}

	//JAVADOC
	public int getMORTGAGE() {
		return MORTGAGE;
	}

	//JAVADOC
	public int getPRICE() {
		return PRICE;
	}

	//JAVADOC
	public boolean isInMortgage() {
		return inMortgage;
	}

	//JAVADOC
	public int getStage() {
		return stage;
	}

	//JAVADOC
	public PlayerData getOwner() {
		return owner;
	}

	//JAVADOC
	public void setInMortgage(boolean inMortgage) {
		this.inMortgage = inMortgage;
	}

	//JAVADOC
	public void setStage(int stage) {
		this.stage = stage;
	}

	//JAVADOC
	public void setOwner(PlayerData owner) {
		this.owner = owner;
	}
}
