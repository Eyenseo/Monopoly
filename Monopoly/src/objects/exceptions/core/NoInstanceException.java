package objects.exceptions.core;

//JAVADOC
public class NoInstanceException extends Exception {
	//JAVADOC
	public NoInstanceException(String object) {
		super("There was no object of:\n\t" + object);
	}
}
