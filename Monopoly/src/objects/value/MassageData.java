package objects.value;

/**
 * MassageData is a class for the network communication that is intended to transfer Text from a card or a new chat message
 */
public class MassageData {
	private int         userId;
	private String      text;
	private MassageType typ;

	/**
	 *
	 * @param userId The value determines the id of the player the MessageData is from
	 * @param typ  The value determines the type of the MessageData either COMMUNITY, CHANCE, CHAT
	 * @param text The value determines the text of the message
	 */
	public MassageData(int userId, MassageType typ, String text) {
		this.userId = userId;
		this.typ = typ;
		this.text = text;
	}

	/**
	 *
	 * @return  The value determines the player id the MessageData is from
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 *
	 * @return the return value is the text of the message
	 */
	public String getText() {
		return text;
	}

	/**
	 *
	 * @return the return value is the type of the message
	 */
	public MassageType getTyp() {
		return typ;
	}

	/**
	 * the enum field is used to specify the typ of a MessageData
	 */
	public enum MassageType {
		COMMUNITY, CHANCE, CHAT
	}
}
