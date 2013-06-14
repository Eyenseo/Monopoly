package objects.events;

import java.util.EventObject;

/**
 * The PurchasableEvent is thrown in the gui
 */
public class PurchasableEvent extends EventObject {
	/**
	 * @param source the value determines the source of the event
	 */
	public PurchasableEvent(Object source) {
		super(source);
	}
}
