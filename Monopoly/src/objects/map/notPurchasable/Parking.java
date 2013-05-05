package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;

/**
 * The Parking class is the FieldCircularList subclass that keeps all the fines of the Player Objects, if there is none or more than one Instance of this class the game will not start.
 *
 * @author Eyenseo
 * @version 1
 */
public class Parking extends NotPurchasable {
	private static boolean justOneInstance = false;
	private        int     money           = 0;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public Parking(String name) throws MoreThanOneDataSetException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneDataSetException(name);
		}
		Parking.justOneInstance = true;
	}

	/**
	 * @return The return value is the amount of money the Parking object holds.
	 */
	public int getMoney() {
		return this.money;
	}

	/**
	 * @param money The value determines how much money has to be added to the current money.
	 */
	public void addMoney(int money) {
		this.money += money;
	}

	@Override
	//JAVADOC
	public void action(Player player) {
		player.addMoney(money);
		money = 0;
	}
}
