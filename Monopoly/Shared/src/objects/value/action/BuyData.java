package objects.value.action;

/**
 * The BuyData is an object with all information regarding purchase of a purchasable field or a house/hotel
 */
public class BuyData extends ActionData {
	private final boolean upgrade;

	/**
	 * @param userId  the value determines the player Id the data original from
	 * @param upgrade the value determines if a house/hotel will be bought or the field
	 */
	public BuyData(int userId, boolean upgrade) {
		super(userId);
		this.upgrade = upgrade;
	}

	/**
	 * @return the return value is true if a house/hotel shall be bought
	 */
	public boolean isUpgrade() {
		return upgrade;
	}
}
