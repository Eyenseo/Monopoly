package objects.value.action;

/**
 * The PlayerStatusData is an object with all information of the status of a Player. The data is send from the client if
 * the player has resolved a financial problem or has decided to give up.
 */
public class PlayerStatusData extends ActionData {
	private PlayerStatus status;

	/**
	 * @param userId The value determines the player the PlayerStatusData is from
	 * @param status The value determines the status of the Player <ul> <li>GIVEUP: if the player has decided to give
	 *               up</li> <li>FINANCIAL: if the player has resolved a financial problem</li> </ul>
	 */
	public PlayerStatusData(int userId, PlayerStatus status) {
		super(userId);
		this.status = status;
	}

	/**
	 * The enum is used to define the meaning of PlayerStatusData
	 */
	public enum PlayerStatus {
		GIVEUP, FINANCIAL
	}

	/**
	 * @return The return value is the Status of the Player
	 */
	public PlayerStatus getStatus() {
		return status;
	}
}
