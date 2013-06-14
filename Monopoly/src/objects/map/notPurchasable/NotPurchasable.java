package objects.map.notPurchasable;

import objects.map.FieldCircularList;
import objects.value.map.FieldData;
import objects.value.map.NotPurchasableData;

import java.io.Serializable;

/**
 * The NotPurchasable class is the superclass of all fields that are not purchasable.
 */
abstract class NotPurchasable extends FieldCircularList implements Serializable {
	private static final long serialVersionUID = 1987099444819130611L;

	/**
	 * @param name The value determines the name of the object.
	 */
	NotPurchasable(String name) {
		super(name);
	}

	/**
	 * @return The return value is the FieldData of The field with its current attributes
	 */
	@Override public FieldData toFieldData() {
		return new NotPurchasableData(FIELDNUMBER, NAME);
	}
}
