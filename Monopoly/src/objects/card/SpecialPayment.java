package objects.card;

import objects.Player;

import java.io.Serializable;
import java.util.HashMap;

/**
 * SpecialPayment is a special transaction card (player based transactions).
 *
 * @version 1
 */
public class SpecialPayment extends Card implements Serializable {
	private static final long serialVersionUID = 4614526083952983870L;
	private final int                      dm;
	private       HashMap<Integer, Player> playerHashMap;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public SpecialPayment(String name, String text, int dm) {
		super(name, text);
		this.dm = dm;
	}

	/**
	 * The method will fire a MessageEvent
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		int amount = 0;
		fireMessageEvent(player.getPlayerId());
		if(dm < 0) {
			int positive = dm * -1;
			for(Player p : playerHashMap.values()) {
				if(!player.equals(p)) {
					p.addMoney(positive);
					amount += positive;
				}
			}
			player.pay(amount);
		} else {
			for(Player p : playerHashMap.values()) {
				if(!player.equals(p)) {
					p.pay(dm);
					amount += dm;
				}
			}
			player.addMoney(amount);
		}
	}

	/**
	 * @param playerHashMap the value determines the array list where all player are stored
	 */
	public void setPlayerHashMap(HashMap<Integer, Player> playerHashMap) {
		this.playerHashMap = playerHashMap;
	}
}
