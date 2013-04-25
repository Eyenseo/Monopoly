package objects.map;

import objects.Player;

/**
 * The structure of StreetCircularList is a circular list of all StreetCircularList objects that belong together e.g.
 * all Streets that are red.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class StreetCircularList extends PurchasableCircularList {
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
	 * @param stage    The value determines the stage the income is at.
	 * @param owner    The value determines the owner.
	 * @param upgrade  The value determines how much the owner has to pay for a house or hotel.
	 * @param color    The values of the array determine the color of the StreetCircularList object. The Array has to
	 *                 be three long, each of the places represent one color {R,G,B}.
	 */
	public StreetCircularList(String name, int price, int[] income, int mortgage, int upgrade, int[] color) {
		super(name, price, income, mortgage);
		this.UPGRADE = upgrade;
		this.COLOR = color;
	}

	/**
	 * This method is for buying houses or hotels and setting the stage accordingly up.
	 */
	public void nextStage() {
		this.stage++;
		this.owner.pay(UPGRADE);
	}

	/**
	 * @param street The value determines the street to check against.
	 * @return The return value is true if the two Streets have the same color.
	 */
	public boolean isSameColor(StreetCircularList street) {
		String[] compare = {"", ""};
		for(int i = 0; i < this.COLOR.length; i++) {
			compare[0] += this.COLOR[i];
			compare[1] += street.COLOR[i];
		}
		return compare[0].equals(compare[1]);
	}

	//JAVADOC
	//TODO improve
	@Override
	public void action(Player player) {
		if(owner != null) {
			player.pay(getBill());
		}
	}

	public int getUpgrade() {
		return UPGRADE;
	}
}
