package core;

import core.data.MapCreator;
import objects.exceptions.map.MapCreationException;
import objects.map.Field;

//TODO Doc
public class Monopoly {

	public static void main(String[] args) {
		Field[] map = null; /* If map is not initialised the value assigned in the try block will be forgotten once
		the try-block is finished */
		try {
			map = new MapCreator().createMap();
		} catch(MapCreationException e) {
			System.out.println(e.getMessageStack());
		}
		System.out.print("Set stop - check the map in debug.");
	}
}