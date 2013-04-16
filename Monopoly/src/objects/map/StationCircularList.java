package objects.map;

import objects.Player;

/**
 * The structure of StationCircularList is a circular list of all StationCircularList objects.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class StationCircularList extends PurchasableCircularList {
	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the income at the different stages.
	 *                 <ol start="0">
	 *                 <li>1 StationCircularList</li>
	 *                 <li>2 Stations</li>
	 *                 <li>3 Stations</li>
	 *                 <li>4 Stations</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage.
	 * @param stage    The value determines the stage the income is at.
	 * @param owner    The value determines the owner.
	 */
	public StationCircularList(String name, int price, int[] income, int mortgage, int stage, Player owner) {
		super(name, price, income, mortgage, stage, owner);
	}
}
