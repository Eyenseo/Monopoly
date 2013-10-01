package objects.value.map;

import objects.value.PlayerData;

/**
 * The StreetData is a class for the network communication that holds all needed information
 */
public class StreetData extends PurchasableData {
	private final int[]   COLOR;
	private final int     UPGRADE;
	private       boolean upgradeable;

	/**
	 * @param fieldNumber the value determines the id
	 * @param name        the value determines the name
	 * @param INCOME      the value determines the array with the income stages
	 * @param MORTGAGE    the value determines the mortgage amount
	 * @param PRICE       the value determines the price
	 * @param inMortgage  the value determines the mortgage state
	 * @param stage       the value determines the stage
	 * @param owner       the value determines the owner
	 * @param COLOR       the value determines the color
	 * @param UPGRADE     the value determines the price to pay for an update
	 */
	public StreetData(int fieldNumber, String name, int[] INCOME, int MORTGAGE, int PRICE, boolean inMortgage,
	                  int stage, PlayerData owner, int[] COLOR, int UPGRADE, boolean upgradeable) {
		super(fieldNumber, name, INCOME, MORTGAGE, PRICE, inMortgage, stage, owner);
		this.COLOR = COLOR;
		this.UPGRADE = UPGRADE;
		this.upgradeable = upgradeable;
	}

	/**
	 * @return the return value is the color
	 */
	public int[] getCOLOR() {
		return COLOR;
	}

	/**
	 * @return the return value is the upgrade price
	 */
	public int getUPGRADE() {
		return UPGRADE;
	}

	/**
	 * @return The return value is true if the Street is upgradeable
	 */
	public boolean isUpgradeable() {
		return upgradeable;
	}

	/**
	 * @param upgradeable The value determines the upgradeable state of the Street
	 */
	public void setUpgradeable(boolean upgradeable) {
		this.upgradeable = upgradeable;
	}
}
