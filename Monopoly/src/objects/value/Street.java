package objects.value;

/**
 * The Street class holds the information of the street.
 *
 * @author Eyenseo
 * @version 0.1
 */
class Street extends Purchasable {
	final int[] color;

	/**
	 * @param position The value determines the numeric position of the field.
	 * @param name     The value determines the name of the field.
	 * @param price    The value determines the price of the field that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the street in the different stages.
	 *                 <ol start="0">
	 *                 <li>Color not complete</li>
	 *                 <li>Color complete</li>
	 *                 <li>1 house</li>
	 *                 <li>2 houses</li>
	 *                 <li>...</li>
	 *                 </ol>
	 * @param stage    The value determines the stage the income is at.
	 * @param color    The values of the array determine the color of the field.The Array has to be three long,
	 *                 each of the places represent one color {R,G,B}.
	 */
	public Street(int position, String name, int price, int[] income, int stage, int[] color) {
		super(position, name, price, income, stage);
		this.color = color;
	}
	// TODO:   getter and setter
}
