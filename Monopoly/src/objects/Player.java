package objects;

import objects.map.Field;
import objects.map.notPurchasable.Go;
import objects.map.purchasable.PurchasableCircularList;

import java.util.ArrayList;
import java.util.Random;

//JAVADOC
public class Player {
	private final String  NAME;
	private       boolean inJail;
	private       boolean jailbaitEvent;
	private       boolean jailbaitCommunity;
	private       int     money;
	private       int[]   dices;
	private       Field   field;
	private Random                             randomGenerator = new Random();
	private ArrayList<PurchasableCircularList> property        = new ArrayList<PurchasableCircularList>();
	//TODO have only one Random Generator for all Player

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
		purchasable.setOwner(this);
		this.money -= purchasable.getPrice();
		this.property.add(purchasable);
		purchasable.setPrice(-1);
	}

	//JAVADOC
	public void addMoney(int amount) {
		this.money += amount;
	}

	//JAVADOC
	public int getMoney() {
		return this.money;
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

	//JAVADOC
	public void pay(int amount) {
		this.money -= amount;
	}

	//JAVADOC
	public Field getField() {
		return this.field;
	}

	//JAVADOC
	public void setField(Field field) {
		this.field = field;
	}

	//JAVADOC
	public boolean move() {
		throwDice();
		boolean doubles = dices[0] == dices[1];
		if(!inJail || !doubles) {
			move(dices[0] + dices[1], true);
		}
		field.action(this);
		return doubles;
	}

	//JAVADOC
	private void move(int by, boolean overGo) {
		if(field.getDiceNext(by) != null) {
			field = field.getDiceNext(by);
		} else {
			for(int i = 0; i < by; i++) {
				field = field.getNext();
				if(overGo && field instanceof Go) {
					field.action(this);
				}
			}
		}
	}

	//JAVADOC
	public int[] throwDice() {
		dices[0] = randomGenerator.nextInt(6) + 1;
		dices[1] = randomGenerator.nextInt(6) + 1;
		return dices;
	}

	//JAVADOC
	public ArrayList<PurchasableCircularList> getProperties() {
		return property;
	}
}
