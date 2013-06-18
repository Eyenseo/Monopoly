package objects.card;

import objects.Player;
import objects.map.notPurchasable.Parking;
import objects.map.purchasable.PurchasableCircularList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * StreetWork is the street work card (Strassen arbeiten).
 *
 * @version 1
 */
public class StreetWork extends Card implements Serializable {
	private static final long serialVersionUID = 8081671259197653645L;
	private final int     dmHouse;
	private final int     dmHotel;
	private       Parking parking;

	/**
	 * @param name    The value determines the name of the Card.
	 * @param text    The value determines the text of the Card.
	 * @param dmHouse The value determines the amount that has to be payed for a house.
	 * @param dmHotel The value determines the mout that has to be payed for a hotel.
	 */
	public StreetWork(String name, String text, int dmHouse, int dmHotel) {
		super(name, text);
		this.dmHouse = dmHouse;
		this.dmHotel = dmHotel;
	}

	/**
	 * @param parking The value determines parking.
	 */
	public void setParking(Parking parking) {
		this.parking = parking;
	}

	/**
	 * The method will fire a CardEvent
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		int amount = 0;
		ArrayList<PurchasableCircularList> property = player.getProperties();
		fireCardEvent(player.getName());
		for(PurchasableCircularList p : property) {
			if(p.getStage() < p.getMaxStage()) {
				amount += p.getStage() * dmHouse;
			} else {
				amount += p.getMaxStage() * dmHotel;
			}
		}
		player.pay(amount);
		parking.addMoney(amount);
	}
}
