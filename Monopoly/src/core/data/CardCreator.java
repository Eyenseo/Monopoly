package core.data;

import objects.card.*;
import objects.exceptions.EndOfBlockException;
import objects.exceptions.StorageReaderException;
import objects.exceptions.card.*;

import java.util.Vector;

//JAVADOC
public class CardCreator extends StorageReader {
	private final String NAME;

	//JAVADOC
	public CardCreator(String file, String name) {
		super(file);
		NAME = name;
	}

	//JAVADOC
	public Card[] cardArray() throws StorageReaderException {
		Vector<Card> cardVector = new Vector<Card>();
		Card[] cardArray;
		Card temp;
		while((temp = nextCard()) != null) {
			cardVector.addElement(temp);
		}
		cardArray = new Card[cardVector.size()];
		cardVector.toArray(cardArray);
		return cardArray;
	}

	//JAVADOC
	private Card nextCard() throws StorageReaderException {
		String line = nextControllWord();
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
				return createJail();
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
			throw new CardCreationException(line);
		}
		return null;
	}

	//JAVADOC
	private GoBack createGoBack() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int fields = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new GoBack(NAME, text, fields);
		} catch(Exception e) {
			throw new GoBackCreationException(NAME + ": " + text, e);
		}
	}

	//JAVADOC
	private GoTo createGoTo() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			String field = nextString();
			boolean overGo = nextString().equals("t");
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new GoTo(NAME, text, field, overGo);
		} catch(Exception e) {
			throw new GoToCreationException(NAME + ": " + text, e);
		}
	}

	//JAVADOC
	private GoToStation createGoToStation() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new GoToStation(NAME, text);
		} catch(Exception e) {
			throw new GoToStationCreationException(NAME + ": " + text, e);
		}
	}

	//JAVADOC
	private Jail createJail() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Jail(NAME, text);
		} catch(Exception e) {
			throw new JailCreationException(NAME + ": " + text, e);
		}
	}

	//JAVADOC
	private Jailbait createJailbait() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Jailbait(NAME, text);
		} catch(Exception e) {
			throw new JailbaitCreationException(NAME + ": " + text, e);
		}
	}

	//JAVADOC
	private PayFineTakeCard createPayFineTakeCard() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new PayFineTakeCard(NAME, text);
		} catch(Exception e) {
			throw new PayFineTakeCardCreationException(NAME + ": " + text, e);
		}
	}

	//JAVADOC
	private Payment createPayment() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int dm = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Payment(NAME, text, dm);
		} catch(Exception e) {
			throw new PaymentCreationException(NAME + ": " + text, e);
		}
	}

	//JAVADOC
	private SpecialPayment createSpecialPayment() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int dm = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new SpecialPayment(NAME, text, dm);
		} catch(Exception e) {
			throw new SpecialPaymentCreationException(NAME + ": " + text, e);
		}
	}

	//JAVADOC
	private StreetWork createStreetWork() throws StorageReaderException {
		String text = null;
		try {
			text = nextString();
			int dmHouse = nextInt();
			int dmHotel = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new StreetWork(NAME, text, dmHouse, dmHotel);
		} catch(Exception e) {
			throw new StreetWorkCreationException(NAME + ": " + text, e);
		}
	}
}