package objects.value;

/**
 * The NotPurchasable class is the superclass of all fields that are not purchasable.
 *
 * @author Eyenseo
 * @version 0.1
 */
abstract class NotPurchasable extends Field {
	/**
	 * @param position The value determines the numeric position of the field.
	 * @param name     The value determines the name of the field.
	 */
	NotPurchasable(int position, String name) {
		super(position, name);
	}
}
