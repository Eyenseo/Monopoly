package objects.exceptions.map;

import objects.exceptions.StorageReaderException;

//JAVADOC
public class CommunityCreationException extends StorageReaderException {
	//JAVADOC
	public CommunityCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Community:\n\t" + name
		                     : "There should have been data for Community but there wasn't!", cause);
	}
}
