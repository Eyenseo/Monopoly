package objects.map.notPurchasable;

import objects.Player;
import objects.value.map.FieldData;
import objects.value.map.TaxData;

import java.io.Serializable;

/**
 * The Tax Class is the FieldCircularList subclass.
 */
public class Tax extends NotPurchasable implements Serializable {
	private static final long serialVersionUID = 5530039603072031838L;
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

	/**
	 * The method will transfer the tax money from the player to <code>Parking</code>
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override public void action(Player player) {
		player.pay(bill);
		parking.addMoney(bill);
	}

	/**
	 * @return The return value is the FieldData of The field with its current attributes
	 */
	@Override public FieldData toFieldData() {
		return new TaxData(FIELDNUMBER, NAME);
	}
}
