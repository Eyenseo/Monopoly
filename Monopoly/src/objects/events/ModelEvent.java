package objects.events;

import java.util.EventObject;

/**
 * The ModelEvent is thrown in the gui
 */
public class ModelEvent extends EventObject {
	/**
	 * @param source the value determines the source of the event
	 */
	public ModelEvent(Object source) {
		super(source);
	}
}
