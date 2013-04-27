package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

//JAVADOC
public class ChanceCreationException extends StorageReaderException {
	//JAVADOC
	public ChanceCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Chance:\n\t" + name
		                     : "There should have been data for Chance but there wasn't!", cause);
	}
}
