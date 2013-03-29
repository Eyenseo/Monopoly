package objects.value;

/**
 * The Street class holds the information of the streets.        <br>
 * The structure of all Streets is a ring list of all Streets that belong together e.g. all Streets that are red.
 *
 * @author Eyenseo
 * @version 0.1
 */
class Street extends Purchasable {
	final         int[] color;
	private final int   house;
	private final int   hotel;

	/**
	 * @param name     The value determines the name of the Street.
	 * @param price    The value determines the price of the Street that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the Street in the different stages.
	 *                 <ol start="0">
	 *                 <li>Color not complete</li>
	 *                 <li>Color complete</li>
	 *                 <li>1 house</li>
	 *                 <li>2 houses</li>
	 *                 <li>...</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage of the Street.
	 * @param stage    The value determines the stage the income is at.
	 * @param owner    The value determines the owner of the Street.
	 * @param house    The value determines how much the owner has to pay for a house.
	 * @param hotel    The value determines how much the owner has to pay for a hotel.
	 * @param color    The values of the array determine the color of the Street. The Array has to be three long,
	 *                 each of the places represent one color {R,G,B}.
	 */
	public Street(String name, int price, int[] income, int mortgage, int stage, Player owner, int house, int hotel,
	              int[] color) {
		super(name, price, income, mortgage, stage, owner);
		this.house = house;
		this.hotel = hotel;
		this.color = color;
	}

	/**
	 * This method is for buying houses or hotels and setting the stage accordingly up.
	 *
	 * @param player The value determines who it is that wants to buy a house or hotel.
	 */
	public void nextStage(Player player) {
		// TODO : Implement - Check if the player is the owner
	}
}
