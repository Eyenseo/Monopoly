package objects.value.action;

/**
 * ActionData holds the player id of the player the data comes from
 */
public abstract class ActionData {
	private final int playerId;

	/**
	 * @param playerId the value determines the player Id the data original from
	 */
	ActionData(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the return value is the player Id the data original from
	 */
	public int getPlayerId() {
		return playerId;
	}
}
