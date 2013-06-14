package objects.value.map;

import objects.value.PlayerData;

/**
 * The StationData is a class for the network communication that holds all needed information
 */
public class StationData extends PurchasableData {
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
	public StationData(int fieldNumber, String name, int[] INCOME, int MORTGAGE, int PRICE, boolean inMortgage,
	                   int stage, PlayerData owner) {
		super(fieldNumber, name, INCOME, MORTGAGE, PRICE, inMortgage, stage, owner);
	}
}
