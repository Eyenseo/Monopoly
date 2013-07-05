package objects.value.action;

/**
 * The PlayerStatusData is an object with all information of a forfeiting player
 */
public class PlayerStatusData extends ActionData {
	private PlayerStatus status;

	//JAVADOC
	public PlayerStatusData(int userId, PlayerStatus status) {
		super(userId);
		this.status = status;
	}

	public enum PlayerStatus {
		GIVEUP, FINANCIAL
	}

	public PlayerStatus getStatus() {
		return status;
	}
}
