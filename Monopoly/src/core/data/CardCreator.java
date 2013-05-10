package core.data;

import objects.card.*;
import objects.exceptions.data.ControlWordNotFoundException;
import objects.exceptions.data.EndOfBlockException;
import objects.exceptions.data.StorageReaderException;
import objects.exceptions.data.card.*;

import java.util.ArrayList;

/**
 * The CardCreator creates Card objects based on the data in a file located in the storage package.
 *
 * @author Eyenseo
 * @version 1
 * @see Card
 */
public class CardCreator extends StorageReader {
	private final String NAME;

	/**
	 * @param file The value determines which file will be loaded.
	 * @param name The value determines the name of the Cards
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	public CardCreator(String file, String name) throws StorageReaderException {
		super(file);
		NAME = name;
	}

	/**
	 * @return The return value is a Vector with Card objects fom the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	public ArrayList<Card> cardArray() throws StorageReaderException {
		ArrayList<Card> cardVector = new ArrayList<Card>();
		Card temp;
		while((temp = nextCard()) != null) {
			cardVector.add(temp);
		}
		return cardVector;
	}

	/**
	 * @return The return value is the next Card, if the end of the file is reached it returns null.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private Card nextCard() throws StorageReaderException {
		String line = nextControlWord();
		if(line != null) {
			if(line.equals("#back")) {
				return createGoBack();
			}
			if(line.equals("#goTo")) {
				return createGoTo();
			}
			if(line.equals("#gotostation")) {
				return createGoToStation();
			}
			if(line.equals("#jail")) {
				return createArrest();
			}
			if(line.equals("#jailbait")) {
				return createJailbait();
			}
			if(line.equals("#payfinetakecard")) {
				return createPayFineTakeCard();
			}
			if(line.equals("#$")) {
				return createPayment();
			}
			if(line.equals("#special$")) {
				return createSpecialPayment();
			}
			if(line.equals("#streetWork")) {
				return createStreetWork();
			}
			throw new ControlWordNotFoundException(line);
		}
		return null;
	}

	/**
	 * @return the return value is a GoBack Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private GoBack createGoBack() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int fields = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new GoBack(NAME, text, fields);
		} catch(Exception e) {
			throw new GoBackCreationException(NAME + ": " + text, e);
		}
	}

	/**
	 * @return the return value is a GoTo Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private GoTo createGoTo() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			String field = nextString();
			boolean overGo = nextString().equals("t");
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new GoTo(NAME, text, field, overGo);
		} catch(Exception e) {
			throw new GoToCreationException(NAME + ": " + text, e);
		}
	}

	/**
	 * @return the return value is a GoToStation Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private GoToStation createGoToStation() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new GoToStation(NAME, text);
		} catch(Exception e) {
			throw new GoToStationCreationException(NAME + ": " + text, e);
		}
	}

	/**
	 * @return the return value is a Arrest Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private Arrest createArrest() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new Arrest(NAME, text);
		} catch(Exception e) {
			throw new JailCreationException(NAME + ": " + text, e);
		}
	}

	/**
	 * @return the return value is a Jailbait Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private Jailbait createJailbait() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new Jailbait(NAME, text);
		} catch(Exception e) {
			throw new JailbaitCreationException(NAME + ": " + text, e);
		}
	}

	/**
	 * @return the return value is a PayFineTakeCard Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private PayFineTakeCard createPayFineTakeCard() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int dm = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new PayFineTakeCard(NAME, text, dm);
		} catch(Exception e) {
			throw new PayFineTakeCardCreationException(NAME + ": " + text, e);
		}
	}

	/**
	 * @return the return value is a Payment Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private Payment createPayment() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int dm = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new Payment(NAME, text, dm);
		} catch(Exception e) {
			throw new PaymentCreationException(NAME + ": " + text, e);
		}
	}

	/**
	 * @return the return value is a SpecialPayment Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private SpecialPayment createSpecialPayment() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int dm = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new SpecialPayment(NAME, text, dm);
		} catch(Exception e) {
			throw new SpecialPaymentCreationException(NAME + ": " + text, e);
		}
	}

	/**
	 * @return the return value is a StreetWork Object based on the data in the file.
	 *
	 * @throws StorageReaderException The Exception has a cause attribute that holds the previous Exception. It should be read out with getMessageStack.
	 */
	private StreetWork createStreetWork() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int dmHouse = nextInt();
			int dmHotel = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(fileName);
			}
			return new StreetWork(NAME, text, dmHouse, dmHotel);
		} catch(Exception e) {
			throw new StreetWorkCreationException(NAME + ": " + text, e);
		}
	}
}