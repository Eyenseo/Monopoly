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
	protected       int                     stage;
	protected       Player                  owner;
	protected       PurchasableCircularList nextGroupElement;

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the street in the different stages.
	 * @param mortgage The value determines the amount of the mortgage.
	 */
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

	/**
	 * @return The return value is the mortgage value.
	 */
	public int getMortgage() {
		return this.MORTGAGE;
	}

	/**
	 * @return The return value is the stage.
	 */
	public int getStage() {
		return this.stage;
	}

	/**
	 * @return The return value is the last index of the income Array.
	 */
	public int getMaxStage() {
		return INCOME.length - 1;
	}

	/**
	 * @return The return value is the income Array.
	 */
	public int[] getIncome() {
		return INCOME;
	}

	/**
	 * @return The return value is true if the PurchasableCircularList is in mortgage.
	 */
	public boolean isInMortgage() {
		return this.inMortgage;
	}

	/**
	 * The method removes or adds the mortgage amount to the owner.
	 *
	 * @param inMortgage The value determines the mortgage stage.
	 */
	public void setInMortgage(boolean inMortgage) {
		this.inMortgage = inMortgage;
		if(inMortgage) {
			owner.addMoney(this.MORTGAGE);
		} else {
			owner.pay(this.MORTGAGE);
		}
	}

	/**
	 * @param player The value determines the the player who has to pay the bill (can be null).
	 * @return The return value is the current income.
	 */
	public int getBill(Player player) {
		return this.INCOME[this.stage];
	}

	/**
	 * @return The return value is an ArrayList of the group members of the PurchasableCircularList object.
	 */
	public ArrayList<PurchasableCircularList> getGroupMembers() {
		ArrayList<PurchasableCircularList> members = new ArrayList<PurchasableCircularList>();
		PurchasableCircularList next = this;
		do {
			members.add(next);
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		return members;
	}

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
	 * The method adds this object to the new owner and removes it from the old one.
	 *
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

	/**
	 * This method checks if all group members are owned by the same owner. If they are the stage is increased to 1.
	 */
	//TODO think of a way to keep the houses when trading
	void sameOwnerCheck() {
		boolean sameOwner = true;
		PurchasableCircularList next = this.nextGroupElement;
		while(sameOwner && !next.equals(this)) {
			if(next.getOwner() == null || !next.getOwner().equals(owner)) {
				sameOwner = false;
			}
			next = next.getNextGroupElement();
		}
		next = this;
		do {
			next.stage = sameOwner ? 1 : 0;
			next = next.getNextGroupElement();
		} while(sameOwner && !next.equals(this));
	}

	/**
	 * @return The return value is the next group member.
	 */
	public PurchasableCircularList getNextGroupElement() {
		return nextGroupElement;
	}

	/**
	 * @param nextGroupElement The value determines the next group member.
	 */
	public void setNextGroupElement(PurchasableCircularList nextGroupElement) {
		nextGroupElement.nextGroupElement = this.nextGroupElement;
		this.nextGroupElement = nextGroupElement;
	}

	//JAVADOC
	@Override
	public void action(Player player) {
		if(owner != null && !inMortgage) {
			player.pay(getBill(player));
			owner.addMoney(getBill(player));
		}
	}
}
