import core.Connector;
import core.data.MapArrayCreator;
import objects.card.CardStack;
import objects.exceptions.core.CardConnectionException;
import objects.exceptions.core.NoInstanceException;
import objects.exceptions.data.StorageReaderException;
import objects.map.Field;

public class MonoTest {
	public static void main(String[] args) {
		Field f = null;
		try {
			MapArrayCreator mac = new MapArrayCreator();
			Field[] mapArray = mac.getMap();
			f = mac.getGo();
			CardStack event = new CardStack("events.txt", "Event Karte");
			CardStack community = new CardStack("community.txt", "Gemeinschafts Karte");
			new Connector().connect(mapArray, event, community);
		} catch(StorageReaderException e) {
			System.err.print(e.getMessageStack());
		} catch(NoInstanceException e) {
			System.err.print(e.getMessage());
		} catch(CardConnectionException e) {
			System.err.print(e.getMessage());
		}
		System.out.print("Set stop - check the map in debug.");
	}
}
