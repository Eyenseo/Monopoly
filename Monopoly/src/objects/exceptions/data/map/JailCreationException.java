package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

//JAVADOC
public class JailCreationException extends StorageReaderException {
	//JAVADOC
	public JailCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Jail:\n\t" + name
		                     : "There should have been data for Jail but there wasn't!", cause);
	}
}
