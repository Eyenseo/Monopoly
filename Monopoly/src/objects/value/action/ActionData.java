package objects.value.action;

/**
 * ActionData holds the player id of the player the data comes from
 */
public abstract class ActionData {
	private final int userId;

	/**
	 * @param userId the value determines the player Id the data original from
	 */
	ActionData(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the return value is the player Id the data original from
	 */
	public int getUserId() {
		return userId;
	}
}
