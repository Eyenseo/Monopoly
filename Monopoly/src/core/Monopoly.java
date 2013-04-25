package core;

import core.data.MapCreator;
import objects.exceptions.StorageReaderException;
import objects.map.Field;

//TODO Doc
public class Monopoly {

	public static void main(String[] args) {
		Field[] map = null; /* If map is not initialised the value assigned in the try block will be forgotten once
		the try-block is finished */
		try {
			map = new MapCreator().createMap();
		} catch(StorageReaderException e) {
			System.out.println(e.getMessageStack());
		}
		System.out.print("Set stop - check the map in debug.");
	}
}