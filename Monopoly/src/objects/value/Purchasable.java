package objects.value;

/**
 * The Purchasable class is the superclass of all Fields that are purchasable. <br>
 * The structure of all Purchasables is a ring list of all Purchasables that belong together e.g. all Stations
 * in one list, or all Streets that are red.
 *
 * @author Eyenseo
 * @version 0.1
 */
abstract class Purchasable extends Field {
	final int   PRICE;
	final int[] INCOME;
	final int   MORTGAGE;   //Hypothek
	Player owner;
	int    stage;
	private Purchasable next;

	/**
	 * @param name     The value determines the name of the Purchasable.
	 * @param price    The value determines the price of the Purchasable that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the street in the different stages.
	 * @param mortgage The value determines the amount of the mortgage of the Purchasable.
	 * @param stage    The value determines the stage the income is at.
	 * @param owner    The value determines the owner of the Purchasable.
	 */
	//TODO Simplify the constructor by assigning default values
	Purchasable(String name, int price, int[] income, int mortgage, int stage, Player owner) {
		super(name);
		this.PRICE = price;
		this.INCOME = income;
		this.MORTGAGE = mortgage;
		this.stage = stage;
		this.owner = owner;
		this.next = this;   //TODO Check if this points to the Purchasable class or the subclass
	}

	/**
	 * @return The return value is the owner of the Purchasable.
	 */
	Player getOwner() {
		return this.owner;
	}

	/**
	 * @param owner The value determines the owner of the Purchasable.
	 */
	void buy(Player owner) {
		/* TODO: Decrease the money of the player and check if all Purchasables have the same owner to set the
		stage according
		 */
		this.owner = owner;
	}

	/**
	 * @return The return value that has to be payed to own the Purchasable.
	 */
	int getPrice() {
		return this.PRICE;
	}

	/**
	 * @return The return value is the amount that has to be payed if someone else then the owner is on the
	 *         Purchasable.
	 */
	int getBill(Player player) {
		return this.INCOME[this.stage];
	}

	/**
	 * @param purchasable The value determines the next Purchasable in the ring list of Purchasables that belong
	 *                    together.
	 */
	public void add(Purchasable purchasable) {
		purchasable.next = this.next;
		this.next = purchasable;
	}

	/**
	 * @return The return value is the reference to the next Purchasable.
	 */
	private Purchasable getNext() {
		return this.next;
	}
}
