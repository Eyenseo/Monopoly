package objects.card;

import objects.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * SpecialPayment is a special transaction card (player based transactions).
 *
 * @version 1
 */
public class SpecialPayment extends Card implements Serializable {
	private static final long serialVersionUID = 4614526083952983870L;
	private final int               dm;
	private       ArrayList<Player> playerArrayList;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public SpecialPayment(String name, String text, int dm) {
		super(name, text);
		this.dm = dm;
	}

	/**
	 * The method will fire a CardEvent
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		int amount = 0;
		fireCardEvent(player.getName());
		for(Player p : playerArrayList) {
			if(!player.equals(p)) {
				p.addMoney(dm);
				amount += dm;
			}
		}
		player.pay(amount);
	}

	/**
	 * @param playerArrayList the value determines the array list where all player are stored
	 */
	public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
		this.playerArrayList = playerArrayList;
	}
}
