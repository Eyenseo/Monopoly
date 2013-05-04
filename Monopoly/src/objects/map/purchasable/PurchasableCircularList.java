package objects.map.purchasable;

import objects.Player;
import objects.map.FieldCircularList;

import java.util.ArrayList;

/**
 * The structure of PurchasableCircularList and the subclasses is a circular list of all objects that
 * belong together e.g. all Stations in one list, or all Streets that are red.
 *
 * @author Eyenseo
 * @version 1
 */
public abstract class PurchasableCircularList extends FieldCircularList {
	protected final int[]                   INCOME;
	protected final int                     MORTGAGE;   //Hypothek
	protected       boolean                 inMortgage;
	protected final int                     PRICE;
	//TODO The price has to be able to be set by the player [-1 for not for sale and everything above for sale (should lead to a bidding option)].
	protected       int                     stage;
	protected       Player                  owner;
	protected       PurchasableCircularList nextGroupElement;

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the street in the different stages.
	 * @param mortgage The value determines the amount of the mortgage.
	 */
	//TODO Simplify the constructor by assigning default values
	PurchasableCircularList(String name, int price, int[] income, int mortgage) {
		super(name);
		this.PRICE = price;
		this.INCOME = income;
		this.MORTGAGE = mortgage;
		this.owner = null;
		this.stage = 0;
		this.inMortgage = false;
		this.nextGroupElement = this;
	}

	/**
	 * @return The return value is the amount that has to be payed to become the owner.
	 */
	public int getPrice() {
		return this.PRICE;
	}

	//JAVADOC
	public int getMortgage() {
		return this.MORTGAGE;
	}

	//JAVADOC
	public int getStage() {
		return this.stage;
	}

	//JAVADOC
	public int getMaxStage() {
		return INCOME.length - 1;
	}

	//JAVADOC
	public int[] getIncome() {
		return INCOME;
	}

	//JAVADOC
	public boolean isInMortgage() {
		return this.inMortgage;
	}

	//JAVADOC
	public void setInMortgage(boolean inMortgage) {
		this.inMortgage = inMortgage;
		if(inMortgage) {
			owner.addMoney(this.MORTGAGE);
		} else {
			owner.pay(this.MORTGAGE);
		}
	}

	/**
	 * @return The return value is the current income.
	 */
	//JAVADOC
	public int getBill(Player payer) {
		return this.INCOME[this.stage];
	}

	//JAVADOC
	public ArrayList<PurchasableCircularList> getGroupMembers() {
		ArrayList<PurchasableCircularList> members = new ArrayList<PurchasableCircularList>();
		PurchasableCircularList next = this;
		do {
			members.add(next);
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		return members;
	}

	//JAVADOC
	@Override
	public String toString() {
		return this.getName() + ((getOwner() != null) ? " (" + getOwner() + ")" : "");
	}

	/**
	 * @return The return value is the owner.
	 */
	public Player getOwner() {
		return this.owner;
	}

	/**
	 * @param owner The value determines the owner.
	 */
	public void setOwner(Player owner) {
		if(this.owner != null) {
			this.owner.removeProperty(this);
		}
		this.owner = owner;
		owner.addProperty(this);
		sameOwnerCheck();
	}

	//JAVADOC
	void sameOwnerCheck() {
		boolean sameOwner = true;
		PurchasableCircularList next = this.nextGroupElement;
		while(sameOwner && !next.equals(this)) {
			if(next.getOwner() == null || !next.getOwner().equals(owner)) {
				sameOwner = false;
			}
			next = next.getNextGroupElement();
		}
		if(sameOwner) {
			next = this;
			do {
				next.stage = 1;
				next = next.getNextGroupElement();
			} while(sameOwner && !next.equals(this));
		}
	}

	//JAVADOC
	public PurchasableCircularList getNextGroupElement() {
		return nextGroupElement;
	}

	//JAVADOC
	public void setNextGroupElement(PurchasableCircularList nextGroupElement) {
		nextGroupElement.nextGroupElement = this.nextGroupElement;
		this.nextGroupElement = nextGroupElement;
	}

	//JAVADOC
	//TODO improve
	@Override
	public void action(Player player) {
		if(owner != null) {
			player.pay(getBill(player));
			owner.addMoney(getBill(player));
		}
	}
}
