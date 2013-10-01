package objects.listeners;

import objects.events.ClientOperatorMessageDataEvent;

/**
 * The ClientOperatorMessageDataEventListener is the listener for a ClientOperatorMessageDataEvent
 */
public interface ClientOperatorMessageDataEventListener {
	/**
	 * The method will be performed if a ClientOperatorMessageDataEvent happens
	 *
	 * @param event the value determines the event
	 */
	public void actionPerformed(ClientOperatorMessageDataEvent event);
}
