package objects;

import objects.map.Field;
import objects.map.PurchasableCircularList;

import java.util.HashMap;
import java.util.Random;

//JAVADOC
public class Player {
	private final String  NAME;
	private       boolean inJail;
	private       boolean jailbaitEvent;
	private       boolean jailbaitCommunity;
	private       int     money;
	private       int[]   dices;
	private HashMap<String, Field> ownedFieldMap   = new HashMap<String, Field>();
	private Random                 randomGenerator = new Random(); //TODO have only one Random Generator for all Player

	//JAVADOC
	public Player(String name, int money) {
		this.NAME = name;
		this.money = money;
		this.inJail = false;
		this.jailbaitEvent = false;
		this.jailbaitCommunity = false;
		dices = new int[2];
	}

	//JAVADOC
	public String getName() {
		return this.NAME;
	}

	//JAVADOC
	public boolean isInJail() {
		return this.inJail;
	}

	//JAVADOC
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	//JAVADOC
	public void buy(PurchasableCircularList purchasable) {
		if(purchasable.getOwner() != null) {
			purchasable.getOwner().addMoney(purchasable.getPrice());
		}
		this.money -= purchasable.getPrice();
		purchasable.setOwner(this);
		this.ownedFieldMap.put(purchasable.getName(), purchasable);
	}

	//JAVADOC
	public void addMoney(int amount) {
		this.setMoney(getMoney() + amount);
	}

	//JAVADOC
	public int getMoney() {
		return this.money;
	}

	//JAVADOC
	public void setMoney(int money) {
		this.money = money;
	}

	//JAVADOC
	public int[] throwDice() {
		dices[0] = randomGenerator.nextInt(6) + 1;
		dices[1] = randomGenerator.nextInt(6) + 1;
		return dices;
	}

	//JAVADOC
	public int[] getDices() {
		return this.dices;
	}

	//JAVADOC
	public boolean isJailbaitEvent() {
		return jailbaitEvent;
	}

	//JAVADOC
	public boolean isJailbaitCommunity() {
		return jailbaitCommunity;
	}

	@Override
	public String toString() {
		return this.NAME;
	}

	//JAVADOC
	public void pay(int amount) {
		this.money -= amount;
	}
}
