package objects.value.map;

/**
 * The GoData is a class for the network communication that holds all needed information
 */
public class GoData extends NotPurchasableData {
	/**
	 * @param fieldNumber the value determines the id
	 * @param name        the value determines the name
	 */
	public GoData(int fieldNumber, String name) {
		super(fieldNumber, name);
	}
}
