package objects.exceptions.data;

/**
 * The CreationException will be thrown if there was a problem while creating a object.
 *
 * @author Eyenseo
 * @version 1
 */
public class CreationException extends StorageReaderException {
	/**
	 * @param objectName The value determines the name of the object to be created.
	 * @param className  The value determines the class that failed to be created
	 * @param cause      The value determines the cause of the Exception.
	 */
	public CreationException(String objectName, String className, Throwable cause) {
		super((objectName != null) ?
				      "Something was missing while reading the data of " + className + ":\n\t" + objectName :
				      "There should have been data for " + className + " but there wasn't!", cause);
	}
}
