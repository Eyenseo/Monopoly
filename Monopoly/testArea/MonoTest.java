import core.Connector;
import core.data.MapArrayCreator;
import objects.Player;
import objects.card.CardStack;
import objects.exceptions.core.CardConnectionException;
import objects.exceptions.core.NoInstanceException;
import objects.exceptions.data.StorageReaderException;
import objects.map.Field;
import objects.map.purchasable.PurchasableCircularList;
import ui.cui.ConsoleMenu;

public class MonoTest {
	public static void main(String[] args) {
		Field go = null;
		ConsoleMenu menu = new ConsoleMenu();
		try {
			MapArrayCreator mac = new MapArrayCreator();
			Field[] mapArray = mac.getMap();
			go = mac.getGo();
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
		Player p = new Player("Julia", 100000000);
		p.buy((PurchasableCircularList) go.getNext());
		p.buy((PurchasableCircularList) go.getNext().getNext().getNext());
		menu.playerPropertiesDetails(p);
		menu.groupDetails((PurchasableCircularList) go.getNext().getNext().getNext().getNext().getNext());
		menu.groupDetails((PurchasableCircularList) go.getNext().getNext().getNext().getNext().getNext().getNext());
		System.out.print("Set stop - check the map in debug.");
	}
}
