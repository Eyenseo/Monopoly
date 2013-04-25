package objects.map;

import objects.Player;

/**
 * The structure of PurchasableCircularList and the subclasses is a circular list of all objects that
 * belong together e.g. all Stations in one list, or all Streets that are red.
 *
 * @author Eyenseo
 * @version 1
 */
public abstract class PurchasableCircularList extends Field {
	protected final int                     PRICE;
	//TODO The price has to be able to be set by the player [-1 for not for sale and everything above for sale (should lead to a bidding option)].
	protected final int[]                   INCOME;
	protected final int                     MORTGAGE;   //Hypothek
	protected       Player                  owner;
	protected       int                     stage;
	protected       PurchasableCircularList next;
	protected       boolean                 inMortgage;

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
		this.next = this;
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
		this.owner = owner;
		if(sameOwnerCheck(this)) {
			stage = 1;
		}
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
	public int getBill() {
		return this.INCOME[this.stage];
	}

	/**
	 * @param purchasable The value determines the next object in the circular list of objects that belong together.
	 */
	//TODO Move to constructor
	public void add(PurchasableCircularList purchasable) {
		purchasable.next = this.next;
		this.next = purchasable;
	}

	/**
	 * @return The return value is the reference to the next PurchasableCircularList object.
	 */
	private PurchasableCircularList getNext() {
		return this.next;
	}

	//JAVADOC
	private boolean sameOwnerCheck(PurchasableCircularList start) {
		if(this.owner.equals(this.next.getOwner())) {
			if(this.next.getNext().equals(start)) {
				return true;
			}
			return this.next.sameOwnerCheck(start);
		}
		return false;
	}

	//JAVADOC
	@Override
	public String toString() {
		return this.getName() + ((getOwner() != null) ? " (" + getOwner() + ")" : "");
	}
}
