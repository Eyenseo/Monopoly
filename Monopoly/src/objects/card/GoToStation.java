package objects.card;

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

	/**
	 * The method will fire a CardEvent
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		fireCardEvent(player.getName());
		FieldCircularList field = player.getPosition();
		while(!(field instanceof Station)) {
			field = field.getNext();
		}
		player.setPosition(field);
		field.action(player);
	}
}
