package objects.value.action;

import java.util.ArrayList;

/**
 * The HaggleData is an object with all information regarding a trade between two players
 */
public class HaggleData extends ActionData {
	private final int                sellerId;       // id from the Player that something is Requested from
	private final ArrayList<Integer> userFieldIds;   // ids from Purchasables that the player wants
	private final ArrayList<Integer> sellerFieldIds; // ids from Purchasables that the seller wants
	private       HaggleState        haggleState;    // state of the trade
	private       int                userMoney;      // money that the Player (who sends the request) wants
	private       int                sellerMoney;    // money that the seller (who sends th offer) wants

	/**
	 * @param userId   the value determines the player Id the data original from
	 * @param sellerId the value determines the id of the Player that something is Requested from
	 */
	public HaggleData(int userId, int sellerId) {
		super(userId);
		this.sellerId = sellerId;
		userFieldIds = new ArrayList<Integer>();
		sellerFieldIds = new ArrayList<Integer>();
		haggleState = HaggleState.ESTABLISH;
	}

	/**
	 * The method was used for the time there was no network
	 *
	 * @param haggleData the value determines a HaggleData to copy
	 */
	@Deprecated public HaggleData(HaggleData haggleData) {
		super(haggleData.getUserId());
		sellerId = haggleData.getSellerId();
		userFieldIds = new ArrayList<Integer>(haggleData.getUserFieldIds());
		sellerFieldIds = new ArrayList<Integer>(haggleData.getSellerFieldIds());
		haggleState = haggleData.getHaggleState();
		userMoney = haggleData.getUserMoney();
		sellerMoney = haggleData.getSellerMoney();
	}

	/**
	 * @return the return value is the id of the seller (the player that got a request)
	 */
	public int getSellerId() {
		return sellerId;
	}

	/**
	 * @return the return value is the amount of money that the Player (who sends the request) wants
	 */
	public int getUserMoney() {
		return userMoney;
	}

	/**
	 * @return the return value is the amount of money that the seller (who sends th offer) wants
	 */
	public int getSellerMoney() {
		return sellerMoney;
	}

	/**
	 * @return the return value is a ArrayList of ids from Purchasables that the player wants
	 */
	public ArrayList<Integer> getUserFieldIds() {
		return userFieldIds;
	}

	/**
	 * @return the return value is a ArrayList of ids from Purchasables that the seller wants
	 */
	public ArrayList<Integer> getSellerFieldIds() {
		return sellerFieldIds;
	}

	/**
	 * @return the return value is the state of the trade
	 */
	public HaggleState getHaggleState() {
		return haggleState;
	}

	/**
	 * @param haggleState the value determine the new state of the trade
	 */
	public void setHaggleState(HaggleState haggleState) {
		this.haggleState = haggleState;
	}

	/**
	 * @param userMoney the value determines the amount of money that the Player (who sends the request) wants
	 */
	public void setUserMoney(int userMoney) {
		this.userMoney = userMoney;
	}

	/**
	 * @param sellerMoney the value determines the amount of money that the seller (who sends th offer) wants
	 */
	public void setSellerMoney(int sellerMoney) {
		this.sellerMoney = sellerMoney;
	}

	/**
	 * <ul>
	 * <li>ESTABLISH: request for the server to allow the trade</li>
	 * <li>ESTABLISHED: answer of the server to a request (ESTABLISH) if successful</li>
	 * <li>REQUEST: request to the seller </li>
	 * <li>OFFER: offer from the seller to the player </li>
	 * <li>ACCEPT: request for the server to execute the trade</li>
	 * <li>DECLINE: request to the server to end the trade without doing anything</li>
	 * </ul>
	 */
	public enum HaggleState {
		ESTABLISH, ESTABLISHED, REQUEST, OFFER, ACCEPT, DECLINE
	}
}
