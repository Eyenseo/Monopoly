package objects.map.notPurchasable;

import objects.Player;

//JAVADOC
public class Tax extends NotPurchasable {
	private       Parking parking;
	private final int     bill;

	//JAVADOC
	public Tax(String name, int bill) {
		super(name);
		this.bill = bill;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	@Override
	public void action(Player player) {
		player.pay(bill);
		parking.addMoney(bill);
	}
}
