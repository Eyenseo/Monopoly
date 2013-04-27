package objects.map.notPurchasable;

import objects.exceptions.core.MoreThanOneInstanceException;

//JAVADOC
public class Parking extends NotPurchasable {
	private static boolean justOneInstance = false;
	private        int     money           = 0;

	//JAVADOC
	public Parking(String name) throws MoreThanOneInstanceException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneInstanceException(name);
		}
		Parking.justOneInstance = true;
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