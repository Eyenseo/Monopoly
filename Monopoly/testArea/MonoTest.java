import core.Connector;
import core.data.MapArrayCreator;
import objects.exceptions.core.NoInstanceException;
import objects.exceptions.data.StorageReaderException;
import objects.map.Field;

public class MonoTest {
	public static void main(String[] args) {
		Field f = null;
		try {
			f = new Connector().connect(new MapArrayCreator().createMapArray());
		} catch(StorageReaderException e) {
			System.err.print(e.getMessageStack());
		} catch(NoInstanceException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		System.out.print("Set stop - check the map in debug.");
	}
}
