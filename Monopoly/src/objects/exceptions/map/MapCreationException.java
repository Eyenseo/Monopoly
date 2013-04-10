package objects.exceptions.map;

import objects.exceptions.StorageReaderException;
import objects.value.map.Field;

/**
 * The MapCreationException is the Exception for all Exceptions thrown while creating a map from storage data.
 *
 * @author Eyenseo
 * @version 1
 */
public class MapCreationException extends StorageReaderException {
	String message = "";

	/**
	 * The message will hold the last two Fileds that were created successfully if possible.
	 *
	 * @param map   The value determines field array that represents the map.
	 * @param cause The value determines the previous Exception.
	 */
	public MapCreationException(Field[] map, Throwable cause) {
		super(cause);
		for(int i = 0; i < map.length; i++) {
			if(map[i] == null) {
				message += "There was an problem while creating Field " + i + ".\n";
				if(i > 1) {
					if(i > 2) {
						message += "\t" + (i - 2) + " - " + map[i - 2].getName() + "\n";
					}
					message += "\t" + (i - 1) + " - " + map[i - 1].getName() + "\n";
				}
				message += "\t" + i + " - ?";
				break;
			}
		}
	}

	/**
	 * @return The return value is the String with detailed information about the problem that occurred.
	 */
	public String getMessage() {
		return message;
	}
}
