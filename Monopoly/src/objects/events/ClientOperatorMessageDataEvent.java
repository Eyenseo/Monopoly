package objects.events;

import objects.value.MassageData;

import java.util.EventObject;

/**
 * The ClientOperatorMessageDataEvent is thrown at the client if there is new MessageData
 */
public class ClientOperatorMessageDataEvent extends EventObject {
	MassageData massageData;

	/**
	 * @param source      the value determines the source of the event
	 * @param massageData the value determines the MassageData of the event
	 */
	public ClientOperatorMessageDataEvent(Object source, MassageData massageData) {
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
