package core;

import objects.value.*;

import java.io.IOException;

/**
 * The MapCreator class provides the method to create the map out of the data in the storage package.
 *
 * @author Eyenseo
 * @version 1
 */
class MapCreator extends StorageReader {
	private StreetCircularList   streetGroupBuffer   = null;
	private StationCircularList  stationGroupBuffer  = null;
	private FacilityCircularList facilityGroupBuffer = null;

	//TODO constructor with parameter with the Community-Queue and the Chance-Queue for the Community and Chance Fields
	MapCreator() {
		//TODO Path may be different if run as package.
		super("./Monopoly/src/storage/map.txt");
	}

	/**
	 * @return The return value is the finished map. A Field array with 40 length and different objects of the
	 *         objects.value package.
	 */
	//TODO Exception handling
	//TODO Method to create map from save
	Field[] createMap() {
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
	 * @return The return value is the next Field of the map.
	 *
	 * @throws IOException
	 */
	private Field nextField() throws IOException {
		String line;
		int number;
		while((line = nextString()) != null) {
			number = Integer.parseInt(line);
			switch(number) {
				case 0:
					return createStreet();
				case 1:
					return createStation();
				case 2:
					return createChance();
				case 3:
					return createCommunity();
				case 4:
					return createTax();
				case 5:
					return createFacility();
				case 6:
					return createGo();
				case 7:
					return createJail();
				case 8:
					return createParking();
				case 9:
					return createGoToJail();
			}
		}
		return null;
	}

	/**
	 * This method connects the streets of the same color while creating them.
	 *
	 * @return The return value is a StreetCircularList object based on the data in the storage package.
	 *
	 * @throws IOException
	 */
	private StreetCircularList createStreet() throws IOException {
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
		StreetCircularList street = new StreetCircularList(name, price, income, mortgage, 0, null, upgrade, color);
		connectStreet(street);
		return street;
	}

	/**
	 * This method checks the last StreetCircularList object was already of it's color, if it is it connects the them,
	 * if not the StreetCircularList object in the parameter is the 'start' of a new ring list.
	 *
	 * @param street The value determines the StreetCircularList object which will check if it belongs to the previous
	 *               StreetCircularList object.
	 */
	void connectStreet(StreetCircularList street) {
		if(streetGroupBuffer != null && streetGroupBuffer.isSameColor(street)) {
			streetGroupBuffer.add(street);
		} else {  // The else block is for performance by letting go of the 'order' from the circular list
			streetGroupBuffer = street;
		}
	}

	/**
	 * This method connects the new station with the other if other exist.
	 *
	 * @return The return value is a StationCircularList object based on the data in the storage package.
	 *
	 * @throws IOException
	 */
	private StationCircularList createStation() throws IOException {
		String name = nextString();
		int price = nextInt();
		int[] income = new int[4];
		for(int i = 0; i < income.length; i++) {
			income[i] = nextInt();
		}
		int mortgage = nextInt();
		//TODO get owner and stage from a save file
		StationCircularList station = new StationCircularList(name, price, income, mortgage, 0, null);
		connectStation(station);
		return station;
	}

	/**
	 * This method connects the station with the next one if there was already one.
	 *
	 * @param station The value determines the StationCircularList object that will be added to the other
	 *                StationCircularList objects.
	 */
	private void connectStation(StationCircularList station) {
		if(stationGroupBuffer != null) {
			stationGroupBuffer.add(station);
		} else {// The else block is for performance by letting go of the 'order' from the circular list
			stationGroupBuffer = station;
		}
	}

	/**
	 * This method connects the new facility with the other if other exist.
	 *
	 * @return The return value is a FacilityCircularList object based on the data in the storage package.
	 *
	 * @throws IOException
	 */
	private FacilityCircularList createFacility() throws IOException {
		String name = nextString();
		int price = nextInt();
		int[] income = {nextInt(), nextInt()};
		int mortgage = nextInt();
		//TODO get owner and stage from a save file
		FacilityCircularList facility = new FacilityCircularList(name, price, income, mortgage, 0, null);
		connectFacility(facility);
		return facility;
	}

	/**
	 * This method connects the facility with the next one if there was already one.
	 *
	 * @param facility The value determines the FacilityCircularList object that will be added to the other
	 *                 FacilityCircularList objects.
	 */
	private void connectFacility(FacilityCircularList facility) {
		if(facilityGroupBuffer != null) {
			facilityGroupBuffer.add(facility);
		} else {// The else block is for performance by letting go of the 'order' from the circular list
			facilityGroupBuffer = facility;
		}
	}

	/**
	 * @return The return value is a Chance object based on the data in the storage package.
	 */
	private Chance createChance() {
		return new Chance();
	}

	/**
	 * @return The return value is a Community object based on the data in the storage package.
	 */
	private Community createCommunity() {
		return new Community();
	}

	/**
	 * @return The return value is a Tax object based on the data in the storage package.
	 */
	private Tax createTax() throws IOException {
		String name = nextString();
		int bill = nextInt();
		return new Tax(name, bill);
	}

	/**
	 * @return The return value is a Go object based on the data in the storage package.
	 */
	private Go createGo() {
		return new Go();
	}

	/**
	 * @return The return value is a Jail object based on the data in the storage package.
	 */
	private Jail createJail() {
		return new Jail();
	}

	/**
	 * @return The return value is a Parking object based on the data in the storage package.
	 */
	private Parking createParking() {
		return new Parking();
	}

	/**
	 * @return The return value is a GoToJail object based on the data in the storage package.
	 */
	private GoToJail createGoToJail() {
		return new GoToJail();
	}
}
