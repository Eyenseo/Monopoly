package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;

import java.io.Serializable;

/**
 * The Parking class is the FieldCircularList subclass that keeps all the fines of the Player Objects, if there is none
 * or more than one Instance of this class the game will not start.
 */
public class Parking extends NotPurchasable implements Serializable {
	private static final long    serialVersionUID = 6193402746206108126L;
	private static       boolean justOneInstance  = false;
	private              int     money            = 0;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public Parking(String name) throws MoreThanOneDataSetException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneDataSetException(name);
		}
		//TODO right reference?
		Parking.justOneInstance = true;
	}

	/**
	 * @param money The value determines how much money has to be added to the current money.
	 */
	public void addMoney(int money) {
		this.money += money;
	}

	/**
	 * The method will transfer the collected money to the player
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override public void action(Player player) {
		player.addMoney(money);
		money = 0;
	}
}
