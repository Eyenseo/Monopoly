package core.data;

import objects.card.*;
import objects.exceptions.EndOfBlockException;
import objects.exceptions.StorageReaderException;
import objects.exceptions.card.*;

import java.util.List;
import java.util.Vector;

public class CardCreator extends StorageReader {
	public CardCreator(String file) {
		super(file);
	}

	public Card[] cardArray() throws CardArrayException {
		List<Card> cards = new Vector<Card>();
		while(true) { //TODO Placeholder :)
			cards.add(nextCard());
		}
		return (Card[]) cards.toArray();
	}

	private Card nextCard() throws StorageReaderException {
		String line = nextString();
		if(isControlWord(line)) {
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
		}
	}

	private GoBack createGoBack() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			int fields = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new GoBack(name, text, fields);
		} catch(Exception e) {
			throw new GoBackCreationException(name, e);
		}
	}

	private GoTo createGoTo() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new GoTo(name, text);
		} catch(Exception e) {
			throw new GoToCreationException(name, e);
		}
	}

	private GoToStation createGoToStation() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			int fields = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new GoToStation(name, text);
		} catch(Exception e) {
			throw new GoToStationCreationException(name, e);
		}
	}

	private Jail createJail() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			int fields = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Jail(name, text);
		} catch(Exception e) {
			throw new JailCreationException(name, e);
		}
	}

	private Jailbait createJailbait() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			int fields = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Jailbait(name, text);
		} catch(Exception e) {
			throw new JailbaitCreationException(name, e);
		}
	}

	private PayFineTakeCard createPayFineTakeCard() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			int fields = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new PayFineTakeCard(name, text);
		} catch(Exception e) {
			throw new PayFineTakeCardCreationException(name, e);
		}
	}

	private Payment createPayment() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			int dm = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new Payment(name, text, dm);
		} catch(Exception e) {
			throw new PaymentCreationException(name, e);
		}
	}

	private SpecialPayment createSpecialPayment() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			int dm = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new SpecialPayment(name, text, dm);
		} catch(Exception e) {
			throw new SpecialPaymentCreationException(name, e);
		}
	}

	private StreetWork createStreetWork() throws StorageReaderException {
		String name = null;
		try {
			name = nextString();
			String text = nextString();
			int dmHouse = nextInt();
			int dmHotel = nextInt();
			if(!isEndOfBlock()) {
				throw new EndOfBlockException(path);
			}
			return new StreetWork(name, text, dmHouse, dmHotel);
		} catch(Exception e) {
			throw new StreetWorkCreationException(name, e);
		}
	}
}


