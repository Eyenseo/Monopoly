package objects.value.connection.action;

//JAVADOC
public abstract class ActionData {
	private final int playerId;

	//JAVADOC
	ActionData(int playerId) {
		this.playerId = playerId;
	}

	//JAVADOC
	public int getPlayerId() {
		return playerId;
	}
}
