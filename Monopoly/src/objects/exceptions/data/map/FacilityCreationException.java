package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The FacilityCreationException will be thrown if there was a problem while creating a Facility object.
 *
 * @author Eyenseo
 * @version 1
 */
public class FacilityCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public FacilityCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Facility:\n\t" + name :
				      "There should have been data for a Facility but there wasn't!", cause);
	}
}
