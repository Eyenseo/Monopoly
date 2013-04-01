package objects.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Eyenseo
 * Date: 31.03.13
 * Time: 22:48
 * To change this template use File | Settings | File Templates.
 */
public class TaxCreationException extends StorageReaderException {
	public TaxCreationException(String name, Throwable cause) {
		super((name != null) ? "Something was missing while reading the data of Tax: " + name + "\""
				      : "There should have been data for a Tax but there wasn't!", cause);
	}
}
