package core;

import objects.exceptions.*;
import objects.value.*;

/**
 * The MapCreator class provides the method to create the map out of the data in the storage package.
 *
 * @author Eyenseo
 * @version 1
 */
//TODO read from save file
class MapCreator extends StorageReader {
	private StreetCircularList   streetGroupBuffer   = null;
	private StationCircularList  stationGroupBuffer  = null;
	private FacilityCircularList facilityGroupBuffer = null;

	//TODO constructor with parameter with the Community-Queue and the Chance-Queue for the Community and Chance Fields
	MapCreator() {
		super("map.txt");
	}

	/**
	 * @return The return value is the finished map. A Field array with 40 length and different objects of the
	 *         objects.value package.
	 * @throws MapCreationException The Exception holds in its cause attribute the previous Exception and should be
	 *                              read out with getMessageStack.
	 * @see StorageReaderException
	 */
	//TODO Method to create map from save
	Field[] createMap() throws MapCreationException {
		Field[] map = new Field[40];
		int i = 0;
		try {
			for(i = 0; i < map.length; i++) {
				map[i] = nextField();
			}
			return map;
		} catch(StorageReaderException e) {
			throw new MapCreationException(i, e);
		}
	}

	/**
	 * @return The return value is the next Field of the map.
	 * @throws StorageReaderException The Exception holds in its cause attribute the previous Exception and should be
	 *                                read out with getMessageStack.
	 */
	private Field nextField() throws StorageReaderException {
		String line;
		while((line = nextString()) != null) {
			if(isControlWord(line)) {
				if(line.equals("#!")) {
					continue;
				}
				if(line.equals("#Street")) {
					return createStreet();
				}
				if(line.equals("#Street")) {
					return createStreet();
				}
				if(line.equals("#Station")) {
					return createStation();
				}
				if(line.equals("#Facility")) {
					return createFacility();
				}
				if(line.equals("#Tax")) {
					return createTax();
				}
				if(line.equals("#Chance")) {
					return createChance();
				}
				if(line.equals("#Community")) {
					return createCommunity();
				}
				if(line.equals("#Go")) {
					return createGo();
				}
				if(line.equals("#Jail")) {
					return createJail();
				}
				if(line.equals("#Parking")) {
					return createParking();
				}
				if(line.equals("#GoToJail")) {
					return createGoToJail();
				}
			}
		}
		throw new FieldCreationException();
	}

	/**
	 * This method connects the streets of the same color while creating them.
	 *
	 * @return The return value is a StreetCircularList object based on the data in the storage package.
	 * @throws StreetCreationException The Exception holds in its cause attribute the previous Exception and should be
	 *                                 read out with getMessageStack.
	 * @see StorageReaderException
	 */
	private StreetCircularList createStreet() throws StreetCreationException {
		String name = null;
		try {
			name = nextString();
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
			StreetCircularList street = new StreetCircularList(name, price, income, mortgage, 0, null, upgrade, color);
			connectStreet(street);
			return street;
		} catch(Exception e) {
			throw new StreetCreationException(name, e);
		}
	}

	/**
	 * This method checks the last StreetCircularList object was already of it's color, if it is it connects the them,
	 * if not the StreetCircularList object in the parameter is the 'start' of a new circular list.
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
	 * @throws StationCreationException The Exception holds in its cause attribute the previous Exception and should be
	 *                                  read out with getMessageStack.
	 * @see StorageReaderException
	 */
	private StationCircularList createStation() throws StationCreationException {
		String name = null;
		try {
			name = nextString();
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
		} catch(Exception e) {
			throw new StationCreationException(name, e);
		}
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
	 * @throws FacilityCreationException The Exception holds in its cause attribute the previous Exception and should
	 *                                   be read out with getMessageStack.
	 * @see StorageReaderException
	 */
	private FacilityCircularList createFacility() throws FacilityCreationException {
		String name = null;
		try {
			name = nextString();
			int price = nextInt();
			int[] income = {nextInt(), nextInt()};
			int mortgage = nextInt();
			FacilityCircularList facility = new FacilityCircularList(name, price, income, mortgage, 0, null);
			connectFacility(facility);
			return facility;
		} catch(Exception e) {
			throw new FacilityCreationException(name, e);
		}
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
	 * @return The return value is a Tax object based on the data in the storage package.
	 * @throws TaxCreationException The Exception hold in its cause attribute the previous Exception and should be
	 *                              read out with getMessageStack.
	 * @see StorageReaderException
	 */
	private Tax createTax() throws TaxCreationException {
		String name = null;
		try {
			name = nextString();
			int bill = nextInt();
			return new Tax(name, bill);
		} catch(Exception e) {
			throw new TaxCreationException(name, e);
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
