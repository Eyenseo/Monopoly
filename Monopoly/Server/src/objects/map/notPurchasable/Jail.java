package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;

import java.io.Serializable;

/**
 * The Jail class is the FieldCircularList subclass, if there is none or more than one Instance of this class the game
 * will not start.
 */
public class Jail extends NotPurchasable implements Serializable {
	private static final long serialVersionUID = -7791364597281521600L;
	private Parking parking;
	private static boolean justOneInstance = false;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public Jail(String name) throws MoreThanOneDataSetException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneDataSetException(name);
		}
		Jail.justOneInstance = true;
	}

	/**
	 * @param parking The value determines the Parking object.
	 */
	public void setParking(Parking parking) {
		this.parking = parking;
	}

	/**
	 * The method removes the fine from the player, adds it to the Parking object and setts the Player free.
	 *
	 * @param player The value determines the Player object that has to pay the fine.
	 */
	public void payFine(Player player) {
		player.pay(1000);
		parking.addMoney(1000);
		player.setInJail(false);
	}

	/**
	 * The method does nothing
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override public void action(Player player) {
	}
}
