package objects.listeners;

import objects.events.ClientOperatorHaggleDataEvent;

/**
 * The ClientOperatorHaggleDataEventListener is the listener for a ClientOperatorHaggleDataEvent
 */
public interface ClientOperatorHaggleDataEventListener {
               	/**
	 * The method will be performed if a ClientOperatorHaggleDataEvent happens
	 *
	 * @param event the value determines the event
	 */
	public void actionPerformed(ClientOperatorHaggleDataEvent event);
}
