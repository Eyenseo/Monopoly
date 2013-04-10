package objects.exceptions.map;

import objects.exceptions.StorageReaderException;

/**
 * The FieldCreationException is the Exception for all Exceptions thrown while creating a Field from storage data.
 *
 * @author Eyenseo
 * @version 1
 */
public class FieldCreationException extends StorageReaderException {
	public FieldCreationException() {
		super("There was an undefined control word");
	}
}
