package objects.value.map;

import objects.value.PlayerData;

/**
 * The PurchasableData is a class for the network communication that holds all needed information
 */
public abstract class PurchasableData extends FieldData {
	private final int[]      INCOME;
	private final int        MORTGAGE;
	private final int        PRICE;
	private       boolean    inMortgage;
	private       int        stage;
	private       PlayerData owner;

	/**
	 * @param fieldNumber the value determines the id
	 * @param name        the value determines the name
	 * @param INCOME      the value determines the array with the income stages
	 * @param MORTGAGE    the value determines the mortgage amount
	 * @param PRICE       the value determines the price
	 * @param inMortgage  the value determines the mortgage state
	 * @param stage       the value determines the stage
	 * @param owner       the value determines the owner
	 */
	PurchasableData(int fieldNumber, String name, int[] INCOME, int MORTGAGE, int PRICE, boolean inMortgage, int stage,
	                PlayerData owner) {
		super(fieldNumber, name);
		this.INCOME = INCOME;
		this.MORTGAGE = MORTGAGE;
		this.PRICE = PRICE;
		this.inMortgage = inMortgage;
		this.stage = stage;
		this.owner = owner;
	}

	/**
	 * @return the return value are the income stages
	 */
	public int[] getINCOME() {
		return INCOME;
	}

	/**
	 * @return the return value is the mortgage amount
	 */
	public int getMORTGAGE() {
		return MORTGAGE;
	}

	/**
	 * @return the return value is the price
	 */
	public int getPRICE() {
		return PRICE;
	}

	/**
	 * @return the return value is the mortgage state
	 */
	public boolean isInMortgage() {
		return inMortgage;
	}

	/**
	 * @return the return value is the stage
	 */
	public int getStage() {
		return stage;
	}

	/**
	 * @return the return value is the owner
	 */
	public PlayerData getOwner() {
		return owner;
	}

	/**
	 * @param inMortgage the value determines the mortgage state
	 */
	public void setInMortgage(boolean inMortgage) {
		this.inMortgage = inMortgage;
	}

	/**
	 * @param stage the value determines the stage
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
}
