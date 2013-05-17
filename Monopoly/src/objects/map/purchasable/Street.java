package objects.map.purchasable;

import java.io.Serializable;

/**
 * The structure of Street is a circular list of all Street objects that belong together e.g.
 * all Streets that are red.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class Street extends PurchasableCircularList implements Serializable {
	private static final long serialVersionUID = 8118177447664813032L;
	private final int[] COLOR;
	private final int   UPGRADE;

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the income at the different stages.
	 *                 <ol start="0">
	 *                 <li>Color not complete</li>
	 *                 <li>Color complete</li>
	 *                 <li>1 house</li>
	 *                 <li>2 houses</li>
	 *                 <li>...</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage.
	 * @param upgrade  The value determines how much the owner has to pay for a house or hotel.
	 * @param color    The values of the array determine the color of the Street object. The Array has to
	 *                 be three long, each of the places represent one color {R,G,B}.
	 */
	public Street(String name, int price, int[] income, int mortgage, int upgrade, int[] color) {
		super(name, price, income, mortgage);
		UPGRADE = upgrade;
		COLOR = color;
	}

	/**
	 * This method is for buying houses or hotels and setting the stage accordingly up.
	 */
	public void nextStage() {
		stage++;
		owner.pay(UPGRADE);
	}

	/**
	 * @param street The value determines the street to check against.
	 * @return The return value is true if the two Streets have the same color.
	 */
	public boolean isSameColor(Street street) {
		String[] compare = {"", ""};
		for(int i = 0; i < COLOR.length; i++) {
			compare[0] += COLOR[i];
			compare[1] += street.COLOR[i];
		}
		return compare[0].equals(compare[1]);
	}
}
