package core.data;

import objects.exceptions.data.ControlWordNotFoundException;
import objects.exceptions.data.EndOfBlockException;
import objects.exceptions.data.StorageReaderException;
import objects.exceptions.data.map.*;
import objects.map.FieldCircularList;
import objects.map.notPurchasable.*;
import objects.map.purchasable.Facility;
import objects.map.purchasable.Station;
import objects.map.purchasable.Street;

import java.util.Vector;

/**
 * The MapArrayCreator creates FieldCircularList objects based on the data in the map.txt file located in the storage package.
 *
 * @author Eyenseo
 * @version 1
 * @see FieldCircularList
 */
public class MapArrayCreator extends StorageReader {
	private FieldCircularList[] map;
	private FieldCircularList   jail;
	private FieldCircularList   go;

	/**
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	public MapArrayCreator() throws StorageReaderException {
		super("map.txt");
		map = createMapArray();
	}

	/**
	 * @return The return value is an Array containing FieldCircularList objects.
	 */
	public FieldCircularList[] getMap() {
		return map;
	}

	/**
	 * @return The return value is a Jail object.
	 */
	public FieldCircularList getJail() {
		return jail;
	}

	/**
	 * @return The return value is a Go object.
	 */
	public FieldCircularList getGo() {
		return go;
	}

	/**
	 * @return The return value is an Array containing FieldCircularList objects.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
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
	 * @return The return value is the next FieldCircularList, if the end of the file is reached it returns null.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private FieldCircularList nextField() throws StorageReaderException {
		String line = nextControlWord();
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
			throw new ControlWordNotFoundException(line);
		}
		return null;
	}

	/**
	 * @return the return value is a Street Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
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
	 * @return the return value is a Station Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
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
			return new Station(name, price, income, mortgage);
		} catch(Exception e) {
			throw new StationCreationException(name, e);
		}
	}

	/**
	 * @return the return value is a Facility Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
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
	 * @return the return value is a Tax Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
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
	 * @return the return value is a Chance Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
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
	 * @return the return value is a Community Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
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
	 * @return the return value is a Go Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private Go createGo() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			int turnMoney = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Go(name, turnMoney);
		} catch(Exception e) {
			throw new GoCreationException(name, e);
		}
	}

	/**
	 * @return the return value is a Jail Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
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
	 * @return the return value is a Parking Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
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
	 * @return the return value is a GoToJail Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
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