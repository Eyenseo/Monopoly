package objects.value.map;

/**
 * The CommunityData is a class for the network communication that holds all needed information
 */
public class CommunityData extends NotPurchasableData {
	/**
	 * @param fieldNumber the value determines the id
	 * @param name        the value determines the name
	 */
	public CommunityData(int fieldNumber, String name) {
		super(fieldNumber, name);
	}
}
