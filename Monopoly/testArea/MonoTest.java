import core.data.MapArrayCreator;
import objects.exceptions.StorageReaderException;
import objects.map.Field;
import objects.map.MapConnector;

public class MonoTest {
	public static void main(String[] args) {
		Field f = null;
		try {
			f = new MapConnector().make(new MapArrayCreator().createMapArray());
		} catch(StorageReaderException e) {
			System.err.print(e.getMessageStack());
		}
		System.out.print("Set stop - check the map in debug.");
	}
}
