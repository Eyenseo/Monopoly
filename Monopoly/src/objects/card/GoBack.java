package objects.card;
//JAVADOC

import objects.Player;

/**
 * GoBack is the back card.
 *
 * @version 1
 */
public class GoBack extends Card {
	private int fields;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public GoBack(String name, String text, int fields) {
		super(name, text);
		this.fields = fields;
	}

	public void action(Player player) {
		player.move(fields, false);
	}
}
