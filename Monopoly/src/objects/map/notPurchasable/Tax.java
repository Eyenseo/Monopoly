package objects.map.notPurchasable;

//JAVADOC
public class Tax extends NotPurchasable {
	private final int bill;

	//JAVADOC
	public Tax(String name, int bill) {
		super(name);
		this.bill = bill;
	}
}
