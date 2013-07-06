package objects.map.notPurchasable;

import objects.value.map.ChanceData;
import objects.value.map.FieldData;

import java.io.Serializable;

/**
 * The Chance Class is the FieldCircularList subclass that holds the chance CardStack.
 */
public class Chance extends CardField implements Serializable {
	private static final long serialVersionUID = 3091888401355665603L;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public Chance(String name) {
		super(name);
	}

	/**
	 * @return The return value is the FieldData of The field with its current attributes
	 */
	@Override public FieldData toFieldData() {
		return new ChanceData(FIELDNUMBER, NAME);
	}
}
