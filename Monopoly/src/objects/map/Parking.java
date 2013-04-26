package objects.map;

import objects.exceptions.map.JustOneInstanceException;

//JAVADOC
public class Parking extends NotPurchasable {
	private static boolean justOneInstance = false;
	private        int     money           = 0;

	//JAVADOC
	public Parking(String name) throws JustOneInstanceException {
		super(name);
		if(justOneInstance) {
			throw new JustOneInstanceException(name);
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
