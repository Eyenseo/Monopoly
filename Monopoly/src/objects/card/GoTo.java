package objects.card;

import objects.Player;
import objects.map.FieldCircularList;
//JAVADOC

/**
 * GoTo is the go to card
 *
 * @version 1
 */
public class GoTo extends Card {
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

	@Override
	public void action(Player player) {
		menu.showCardText(this);
		if(OVERGO && field.getFieldNumber() < player.getField().getFieldNumber()) {
			go.action(player);
		}
		player.setField(field);
		player.getField().action(player);
	}
}
