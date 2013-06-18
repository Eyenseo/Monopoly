package objects.value;

public class CardData {
	private String playerName;
	private String text;
	private String typ;

	public CardData(String playerName, String typ, String text) {
		this.playerName = playerName;
		this.typ = typ;
		this.text = text;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getText() {
		return text;
	}

	public String getTyp() {
		return typ;
	}
}
