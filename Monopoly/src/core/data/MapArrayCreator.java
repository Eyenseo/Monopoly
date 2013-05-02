package core.data;

import objects.exceptions.data.EndOfBlockException;
import objects.exceptions.data.StorageReaderException;
import objects.exceptions.data.map.*;
import objects.map.FieldCircularList;
import objects.map.notPurchasable.*;
import objects.map.purchasable.Facility;
import objects.map.purchasable.Station;
import objects.map.purchasable.Street;

import java.util.Vector;

//JAVADOC
public class MapArrayCreator extends StorageReader {
	private FieldCircularList[] map;
	private FieldCircularList   jail;
	private FieldCircularList   go;

	//TODO constructor with parameter with the Community-Queue and the Chance-Queue for the Community and Chance Fields
	public MapArrayCreator() throws StorageReaderException {
		super("map.txt");
		map = createMapArray();
	}

	//JAVADOC
	public FieldCircularList[] getMap() {
		return map;
	}

	//JAVADOC
	public FieldCircularList getJail() {
		return jail;
	}

	//JAVADOC
	public FieldCircularList getGo() {
		return go;
	}

	/**
	 * @return The return value is the finished map. A FieldCircularList array with 40 length and different objects of the
	 *         objects.value package.
	 *
	 * @throws objects.exceptions.data.map.MapArrayCreationException
	 *          The Exception holds in its cause attribute the previous Exception and should be
	 *          read out with getMessageStack.
	 * @see StorageReaderException
	 */
	//JAVADOC
	private FieldCircularList[] createMapArray() throws StorageReaderException {
		Vector<FieldCircularList> fieldVector = new Vector<FieldCircularList>();
		FieldCircularList[] fieldArray;
		FieldCircularList temp;
		while((temp = nextField()) != null) {
			fieldVector.addElement(temp);
		}
		fieldArray = new FieldCircularList[fieldVector.size()];
		fieldVector.toArray(fieldArray);
		return fieldArray;
	}

	/**
	 * @return The return value is the next FieldCircularList of the map.
	 *
	 * @throws StorageReaderException The Exception holds in its cause attribute the previous Exception and should be
	 *                                read out with getMessageStack.
	 */
	//JAVADOC
	private FieldCircularList nextField() throws StorageReaderException {
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
				return go = createGo();
			}
			if(line.equals("#Jail")) {
				return jail = createJail();
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
	 * @return The return value is a Street object based on the data in the storage package.
	 *
	 * @throws StreetCreationException The Exception holds in its cause attribute the previous Exception and should be
	 *                                 read out with getMessageStack.
	 * @see StorageReaderException
	 */
	//JAVADOC
	private Street createStreet() throws StorageReaderException {
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
			return new Street(name, price, income, mortgage, upgrade, color);
		} catch(Exception e) {
			throw new StreetCreationException(name, e);
		}
	}

	/**
	 * This method connects the new station with the other if other exist.
	 *
	 * @return The return value is a Station object based on the data in the storage package.
	 *
	 * @throws StationCreationException The Exception holds in its cause attribute the previous Exception and should be
	 *                                  read out with getMessageStack.
	 * @see StorageReaderException
	 */
	//JAVADOC
	private Station createStation() throws StorageReaderException {
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
			return new Station(name, price, income, mortgage);
		} catch(Exception e) {
			throw new StationCreationException(name, e);
		}
	}

	/**
	 * This method connects the new facility with the other if other exist.
	 *
	 * @return The return value is a Facility object based on the data in the storage package.
	 *
	 * @throws objects.exceptions.data.map.FacilityCreationException
	 *          The Exception holds in its cause attribute the previous Exception and should
	 *          be read out with getMessageStack.
	 * @see StorageReaderException
	 */
	//JAVADOC
	private Facility createFacility() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			int price = nextInt();
			int[] income = {nextInt(), nextInt()};
			int mortgage = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Facility(name, price, income, mortgage);
		} catch(Exception e) {
			throw new FacilityCreationException(name, e);
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
	//JAVADOC
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
	//JAVADOC
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
	//JAVADOC
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
	//JAVADOC
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
	//JAVADOC
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
	//JAVADOC
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