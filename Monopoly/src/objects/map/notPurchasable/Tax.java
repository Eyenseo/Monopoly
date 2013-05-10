package objects.map.notPurchasable;

import objects.Player;

/**
 * The Tax Class is the FieldCircularList subclass.
 *
 * @author Eyenseo
 * @version 1
 */
public class Tax extends NotPurchasable {
	private       Parking parking;
	private final int     bill;

	/**
	 * @param name The value determines the name of the Field.
	 * @param bill The value determines the amount a player has to pay if stands on this Field.
	 */
	public Tax(String name, int bill) {
		super(name);
		this.bill = bill;
	}

	/**
	 * @param parking The value determines the Parking object.
	 */
	public void setParking(Parking parking) {
		this.parking = parking;
	}

	@Override
	//JAVADOC
	public void action(Player player) {
		player.pay(bill);
		parking.addMoney(bill);
	}
}
