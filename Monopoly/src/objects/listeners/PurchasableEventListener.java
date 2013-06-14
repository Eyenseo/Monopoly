package objects.listeners;

import objects.events.PurchasableEvent;

/**
 * The PurchasableEventListener is the listener for a PurchasableEvent
 */
public interface PurchasableEventListener {
	/**
	 * The method will be performed if a PurchasableEvent happens
	 *
	 * @param event the value determines the event
	 */
	public void actionPerformed(PurchasableEvent event);
}
