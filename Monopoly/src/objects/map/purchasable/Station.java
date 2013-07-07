package objects.map.purchasable;

import objects.Player;
import objects.value.map.FieldData;
import objects.value.map.StationData;

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
	 *                 <li>1Station</li>
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
			if(next.getOwner() != null && next.getOwner().equals(getOwner())) {
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
				next.setStage(index);
			}
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		firePurchasableEvent();
	}

	/**
	 * The method will transfer twice of the money if doublePay is true the money the player has to pay to the owner
	 *
	 * @param player The value determines the Player who caused the method call
	 * @param doublePay The value determines if the player has to pay double
	 */
	public void action(Player player, boolean doublePay) {
		if(getOwner() != null && !player.equals(getOwner()) && doublePay && !isInMortgage()) {
			player.pay(getBill(player) * 2);
			getOwner().addMoney(getBill(player) * 2);
		} else {
			action(player);
		}
	}

	/**
	 * @return the return value is a new FieldData object of StationData with the current attributes of the Station
	 */
	@Override public FieldData toFieldData() {
		if(owner != null) {
			return new StationData(FIELDNUMBER, NAME, INCOME, MORTGAGE, PRICE, inMortgage, stage,
			                       owner.toPlayerData(false));
		} else {
			return new StationData(FIELDNUMBER, NAME, INCOME, MORTGAGE, PRICE, inMortgage, stage, null);
		}
	}

	/**
	 * The method adds this object to the new owner and removes it from the old one.
	 * The method will fire PurchasableEvent
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
		Station next = (Station) nextGroupElement;
		do {
			next.integrityCheck();
			next = (Station) next.getNextGroupElement();
		} while(!next.equals(this));
	}

	/**
	 * The method will check for each group member the owner and set the stage accordingly
	 */
	private void integrityCheck() {
		PurchasableCircularList next = nextGroupElement;
		int stations = 0;
		int owned = 0;
		do {
			stations++;
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		next = this;
		do {
			if(next.getOwner() != null && next.getOwner().equals(owner)) {
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
