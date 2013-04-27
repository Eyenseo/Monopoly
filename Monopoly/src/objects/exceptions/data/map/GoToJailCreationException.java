package objects.exceptions.data.map;

import objects.exceptions.data.StorageReaderException;

/**
 * The FacilityCreationException is the Exception for all Exceptions thrown while creating a Facility from storage data.
 *
 * @author Eyenseo
 * @version 1
 */
public class GoToJailCreationException extends StorageReaderException {
	/**
	 * @param name  The value determines the name, if it exists, of the Facility with the problem.
	 * @param cause The value determines the previous Exception.
	 */
	public GoToJailCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of GoToJail:\n\t" + name
		                     : "There should have been data for GoToJail but there wasn't!", cause);
	}
}
