package core;

import objects.value.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Eyenseo
 * Date: 26.03.13
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public class Monopoly {

	public static void main(String[] args) {
		Field[] map = new MapCreator().makeMap();
		System.out.print("Set stop - check the map in debug.");
	}
}
