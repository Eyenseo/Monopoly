package objects.value.map;

/**
 * The NotPurchasable class is the superclass of all fields that are not purchasable.
 *
 * @author Eyenseo
 * @version 0.1
 */
abstract class NotPurchasable extends Field {
	/**
	 * @param name The value determines the name of the object.
	 */
	NotPurchasable(String name) {
		super(name);
	}
}
