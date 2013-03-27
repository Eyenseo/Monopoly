package objects.value;

/**
 * The Station class holds the information of the stations.<br>
 * The structure of all Stations is a ring list of all Stations that belong together.
 *
 * @author Eyenseo
 * @version 0.1
 */
class Station extends Purchasable {

	/**
	 * @param name     The value determines the name of the Station.
	 * @param price    The value determines the price of the Station that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the Station in the different stages.
	 *                 <ol start="0">
	 *                 <li>1 Station</li>
	 *                 -   *                 <li>2 Stations</li>
	 *                 -   *                 <li>3 Stations</li>
	 *                 -   *                 <li>4 Stations</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage of the Station.
	 * @param stage    The value determines the stage the income is at.
	 * @param owner    The value determines the owner of the Station.
	 */
	public Station(String name, int price, int[] income, int mortgage, int stage, Player owner) {
		super(name, price, income, mortgage, stage, owner);
	}

	/**
	 * @return The return value is the amount that has to be payed if someone else then the owner is on the Station.
	 */
	@Override
	int getBill(Player player) {
		return this.income[this.stage];
	}
}
