package core.data;

import objects.exceptions.EndOfBlockException;
import objects.exceptions.StorageReaderException;
import objects.exceptions.map.*;
import objects.map.*;

import java.util.Vector;

/**
 * The MapCreator class provides the method to create the map out of the data in the storage package.
 *
 * @author Eyenseo
 * @version 1
 */
public class MapCreator extends StorageReader {
	private StreetCircularList   streetGroupBuffer   = null;
	private StationCircularList  stationGroupBuffer  = null;
	private FacilityCircularList facilityGroupBuffer = null;

	//TODO constructor with parameter with the Community-Queue and the Chance-Queue for the Community and Chance Fields
	public MapCreator() {
		super("map.txt");
	}

	/**
	 * @return The return value is the finished map. A Field array with 40 length and different objects of the
	 *         objects.value package.
	 *
	 * @throws MapCreationException The Exception holds in its cause attribute the previous Exception and should be
	 *                              read out with getMessageStack.
	 * @see StorageReaderException
	 */
	//JAVADOC
	public Field[] createMap() throws StorageReaderException {
		Vector<Field> fieldVector = new Vector<Field>();
		Field[] fieldArray;
		Field temp;
		while((temp = nextField()) != null) {
			fieldVector.addElement(temp);
		}
		fieldArray = new Field[fieldVector.size()];
		fieldVector.toArray(fieldArray);
		return fieldArray;
	}

	/**
	 * @return The return value is the next Field of the map.
	 *
	 * @throws StorageReaderException The Exception holds in its cause attribute the previous Exception and should be
	 *                                read out with getMessageStack.
	 */
	private Field nextField() throws StorageReaderException {
		String line = nextControllWord();
		if(line != null) {
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
			throw new FieldCreationException(line);
		}
		return null;
	}

	/**
	 * This method connects the streets of the same color while creating them.
	 *
	 * @return The return value is a StreetCircularList object based on the data in the storage package.
	 *
	 * @throws StreetCreationException The Exception holds in its cause attribute the previous Exception and should be
	 *                                 read out with getMessageStack.
	 * @see StorageReaderException
	 */
	private StreetCircularList createStreet() throws StorageReaderException {
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
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			StreetCircularList street = new StreetCircularList(name, price, income, mortgage, upgrade, color);
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
	private void connectStreet(StreetCircularList street) {
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
	 * @throws StationCreationException The Exception holds in its cause attribute the previous Exception and should be
	 *                                  read out with getMessageStack.
	 * @see StorageReaderException
	 */
	private StationCircularList createStation() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			int price = nextInt();
			int[] income = new int[4];
			for(int i = 0; i < income.length; i++) {
				income[i] = nextInt();
			}
			int mortgage = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			//TODO get owner and stage from a save file
			StationCircularList station = new StationCircularList(name, price, income, mortgage);
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
	 *
	 * @throws FacilityCreationException The Exception holds in its cause attribute the previous Exception and should
	 *                                   be read out with getMessageStack.
	 * @see StorageReaderException
	 */
	private FacilityCircularList createFacility() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			int price = nextInt();
			int[] income = {nextInt(), nextInt()};
			int mortgage = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			FacilityCircularList facility = new FacilityCircularList(name, price, income, mortgage);
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
	 *
	 * @throws TaxCreationException The Exception hold in its cause attribute the previous Exception and should be
	 *                              read out with getMessageStack.
	 * @see StorageReaderException
	 */
	private Tax createTax() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			int bill = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Tax(name, bill);
		} catch(Exception e) {
			throw new TaxCreationException(name, e);
		}
	}

	/**
	 * @return The return value is a Chance object based on the data in the storage package.
	 */
	private Chance createChance() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Chance(name);
		} catch(Exception e) {
			throw new ChanceCreationException(name, e);
		}
	}

	/**
	 * @return The return value is a Community object based on the data in the storage package.
	 */
	private Community createCommunity() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Community(name);
		} catch(Exception e) {
			throw new CommunityCreationException(name, e);
		}
	}

	/**
	 * @return The return value is a Go object based on the data in the storage package.
	 */
	private Go createGo() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			int turnmoney = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Go(name, turnmoney);
		} catch(Exception e) {
			throw new GoCreationException(name, e);
		}
	}

	/**
	 * @return The return value is a Jail object based on the data in the storage package.
	 */
	private Jail createJail() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Jail(name);
		} catch(Exception e) {
			throw new JailCreationException(name, e);
		}
	}

	/**
	 * @return The return value is a Parking object based on the data in the storage package.
	 */
	private Parking createParking() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Parking(name);
		} catch(Exception e) {
			throw new ParkingCreationException(name, e);
		}
	}

	/**
	 * @return The return value is a GoToJail object based on the data in the storage package.
	 */
	private GoToJail createGoToJail() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new GoToJail(name);
		} catch(Exception e) {
			throw new GoToJailCreationException(name, e);
		}
	}
}