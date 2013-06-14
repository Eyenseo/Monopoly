package objects.listeners;

import objects.events.PlayerEvent;

/**
 * The PlayerEventListener is the listener for a PlayerEvent
 */
public interface PlayerEventListener {
	/**
	 * The method will be performed if a PlayerEvent happens
	 *
	 * @param event the value determines the event
	 */
	public void actionPerformed(PlayerEvent event);
}
