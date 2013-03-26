package objects.value;

/**
 * The Facility class holds the information of the facilities.
 *
 * @author Eyenseo
 * @version 0.1
 */
class Facility extends Purchasable {
	/**
	 * @param position The value determines the numeric position of the field.
	 * @param name     The value determines the name of the field.
	 * @param price    The value determines the price of the field that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the street in the different stages.
	 *                 <ol start="0">
	 *                 <li>Water Work <b>or</b> Electric Company</li>
	 *                 <li>Water Work <b>and</b> Electric Company</li>
	 *                 </ol>
	 * @param stage    The value determines the stage the income is at.
	 */
	Facility(int position, String name, int price, int[] income, int stage) {
		super(position, name, price, income, stage);
	}
}
