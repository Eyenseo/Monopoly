package objects.value.connection.action;

//JAVADOC
public class GiveUpData extends ActionData {
	private final boolean giveUp;

	//JAVADOC
	public GiveUpData(int playerId, boolean giveUp) {
		super(playerId);
		this.giveUp = giveUp;
	}

	//JAVADOC
	public boolean isGiveUp() {
		return giveUp;
	}
}
