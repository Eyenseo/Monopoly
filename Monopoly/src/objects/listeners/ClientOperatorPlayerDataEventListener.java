package objects.listeners;

import objects.events.ClientOperatorPlayerDataEvent;

/**
 * The ClientOperatorPlayerDataEventListener is the listener for a ClientOperatorPlayerDataEvent
 */
public interface ClientOperatorPlayerDataEventListener {
	/**
	 * The method will be performed if a ClientOperatorPlayerDataEvent happens
	 *
	 * @param event the value determines the event
	 */
	public void actionPerformed(ClientOperatorPlayerDataEvent event);
}
