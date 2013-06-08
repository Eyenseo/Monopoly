package objects.map.purchasable;

import objects.Player;

import java.io.Serializable;

/**
 * The structure of Station is a circular list of all Station objects.
 *
 * @author Eyenseo
 * @version 0.1
 */
public class Station extends PurchasableCircularList implements Serializable {
	private static final long serialVersionUID = 1200011350581598724L;

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the income at the different stages.
	 *                 <ol start="0">
	 *                 <li>1 Station</li>
	 *                 <li>2 Stations</li>
	 *                 <li>3 Stations</li>
	 *                 <li>4 Stations</li>
	 *                 </ol>
	 * @param mortgage The value determines the amount of the mortgage.
	 */
	public Station(String name, int price, int[] income, int mortgage) {
		super(name, price, income, mortgage);
	}

	/**
	 * This method checks if all group members are owned by the same owner. If they are the stage is increased.
	 */
	@Override void sameOwnerCheck() {
		int index = 0;
		PurchasableCircularList next = nextGroupElement;
		while(!next.equals(this)) {
			if(next.getOwner() != null && next.getOwner().equals(owner)) {
				index++;
				if(index == INCOME.length - 1) {
					break;
				}
			}
			next = next.getNextGroupElement();
		}
		next = this;
		do {
			if(next.getOwner() != null && next.getOwner().equals(getOwner())) {
				next.stage = index;
			}
			next = next.getNextGroupElement();
		} while(!next.equals(this));
	}

	//JAVADOC
	public void action(Player player, boolean doublePay) {
		if(doublePay && owner != null && !inMortgage) {
			player.pay(getBill(player) * 2);
			owner.addMoney(getBill(player) * 2);
		} else {
			action(player);
		}
	}

	@Override
	public void setOwner(Player player) {
		super.setOwner(player);
		globalStageUpdate();
	}

	void globalStageUpdate() {
		Station next = (Station) nextGroupElement;
		do {
			next.integrityCheck();
			next = (Station) next.getNextGroupElement();
		} while(!next.equals(this));
	}

	public void integrityCheck() {
		PurchasableCircularList next = nextGroupElement;
		int stations = 0;
		int owned = 0;
		do {
			stations++;
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		next = this;
		do {
			if(next.getOwner() != null && next.getOwner().equals(this.owner)) {
				owned++;
			}
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		if(owned >= (stations * 0.75)) {
			stage = 3;
		} else if(owned >= (stations * 0.5)) {
			stage = 2;
		} else if(owned >= (stations * 0.25)) {
			stage = 1;
		} else {
			stage = 0;
		}
	}
}
