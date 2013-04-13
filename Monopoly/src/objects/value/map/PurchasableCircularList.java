package objects.value.map;

import objects.value.Player;

/**
 * The structure of PurchasableCircularList and the subclasses is a circular list of all objects that
 * belong together e.g. all Stations in one list, or all Streets that are red.
 *
 * @author Eyenseo
 * @version 1
 */
public abstract class PurchasableCircularList extends Field {
	private final int                     PRICE;
	private final int[]                   INCOME;
	private final int                     MORTGAGE;   //Hypothek
	private       Player                  owner;
	private       int                     stage;
	private       PurchasableCircularList next;

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the street in the different stages.
	 * @param mortgage The value determines the amount of the mortgage.
	 * @param stage    The value determines the stage the income is at.
	 * @param owner    The value determines the owner.
	 */
	//TODO Simplify the constructor by assigning default values
	PurchasableCircularList(String name, int price, int[] income, int mortgage, int stage, Player owner) {
		super(name);
		this.PRICE = price;
		this.INCOME = income;
		this.MORTGAGE = mortgage;
		this.stage = stage;
		this.owner = owner;
		this.next = this;   //TODO Check if this points to the PurchasableCircularList class or the subclass
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

	//TODO Doc
	public int getMortgage() {
		return this.MORTGAGE;
	}

	//TODO Doc
	public void setStage(int stage) {
		this.stage = stage;
	}

	//TODO Doc
	public int getStage() {
		return this.stage;
	}

	/**
	 * @return The return value is the current income.
	 */
	public int getBill(Player player) {
		return this.INCOME[this.stage];
	}

	/**
	 * @param purchasable The value determines the next object in the circular list of objects that belong together
	 *                    .
	 */
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

	//TODO Doc
	private boolean sameOwnerCheck(PurchasableCircularList start) {
		if(this.owner.equals(this.next.getOwner())) {
			if(this.next.getNext().equals(start)) {
				return true;
			}
			return this.next.sameOwnerCheck(start);
		}
		return false;
	}
}
