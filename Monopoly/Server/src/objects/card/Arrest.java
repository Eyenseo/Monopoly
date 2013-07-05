package objects.card;

import objects.Player;
import objects.map.FieldCircularList;

import java.io.Serializable;

/**
 * Arrest is the jail card.
 *
 * @version 1
 */
public class Arrest extends Card implements Serializable {
	private static final long serialVersionUID = -8298343677959644482L;
	private FieldCircularList jail;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public Arrest(String name, String text) {
		super(name, text);
	}

	/**
	 * @param jail The value determines the FieldCircularList object that is the Jail.
	 */
	public void setJail(FieldCircularList jail) {
		this.jail = jail;
	}

	/**
	 * The method will set the player position to jail and set the player to be inJail
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		fireMessageEvent(player.getPlayerId());
		player.setPosition(jail);
		player.setInJail(true);
	}
}
