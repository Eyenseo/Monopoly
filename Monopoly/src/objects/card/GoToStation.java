package objects.card;
//JAVADOC

import objects.Player;
import objects.map.FieldCircularList;
import objects.map.purchasable.Station;

import java.io.Serializable;

/**
 * GoToStation is a special_movement card.
 *
 * @version 1
 */
public class GoToStation extends Card implements Serializable {
	private static final long serialVersionUID = 1800701804048783935L;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public GoToStation(String name, String text) {
		super(name, text);
	}

	//JAVADOC
	@Override
	public void action(Player player) {
		menu.showCardText(this);
		FieldCircularList field = player.getField();
		while(!(field instanceof Station)) {
			field = field.getNext();
		}
		player.setField(field);
		field.action(player);
	}
}
