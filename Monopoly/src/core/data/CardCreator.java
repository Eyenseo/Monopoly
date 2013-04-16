package core.data;

import objects.card.Card;
import objects.card.GoBack;
import objects.card.GoTo;
import objects.exceptions.EndOfBlockException;
import objects.exceptions.StorageReaderException;
import objects.exceptions.card.CardCreationException;

public class CardCreator extends StorageReader {
    public CardCreator(String file) {
        super(file);
    }

    public CreateCardArray[]

    {

    }

    private Card nextCard() throws CardCreationException {
        String line = nextString();
        if (isControlWord(line)) {
            if (line.equals("#back")) {
                return createGoBack();
            }
            if (line.equals("#goTo")) {
                return createGoTo();
            }
            if (line.equals("#gotostation")) {
                return createGoToStation();
            }
            if (line.equals("#jail")) {
                return createJail();
            }
            if (line.equals("#jailbait")) {
                return createJailbait();
            }
            if (line.equals("#payfinetakecard")) {
                return createPayFineTakeCard();
            }
            if (line.equals("#$")) {
                return createPayment();
            }
            if (line.equals("#special$")) {
                return createSpecialPayment();
            }
            if (line.equals("#streetWork")) {
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
            if (!isEndOfBlock()) {
                throw new EndOfBlockException(path);
            }
            return new GoBack(name, text, fields);
        } catch (Exception e) {
            throw new GoBackCreationException(name, e);
        }
    }

    private GoTo createGoTo() throws StorageReaderException {
        String name = null;
        try {
            name = nextString();
            String text = nextString();
            if (!isEndOfBlock()) {
                throw new EndOfBlockException(path);
            }
            return new GoTo(name, text);
        } catch (Exception e) {
            throw new GoToCreationException(name, e);
        }
    }

    private GoBack createGoToStation() throws StorageReaderException {
        String name = null;

        try {
            name = nextString();
            String text = nextString();
            int fields = nextInt();
            if (!isEndOfBlock()) {
                throw new EndOfBlockException(path);
            }
            return new GoBack(name, text, fields);
        } catch (Exception e) {
            throw new GoBackCreationException(name, e);
        }
    }


}


