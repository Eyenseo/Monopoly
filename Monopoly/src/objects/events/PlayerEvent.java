package objects.events;

import objects.Player;

import java.util.EventObject;

/**
 * The PlayerEvent is thrown in the gui
 */
public class PlayerEvent extends EventObject {
	/**
	 * @param source the value determines the source of the event
	 */
	public PlayerEvent(Player source) {
		super(source);
	}
}
