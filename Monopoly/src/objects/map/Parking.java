package objects.map;

//TODO Doc
public class Parking extends NotPurchasable {
	private int money;

	//TODO Doc
	public Parking(int money) {
		super("Frei Parken");
		this.money = money;
	}

	//TODO Doc
	public int getMoney() {
		return this.money;
	}

	//TODO Doc
	public void setMoney(int money) {
		this.money = money;
	}
}
