package objects.map.notPurchasable;

import objects.value.map.CommunityData;
import objects.value.map.FieldData;

import java.io.Serializable;

/**
 * The Community Class is the FieldCircularList subclass that holds the chance CardStack.
 */
public class Community extends CardField implements Serializable {
	private static final long serialVersionUID = 420334454093728969L;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public Community(String name) {
		super(name);
	}

	/**
	 * @return The return value is the FieldData of The field with its current attributes
	 */
	@Override public FieldData toFieldData() {
		return new CommunityData(FIELDNUMBER, NAME);
	}
}
