package objects.map.notPurchasable;

import objects.map.FieldCircularList;

import java.io.Serializable;

/**
 * The NotPurchasable class is the superclass of all fields that are not purchasable.
 *
 * @author Eyenseo
 * @version 1
 */
abstract class NotPurchasable extends FieldCircularList implements Serializable {
	private static final long serialVersionUID = 1987099444819130611L;

	/**
	 * @param name The value determines the name of the object.
	 */
	NotPurchasable(String name) {
		super(name);
	}
}
