package objects;

import objects.card.Jailbait;
import objects.map.FieldCircularList;
import objects.map.notPurchasable.Go;
import objects.map.purchasable.PurchasableCircularList;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

//JAVADOC
public class Player {
	private final String            NAME;
	private       boolean           inJail;
	private       int               money;
	private       int[]             dices;
	private       FieldCircularList field;
	private Random                             randomGenerator = new Random();
	private Vector<Jailbait>                   jailbait        = new Vector<Jailbait>();
	private ArrayList<PurchasableCircularList> property        = new ArrayList<PurchasableCircularList>();
	//TODO have only one Random Generator for all Player

	//JAVADOC
	public Player(String name, int money) {
		this.NAME = name;
		this.money = money;
		this.inJail = false;
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
		this.money -= purchasable.getPrice();
		purchasable.setOwner(this);
	}

	//JAVADOC
	public void setOwnship(PurchasableCircularList purchasable) {
		purchasable.setOwner(this);
	}

	//JAVADOC
	public void addProperty(PurchasableCircularList purchasable) {
		this.property.add(purchasable);
	}

	//JAVADOC
	public void removeProperty(PurchasableCircularList purchasable) {
		this.property.remove(purchasable);
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
	public boolean isJailbait() {
		return !jailbait.isEmpty();
	}

	//JAVADOC
	public void pay(int amount) {
		this.money -= amount;
	}

	//JAVADOC
	public FieldCircularList getField() {
		return this.field;
	}

	//JAVADOC
	public void setField(FieldCircularList field) {
		this.field = field;
	}

	//JAVADOC
	public void addJailbait(Jailbait jailbait) {
		this.jailbait.add(jailbait);
	}

	public void useJailbait() {
		jailbait.firstElement().freePlayer(this);
		jailbait.remove(jailbait.firstElement());
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
