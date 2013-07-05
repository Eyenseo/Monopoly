package objects.listeners;

import objects.events.ModelEvent;

/**
 * The ModelEventListener is the listener for a ModelEvent
 */
public interface ModelEventListener {
	/**
	 * The method will be performed if a ModelEvent happens
	 *
	 * @param event the value determines the event
	 */
	public void actionPerformed(ModelEvent event);
}
