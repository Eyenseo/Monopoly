package objects.map.notPurchasable;

import java.io.Serializable;

/**
 * The Chance Class is the FieldCircularList subclass that holds the chance CardStack.
 *
 * @author Eyenseo
 * @version 1
 */
public class Chance extends CardField implements Serializable {
	private static final long serialVersionUID = 3091888401355665603L;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public Chance(String name) {
		super(name);
	}
}
