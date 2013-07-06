package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;
import objects.value.map.FieldData;
import objects.value.map.GoData;

import java.io.Serializable;

/**
 * The Go Class is the FieldCircularList subclass that is the Start of the game, if there is none or more than one
 * Instance of this class the game will not start.
 */
public class Go extends NotPurchasable implements Serializable {
	private static final long    serialVersionUID = 6005179230915968193L;
	private static       boolean justOneInstance  = false;
	private final int TURNMONEY;

	/**
	 * @param name      The value determines the name of the Field.
	 * @param turnMoney The value determines the amount of money a Player gets when standing on Go.
	 * @throws MoreThanOneDataSetException The Exception will be thrown if there is more than one instance of Go.
	 */
	public Go(String name, int turnMoney) throws MoreThanOneDataSetException {
		super(name);
		TURNMONEY = turnMoney;
		if(justOneInstance) {
			throw new MoreThanOneDataSetException(name);
		}
		Go.justOneInstance = true;
	}

	/**
	 * The method will add money to the player
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		player.addMoney(TURNMONEY);
	}

	/**
	 * @return The return value is the FieldData of The field with its current attributes
	 */
	@Override public FieldData toFieldData() {
		return new GoData(FIELDNUMBER, NAME);
	}
}
