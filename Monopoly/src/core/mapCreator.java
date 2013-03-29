package core;

import objects.value.*;

import java.io.IOException;

/**
 * The MapCreator class provides the method to create the map out of the data in the storage package.
 *
 * @author Eyenseo
 * @version 1
 */
public class MapCreator extends StorageReader {
	private Street streetGroupBuffer = null;

	//TODO constructor with parameter with the Community-Queue and the Chance-Queue for the Community and Chance Fields
	public MapCreator() {
		//TODO Path may be different if run as package.
		super("./Monopoly/src/storage/map.txt");
	}

	/**
	 * @return The return value is the finished map
	 */
	//TODO Exception handling
	Field[] makeMap() {
		Field[] map = new Field[40];
		for(int i = 0; i < map.length; i++) {
			try {
				map[i] = nextField();
			} catch(IOException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
		return map;
	}

	/**
	 * @return Returns the next Field of the Map
	 * @throws IOException
	 */
	private Field nextField() throws IOException {
		String line;
		int number;
		while((line = nextString()) != null) {
			number = Integer.parseInt(line);
			switch(number) {
				case 0:
					return makeStreet();
				case 1:
					return makeStation();
				case 2:
					return makeChance();
				case 3:
					return makeCommunity();
				case 4:
					return makeTax();
				case 5:
					return makeFacility();
				case 6:
					return makeGo();
				case 7:
					return makeJail();
				case 8:
					return makeParking();
				case 9:
					return makeGoToJail();
			}
		}
		return null;
	}

	/**
	 * @return The return value is a Street object based on the data in the storage package.
	 * @throws IOException
	 */
	private Street makeStreet() throws IOException {
		Street street;
		String name = nextString();
		int[] color = new int[3];
		for(int i = 0; i < color.length; i++) {
			color[i] = nextInt();
		}
		int price = nextInt();
		int[] income = new int[6];
		for(int i = 0; i < income.length; i++) {
			income[i] = nextInt();
		}
		int upgrade = nextInt();
		int mortgage = nextInt();
		//TODO get owner and stage from a save file
		street = new Street(name, price, income, mortgage, 0, null, upgrade, color);
		connectStreet(street);
		return street;
	}

	/**
	 * This method checks the last Street was already of it's color, if it is it connects the them,
	 * if not the Street in the parameter is the 'star' of a new ring list.
	 *
	 * @param street The value determines the Street which will check if it belongs to the previous Street.
	 */
	void connectStreet(Street street) {
		if(streetGroupBuffer != null && streetGroupBuffer.isSameColor(street)) {
			streetGroupBuffer.add(street);
		} else {
			streetGroupBuffer = street;
		}
	}

	/**
	 * @return The return value is a Station object based on the data in the storage package.
	 * @throws IOException
	 */
	private Station makeStation() throws IOException {
		String name = nextString();
		int price = nextInt();
		int[] income = new int[4];
		for(int i = 0; i < income.length; i++) {
			income[i] = nextInt();
		}
		int mortgage = nextInt();
		//TODO get owner and stage from a save file
		return new Station(name, price, income, mortgage, 0, null);
	}

	/**
	 * @return The return value is a Chance object based on the data in the storage package.
	 * @throws IOException
	 */
	private Chance makeChance() {
		return new Chance();
	}

	/**
	 * @return The return value is a Community object based on the data in the storage package.
	 * @throws IOException
	 */
	private Community makeCommunity() {
		return new Community();
	}

	/**
	 * @return The return value is a Tax object based on the data in the storage package.
	 * @throws IOException
	 */
	private Tax makeTax() throws IOException {
		String name = nextString();
		int bill = nextInt();
		return new Tax(name, bill);
	}

	/**
	 * @return The return value is a Facility object based on the data in the storage package.
	 * @throws IOException
	 */
	private Facility makeFacility() throws IOException {
		String name = nextString();
		int price = nextInt();
		int[] income = {nextInt(), nextInt()};
		int mortgage = nextInt();
		//TODO get owner and stage from a save file
		return new Facility(name, price, income, mortgage, 0, null);
	}

	/**
	 * @return The return value is a Go object based on the data in the storage package.
	 * @throws IOException
	 */
	private Go makeGo() {
		return new Go();
	}

	/**
	 * @return The return value is a Jail object based on the data in the storage package.
	 * @throws IOException
	 */
	private Jail makeJail() {
		return new Jail();
	}

	/**
	 * @return The return value is a Parking object based on the data in the storage package.
	 * @throws IOException
	 */
	private Parking makeParking() {
		return new Parking();
	}

	/**
	 * @return The return value is a GoToJail object based on the data in the storage package.
	 * @throws IOException
	 */
	private GoToJail makeGoToJail() {
		return new GoToJail();
	}
}
