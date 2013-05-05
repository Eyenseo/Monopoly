package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The ParkingCreationException will be thrown if there was a problem while creating a Parking object.
 *
 * @author Eyenseo
 * @version 1
 */
public class ParkingCreationException extends StorageReaderException {

	/**
	 * @param name  The value determines the name of the FieldCircularList.
	 * @param cause The value determines the cause of the Exception.
	 */
	public ParkingCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Parking:\n\t" + name :
				      "There should have been data for Parking but there wasn't!", cause);
	}
}
