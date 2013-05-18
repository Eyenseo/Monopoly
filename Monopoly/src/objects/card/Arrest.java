package objects.card;

import objects.Player;
import objects.map.FieldCircularList;

import java.io.Serializable;
//JAVADOC

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

	//JavaDoc
	@Override
	public void action(Player player) {
		menu.showCardText(this);
		player.setInJail(true);
		player.setPosition(jail);
	}
}
