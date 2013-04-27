package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

//JAVADOC
public class ParkingCreationException extends StorageReaderException {

	//JAVADOC
	public ParkingCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Parking:\n\t" + name
		                     : "There should have been data for Parking but there wasn't!", cause);
	}
}
