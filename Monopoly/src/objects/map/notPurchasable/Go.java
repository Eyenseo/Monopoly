package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;

//JAVADOC
public class Go extends NotPurchasable {
	private static boolean justOneInstance = false;
	private final int TURNMONEY;

	//JAVADOC
	public Go(String name, int turnmoney) throws MoreThanOneDataSetException {
		super(name);
		this.TURNMONEY = turnmoney;
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
