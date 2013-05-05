package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;

/**
 * The Go Class is the FieldCircularList subclass that is the Start of the game, if there is none or more than one Instance of this class the game will not start.
 *
 * @author Eyenseo
 * @version 1
 */
public class Go extends NotPurchasable {
	private static boolean justOneInstance = false;
	private final int TURNMONEY;

	/**
	 * @param name      The value determines the name of the Field.
	 * @param turnMoney The value determines the amount of money a Player gets when standing on Go.
	 * @throws MoreThanOneDataSetException The Exception will be thrown if there is more than one instance of Go.
	 */
	public Go(String name, int turnMoney) throws MoreThanOneDataSetException {
		super(name);
		this.TURNMONEY = turnMoney;
		if(justOneInstance) {
			throw new MoreThanOneDataSetException(name);
		}
		Go.justOneInstance = true;
	}

	//JAVADOC
	@Override
	public void action(Player player) {
		player.addMoney(TURNMONEY);
	}
}
