package objects.value;

public class MassageData {
	private int         userId;
	private String      text;
	private MassageType typ;

	public MassageData(int userId, MassageType typ, String text) {
		this.userId = userId;
		this.typ = typ;
		this.text = text;
	}

	public int getUserId() {
		return userId;
	}

	public String getText() {
		return text;
	}

	public MassageType getTyp() {
		return typ;
	}

	public enum MassageType {
		COMMUNITY, EVENT, CHAT
	}
}
