package objects.exceptions.data;

//JAVADOC
public class MoreThanOneDataSetException extends StorageReaderException {
	//JAVADOC
	public MoreThanOneDataSetException(String name) {
		super("There was a second data set for a Field, but there can be only one" +
		      ((name != null) ? ":\n\t" + name : ""));
	}
}
