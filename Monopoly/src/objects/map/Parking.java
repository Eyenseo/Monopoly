package objects.map;

//JAVADOC
public class Parking extends NotPurchasable {
	private int money;

	//JAVADOC
	public Parking(int money) {
		super("Frei Parken");
		this.money = money;
	}

	//JAVADOC
	public int getMoney() {
		return this.money;
	}

	//JAVADOC
	public void setMoney(int money) {
		this.money = money;
	}
}
