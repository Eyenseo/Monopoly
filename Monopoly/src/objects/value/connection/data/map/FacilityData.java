package objects.value.connection.data.map;

import objects.value.connection.data.PlayerData;

//JAVADOC
//TODO replace this class with a extra attribute in FieldData that holds the typ of Field
public class FacilityData extends PurchasableData {
	//JAVADOC
	public FacilityData(String className, int fieldNumber, String name, int[] INCOME, int MORTGAGE, int PRICE,
	                    boolean inMortgage, int stage, PlayerData owner) {
		super(className, fieldNumber, name, INCOME, MORTGAGE, PRICE, inMortgage, stage, owner);
	}
}
