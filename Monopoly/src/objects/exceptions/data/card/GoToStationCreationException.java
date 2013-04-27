package objects.exceptions.data.card;

import objects.exceptions.data.StorageReaderException;
//JAVADOC

public class GoToStationCreationException extends StorageReaderException {
	//JAVADOC
	public GoToStationCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of a GoToStationCard:\n\t" + name
		                     : "There should have been data for a card but there wasn't!", cause);
	}
}
