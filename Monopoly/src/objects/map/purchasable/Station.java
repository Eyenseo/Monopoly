package objects.map.purchasable;

/**
 * The structure of Station is a circular list of all Station objects.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class Station extends PurchasableCircularList {
	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the income at the different stages.
	 *                 <ol start="0">
	 *                 <li>1 Station</li>
	 *                 <li>2 Stations</li>
	 *                 <li>3 Stations</li>
	 *                 <li>4 Stations</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage.
	 */
	public Station(String name, int price, int[] income, int mortgage) {
		super(name, price, income, mortgage);
	}

	/**
	 * This method checks if all group members are owned by the same owner. If they are the stage is increased.
	 */
	@Override void sameOwnerCheck() {
		int index = 0;
		PurchasableCircularList next = this.nextGroupElement;
		while(!next.equals(this)) {
			if(next.getOwner() != null && next.getOwner().equals(owner)) {
				index++;
			}
			next = next.getNextGroupElement();
		}
		next = this;
		do {
			next.stage = index;
			next = next.getNextGroupElement();
		} while(!next.equals(this));
	}
}
