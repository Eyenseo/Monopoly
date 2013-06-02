package objects.value.connection.action;

//JAVADOC
public class HaggleData extends ActionData {
	private final boolean deal;
	private final int     sellerId;
	private final int[]   fieldIdArray;
	private final int     playerMoney;
	private final int     sellerMoney;

	//JAVADOC
	public HaggleData(int playerId, int sellerId, int[] fieldIdArray, int playerMoney, int sellerMoney, boolean deal) {
		super(playerId);
		this.sellerId = sellerId;
		this.fieldIdArray = fieldIdArray;
		this.playerMoney = playerMoney;
		this.sellerMoney = sellerMoney;
		this.deal = deal;
	}

	//JAVADOC
	public int getSellerId() {
		return sellerId;
	}

	//JAVADOC
	public int[] getFieldIdArray() {
		return fieldIdArray;
	}

	//JAVADOC
	public int getPlayerMoney() {
		return playerMoney;
	}

	//JAVADOC
	public int getSellerMoney() {
		return sellerMoney;
	}

	//JAVADOC
	public boolean isDeal() {
		return deal;
	}
}
