package objects.value.action;

/**
 * The GiveUpData is an object with all information of a forfeiting player
 */
public class GiveUpData extends ActionData {
	private final boolean giveUp;

	/**
	 * @param userId the value determines the player Id the data original from
	 * @param giveUp the value determines if the player gives up or not
	 */
	public GiveUpData(int userId, boolean giveUp) {
		super(userId);
		this.giveUp = giveUp;
	}

	/**
	 * @return the return value is true if the player gives up
	 */
	public boolean isGiveUp() {
		return giveUp;
	}
}
