package objects.map.purchasable;

import objects.Player;
import objects.value.map.FacilityData;
import objects.value.map.FieldData;

import java.io.Serializable;

/**
 * The structure of Facility is a circular list of all Facility objects.
 */
public class Facility extends PurchasableCircularList implements Serializable {
	private static final long serialVersionUID = -8428553388916372195L;

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the income at the different stages. <ol start="0"> <li>Water Work
	 *                 <b>or</b> Electric Company</li> <li>Water Work <b>and</b> Electric Company</li> </ol>
	 * @param mortgage The value determines the amount of the mortgage.
	 */
	public Facility(String name, int price, int[] income, int mortgage) {
		super(name, price, income, mortgage);
	}

	/**
	 * @param player The value determines the the player who has to pay the bill.
	 * @return The return value is the current income.
	 */
	@Override public int getBill(Player player) {
		return INCOME[getStage()] * (player.getDices()[0] + player.getDices()[1]);
	}

	/**
	 * @return the return value is a new FieldData object of FacilityData with the current attributes of the
	 *         PurchasableCircularList
	 */
	@Override public FieldData toFieldData() {
		if(owner != null) {
			return new FacilityData(FIELDNUMBER, NAME, INCOME, MORTGAGE, PRICE, inMortgage, stage,
			                        owner.toPlayerData(false));
		} else {
			return new FacilityData(FIELDNUMBER, NAME, INCOME, MORTGAGE, PRICE, inMortgage, stage, null);
		}
	}

	/**
	 * The method adds this object to the new owner and removes it from the old one. The method will fire PurchasableEvent
	 * The method will check if the stage is right on all its group members
	 *
	 * @param owner The value determines the owner.
	 */
	@Override public void setOwner(Player owner) {
		super.setOwner(owner);
		globalStageUpdate();
	}

	/**
	 * The method will check if the stage is right on all its group members
	 */
	private void globalStageUpdate() {
		Facility next = (Facility) nextGroupElement;
		do {
			next.integrityCheck();
			next = (Facility) next.getNextGroupElement();
		} while(!next.equals(this));
	}

	/**
	 * The method will check for each group member the owner and set the stage accordingly
	 */
	private void integrityCheck() {
		PurchasableCircularList next = nextGroupElement;
		int facilities = 0;
		int owned = 0;
		do {
			facilities++;
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		next = this;
		do {
			if(next.getOwner() != null && next.getOwner().equals(owner)) {
				owned++;
			}
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		if(owned > (facilities / 2)) {
			stage = 1;
		} else {
			stage = 0;
		}
	}
}
