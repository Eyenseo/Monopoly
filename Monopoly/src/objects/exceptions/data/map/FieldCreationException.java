package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The FieldCreationException is the Exception for all Exceptions thrown while creating a Field from storage data.
 *
 * @author Eyenseo
 * @version 1
 */
public class FieldCreationException extends StorageReaderException {
	//JAVADOC
	public FieldCreationException(String word) {
		super("There was an undefined control word:\n\t" + word);
	}
}
