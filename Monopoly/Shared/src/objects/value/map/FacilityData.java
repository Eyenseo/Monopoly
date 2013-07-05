package objects.value.map;

import objects.value.PlayerData;

/**
 * The FacilityData is a class for the network communication that holds all needed information
 */
//TODO replace this class with a extra attribute in FieldData that holds the typ of Field
public class FacilityData extends PurchasableData {
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
	public FacilityData(int fieldNumber, String name, int[] INCOME, int MORTGAGE, int PRICE, boolean inMortgage,
	                    int stage, PlayerData owner) {
		super(fieldNumber, name, INCOME, MORTGAGE, PRICE, inMortgage, stage, owner);
	}
}
