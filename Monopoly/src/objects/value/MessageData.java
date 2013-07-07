package objects.value;

/**
 * MessageData is a class for the network communication that is intended to transfer Text from a card or a new chat message
 */
public class MessageData {
	private int         userId;
	private String      text;
	private MessageType typ;

	/**
	 *
	 * @param userId The value determines the id of the player the MessageData is from
	 * @param typ  The value determines the type of the MessageData either COMMUNITY, CHANCE, CHAT
	 * @param text The value determines the text of the message
	 */
	public MessageData(int userId, MessageType typ, String text) {
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
	public MessageType getTyp() {
		return typ;
	}

	/**
	 * the enum field is used to specify the typ of a MessageData
	 */
	public enum MessageType {
		COMMUNITY, CHANCE, CHAT
	}
}
