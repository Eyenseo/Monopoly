package objects.exceptions.map;

/**
 * Created with IntelliJ IDEA.
 * User: eyenseo
 * Date: 4/26/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapInitialisationException extends Exception {
	public MapInitialisationException(String object) {
		super("There was no object of:\n\t" + object);
	}
}
