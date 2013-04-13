package objects.value;

import objects.value.map.Field;
import objects.value.map.PurchasableCircularList;

import java.util.HashMap;

//TODO Doc
public class Player {
	private final String  NAME;
	private       boolean inJail;
	private       int     money;
	private       int     position;
	private HashMap<String, Field> ownedFieldMap = new HashMap<String, Field>();

	//TODO Doc
	public Player(String name, boolean inJail, int money, int position) {
		this.NAME = name;
		this.inJail = inJail;
		this.money = money;
		this.position = position;
	}

	//TODO Doc
	public int getPosition() {
		return this.position;
	}

	//TODO Doc
	public String getName() {
		return this.NAME;
	}

	//TODO Doc
	public int getMoney() {
		return this.money;
	}

	//TODO Doc
	public void setMoney() {
	}

	//TODO Doc
	public boolean isInJail() {
		return this.inJail;
	}

	//TODO Doc
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	//TODO Doc
	public void buy(PurchasableCircularList purchasable) {
		this.money -= purchasable.getPrice();
		purchasable.setOwner(this);
		ownedFieldMap.put(purchasable.getName(), purchasable);
	}

	//TODO Doc
	public void moveBy(int amount) {
		this.position += amount;
		if(this.position > 39) {
			this.position -= 40;
		}
	}
	// TODO:   Everything
}
