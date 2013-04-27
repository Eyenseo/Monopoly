package objects.map.purchasable;

/**
 * The structure of FacilityCircularList is a circular list of all FacilityCircularList objects.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class FacilityCircularList extends PurchasableCircularList {

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the income at the different stages.
	 *                 <ol start="0">
	 *                 <li>Water Work <b>or</b> Electric Company</li>
	 *                 <li>Water Work <b>and</b> Electric Company</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage.
	 * @param stage    The value determines the stage the income is at.
	 * @param owner    The value determines the owner.
	 */
	public FacilityCircularList(String name, int price, int[] income, int mortgage) {
		super(name, price, income, mortgage);
	}

	/**
	 * @return The return value is the current income.
	 */
	public int getBill() {
		// TODO Implement - check the unique price calculation
		return 0;
	}
}
