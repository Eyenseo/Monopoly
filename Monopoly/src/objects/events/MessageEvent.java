package objects.events;

import objects.value.MassageData;

import java.util.EventObject;

//JAVADOC
public class MessageEvent extends EventObject {
	MassageData massageData;

	public MessageEvent(Object source, MassageData massageData) {
		super(source);
		this.massageData = massageData;
	}

	public MassageData getMassageData() {
		return massageData;
	}
}
