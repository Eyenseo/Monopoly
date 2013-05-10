package objects.map.notPurchasable;

import java.io.Serializable;

/**
 * The Community Class is the FieldCircularList subclass that holds the chance CardStack.
 *
 * @author Eyenseo
 * @version 1
 */
public class Community extends CardField implements Serializable {
	private static final long serialVersionUID = 420334454093728969L;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public Community(String name) {
		super(name);
	}
}
