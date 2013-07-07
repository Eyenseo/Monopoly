package objects.events;

import objects.value.MassageData;

import java.util.EventObject;

/**
 * The MessageEvent is thrown at the server if there is either chat text or a card text (event or community) has to be
 * transferred
 */
public class MessageEvent extends EventObject {
	MassageData massageData;

	/**
	 * @param source      the value determines the source of the event
	 * @param massageData the value determines the MassageData of the event
	 */
	public MessageEvent(Object source, MassageData massageData) {
		super(source);
		this.massageData = massageData;
	}

	/**
	 * @return the return value is the MassageData of the event
	 */
	public MassageData getMassageData() {
		return massageData;
	}
}
