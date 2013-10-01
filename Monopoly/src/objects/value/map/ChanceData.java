package objects.value.map;

/**
 * The ChanceData is a class for the network communication that holds all needed information
 */
public class ChanceData extends NotPurchasableData {
	/**
	 * @param fieldNumber the value determines the id
	 * @param name        the value determines the name
	 */
	public ChanceData(int fieldNumber, String name) {
		super(fieldNumber, name);
	}
}
