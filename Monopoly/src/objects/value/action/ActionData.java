package objects.value.action;

/**
 * ActionData holds the player id of the player the data comes from
 */
public abstract class ActionData {
	private static int id = 0;
	private final int userId;

	/**
	 * @param userId the value determines the player Id the data original from
	 */
	ActionData(int userId) {
		this.userId = userId;
		id++;
	}

	/**
	 * @return the return value is the player Id the data original from
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 *
	 * @return The return value is the id of the ActionData + the ClassName + the user id
	 */
	public String getId() {
		String name = getClass().getName();
		return "No: " + id + " " + name.substring(name.lastIndexOf(".") + 1, name.length()) + " from user: " + userId;
	}
}
