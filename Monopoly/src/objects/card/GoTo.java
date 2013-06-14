package objects.card;

import objects.Player;
import objects.map.FieldCircularList;

import java.io.Serializable;

/**
 * GoTo is the go to card
 *
 * @version 1
 */
public class GoTo extends Card implements Serializable {
	private static final long serialVersionUID = 5348138600989361405L;
	private final String            FIELDNAME;
	private final boolean           OVERGO;
	private       FieldCircularList field;
	private       FieldCircularList go;

	/**
	 * @param name   The value determines the name of the Card.
	 * @param text   The value determines the text of the Card.
	 * @param field  The value determines the name of the FieldCircularList object the card sends the player to.
	 * @param overGo The value determines if the player may go over Go while moving to the new destination.
	 */
	public GoTo(String name, String text, String field, boolean overGo) {
		super(name, text);
		FIELDNAME = field;
		OVERGO = overGo;
	}

	/**
	 * @param field The value determines the FieldCircularList object the card sends the player to.
	 */
	public void setField(FieldCircularList field) {
		this.field = field;
	}

	/**
	 * @return The return value is the name of the Field the card sends the player to.
	 */
	public String getFieldName() {
		return FIELDNAME;
	}

	/**
	 * @param field The value determines the Go object.
	 */
	public void setGo(FieldCircularList field) {
		go = field;
	}

	/**
	 * --- TODO
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	//TODO Use th GUI / use a event
	@Override
	public void action(Player player) {
		menu.showCardText(this);
		if(OVERGO && field.getFieldNumber() < player.getPosition().getFieldNumber()) {
			go.action(player);
		}
		player.setPosition(field);
		player.getPosition().action(player);
	}
}
