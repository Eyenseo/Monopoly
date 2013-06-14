package objects.value.action;

/**
 * The TurnData is an object with all information regarding the turn state of the player
 */
public class TurnData extends ActionData {
	private final boolean turnAction;

	/**
	 * @param playerId   the value determines the player Id the data original from
	 * @param turnAction the value determines if the player wants to move (true) or to end the turn
	 */
	public TurnData(int playerId, boolean turnAction) {
		super(playerId);
		this.turnAction = turnAction;
	}

	/**
	 * @return the return value is true if the player wants to move
	 */
	public boolean isTurnAction() {
		return turnAction;
	}
}
