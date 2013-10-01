package objects.events;

import objects.value.action.HaggleData;

import java.util.EventObject;

/**
 * The ClientOperatorHaggleDataEvent is thrown at the client if there is new HaggleData
 */
public class ClientOperatorHaggleDataEvent extends EventObject {
	private final HaggleData haggleData;

	/**
	 * @param source     the value determines the source of the event
	 * @param haggleData the value determines the HaggleData of the event
	 */
	public ClientOperatorHaggleDataEvent(Object source, HaggleData haggleData) {
		super(source);
		this.haggleData = haggleData;
	}

	/**
	 * @return the return value is the HaggleData of the event
	 */
	public HaggleData getHaggleData() {
		return haggleData;
	}
}
