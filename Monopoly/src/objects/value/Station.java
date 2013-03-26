package objects.value;

/**
 * The Street class holds the information of the station.
 *
 * @author Eyenseo
 * @version 0.1
 */
class Station extends Purchasable {
	/**
	 * @param position The value determines the numeric position of the field.
	 * @param name     The value determines the name of the field.
	 * @param price    The value determines the price of the field that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the street in the different stages.
	 *                 <ol start="0">
	 *                 <li>1 Station</li>
	 *                 <li>2 Stations</li>
	 *                 <li>3 Stations</li>
	 *                 <li>4 Stations</li>
	 *                 </ol>
	 * @param stage    The value determines the stage the income is at.
	 */
	Station(int position, String name, int price, int[] income, int stage) {
		super(position, name, price, income, stage);
	}
}
