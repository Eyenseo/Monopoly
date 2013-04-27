package objects.map.notPurchasable;

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
}
