package objects.card;

import objects.Player;
import objects.map.notPurchasable.Parking;

import java.io.Serializable;

/**
 * Payment is a transaction card (gain or loss).
 *
 * @version 1
 */
public class Payment extends Card implements Serializable {
	private static final long serialVersionUID = 3661515495615563278L;
	private final int     dm;
	private       Parking parking;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public Payment(String name, String text, int dm) {
		super(name, text);
		this.dm = dm;
	}

	/**
	 * @param parking The value determines parking.
	 */
	public void setParking(Parking parking) {
		this.parking = parking;
	}

	/**
	 * The method will fire a MessageEvent
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		fireMessageEvent(player.getPlayerId());
		if(dm > 0) {
			player.addMoney(dm);
		} else {
			player.pay(dm);
			parking.addMoney(dm);
		}
	}
}
