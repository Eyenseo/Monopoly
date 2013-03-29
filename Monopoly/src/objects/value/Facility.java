package objects.value;

/**
 * The Facility class holds the information of the facilities.     <br>
 * The structure of all Facilities is a ring list of all Facilities that belong together.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class Facility extends Purchasable {

	/**
	 * @param name     The value determines the name of the Facility.
	 * @param price    The value determines the price of the Facility that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the Facility in the different stages.
	 *                 <ol start="0">
	 *                 <li>Water Work <b>or</b> Electric Company</li>
	 *                 <li>Water Work <b>and</b> Electric Company</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage of the Facility.
	 * @param stage    The value determines the stage the income is at.
	 * @param owner    The value determines the owner of the Facility.
	 */
	public Facility(String name, int price, int[] income, int mortgage, int stage, Player owner) {
		super(name, price, income, mortgage, stage, owner);
	}

	/**
	 * @return The return value is the amount that has to be payed if someone else then the owner is on the Facility.
	 */
	@Override
	int getBill(Player player) {
		// TODO : Implement
		return 0;
	}
}
