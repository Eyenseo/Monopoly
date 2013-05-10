package objects.map.notPurchasable;

import objects.map.FieldCircularList;

/**
 * The NotPurchasable class is the superclass of all fields that are not purchasable.
 *
 * @author Eyenseo
 * @version 1
 */
abstract class NotPurchasable extends FieldCircularList {
    /**
     * @param name The value determines the name of the object.
     */
    NotPurchasable(String name) {
        super(name);
    }
}
