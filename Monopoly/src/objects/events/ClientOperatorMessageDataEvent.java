package objects.events;

import objects.value.MassageData;

import java.util.EventObject;

//JAVADOC
public class ClientOperatorMessageDataEvent extends EventObject {
	MassageData massageData;

	public ClientOperatorMessageDataEvent(Object source, MassageData massageData) {
		super(source);
		this.massageData = massageData;
	}

	public MassageData getMassageData() {
		return massageData;
	}
}
