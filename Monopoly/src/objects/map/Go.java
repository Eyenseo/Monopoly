package objects.map;

import objects.Player;
import objects.exceptions.map.JustOneInstanceException;

//JAVADOC
public class Go extends NotPurchasable {
	private static boolean justOneInstance = false;
	private final int TURNMONEY;

	//JAVADOC
	public Go(String name, int turnmoney) throws JustOneInstanceException {
		super(name);
		this.TURNMONEY = turnmoney;
		if(justOneInstance) {
			throw new JustOneInstanceException(name);
		}
		Go.justOneInstance = true;
	}

	//JAVADOC
	@Override
	public void action(Player player) {
		player.addMoney(TURNMONEY);
	}
}
