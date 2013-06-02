package objects.value.connection.action;

//JAVADOC
public class TurnData extends ActionData {
	private final boolean turnAction;

	//JAVADOC
	public TurnData(int playerId, boolean turnAction) {
		super(playerId);
		this.turnAction = turnAction;
	}

	//JAVADOC
	public boolean isTurnAction() {
		return turnAction;
	}
}
