package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The CommunityCreationException will be thrown if there was a problem while creating a Community object.
 *
 * @author Eyenseo
 * @version 1
 */
public class CommunityCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public CommunityCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Community:\n\t" + name :
				      "There should have been data for Community but there wasn't!", cause);
	}
}
