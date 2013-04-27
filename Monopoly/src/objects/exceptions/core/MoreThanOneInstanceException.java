package objects.exceptions.core;

//JAVADOC
public class MoreThanOneInstanceException extends Exception {
	//JAVADOC
	public MoreThanOneInstanceException(String name) {
		super("There was a second data set for a Field, but there can be only one" +
		      ((name != null) ? ":\n\t" + name : ""));
	}
}
