package objects.listeners;

import objects.events.MessageEvent;

/**
 * The MessageEventListener is the listener for a MessageEvents
 */
public interface MessageEventListener {
	/**
	 * The method will be performed if a MessageEvent happens
	 *
	 * @param event the value determines the event
	 */
	public void actionPerformed(MessageEvent event);
}
