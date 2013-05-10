package objects.map.purchasable;

import objects.Player;

import java.io.Serializable;

/**
 * The structure of Facility is a circular list of all Facility objects.
 *
 * @author Eyenseo
 * @version 1
 */
public class Facility extends PurchasableCircularList implements Serializable {
	private static final long serialVersionUID = -8428553388916372195L;

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the income at the different stages.
	 *                 <ol start="0">
	 *                 <li>Water Work <b>or</b> Electric Company</li>
	 *                 <li>Water Work <b>and</b> Electric Company</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage.
	 */
	public Facility(String name, int price, int[] income, int mortgage) {
		super(name, price, income, mortgage);
	}

	@Override
	/**
	 * @param player The value determines the the player who has to pay the bill.
	 * @return The return value is the current income.
	 */
	public int getBill(Player player) {
		return INCOME[stage] * (player.getDices()[0] + player.getDices()[1]);
	}
}
