package objects.listeners;

import objects.events.ClientOperatorPurchasableDataEvent;

/**
 * The ClientOperatorPurchasableDataEventListener is the listener for a ClientOperatorPurchasableDataEvent
 */
public interface ClientOperatorPurchasableDataEventListener {
	/**
	 * The method will be performed if a ClientOperatorPurchasableDataEvent happens
	 *
	 * @param event the value determines the event
	 */
	public void actionPerformed(ClientOperatorPurchasableDataEvent event);
}
