package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;

//JAVADOC
public class Jail extends NotPurchasable {
	private Parking parking;
	private static boolean justOneInstance = false;

	//JAVADOC
	public Jail(String name) throws MoreThanOneDataSetException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneDataSetException(name);
		}
		Jail.justOneInstance = true;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public void payFine(Player player) {
		player.pay(1000);
		parking.addMoney(1000);
		player.setInJail(false);
	}

	@Override
	//JAVADOC
	public void action(Player player) {
		//TODO Check if here has something to be done ... ?
	}
}
