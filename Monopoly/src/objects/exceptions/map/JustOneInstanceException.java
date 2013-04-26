package objects.exceptions.map;

import objects.exceptions.StorageReaderException;

//JAVADOC
public class JustOneInstanceException extends StorageReaderException {
	//JAVADOC
	public JustOneInstanceException(String name) {
		super("There was a second data set for a Field, but there can be only one" +
		      ((name != null) ? ":\n\t" + name : ""));
	}
}
