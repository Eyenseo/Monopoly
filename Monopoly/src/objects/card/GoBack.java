package objects.card;
//JAVADOC

import objects.Player;

import java.io.Serializable;

/**
 * GoBack is the back card.
 */
public class GoBack extends Card implements Serializable {
	private static final long serialVersionUID = 7543740810702116460L;
	private int fields;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public GoBack(String name, String text, int fields) {
		super(name, text);
		this.fields = fields;
	}

	//JAVADOC
	@Override
	public void action(Player player) {
		menu.showCardText(this);
		player.move(fields, false);
		player.getField().action(player);
	}
}
