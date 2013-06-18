package objects.map.purchasable;

import objects.Player;
import objects.events.PurchasableEvent;
import objects.listeners.PurchasableEventListener;
import objects.map.FieldCircularList;
import objects.value.map.FieldData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The structure of PurchasableCircularList and the subclasses is a circular list of all objects that belong together
 * e.g. all Stations in one list, or all Streets that are red.
 */
public abstract class PurchasableCircularList extends FieldCircularList implements Serializable {
	private static final long serialVersionUID = 5554380896193644875L;
	final int[] INCOME;
	final int   MORTGAGE;   //Hypothek
	final int   PRICE;
	PurchasableCircularList nextGroupElement;
	boolean                 inMortgage;
	int                     stage;
	Player                  owner;
	private final ArrayList<PurchasableEventListener> listener;

	/**
	 * @param name     The value determines the name of the object.
	 * @param price    The value determines the price that the player has to pay to buy it.
	 * @param income   The values of the array determine the the income of the street in the different stages.
	 * @param mortgage The value determines the amount of the mortgage.
	 */
	PurchasableCircularList(String name, int price, int[] income, int mortgage) {
		super(name);
		PRICE = price;
		INCOME = income;
		MORTGAGE = mortgage;
		owner = null;
		stage = 0;
		inMortgage = false;
		nextGroupElement = this;
		listener = new ArrayList<PurchasableEventListener>();
	}

	/**
	 * @return The return value is the amount that has to be payed to become the owner.
	 */
	public int getPrice() {
		return PRICE;
	}

	/**
	 * @return The return value is the mortgage value.
	 */
	public int getMortgage() {
		return MORTGAGE;
	}

	/**
	 * @return The return value is the stage.
	 */
	public int getStage() {
		return stage;
	}

	/**
	 * @return The return value is the last index of the income Array.
	 */
	public int getMaxStage() {
		return INCOME.length - 1;
	}

	/**
	 * @return The return value is the income Array.
	 */
	public int[] getIncome() {
		return INCOME;
	}

	/**
	 * @return The return value is true if the PurchasableCircularList is in mortgage.
	 */
	public boolean isInMortgage() {
		return inMortgage;
	}

	/**
	 * The method removes or adds the mortgage amount to the owner.
	 * The method will fire PurchasableEvent
	 *
	 * @param inMortgage The value determines the mortgage stage.
	 */
	public void setInMortgage(boolean inMortgage) {
		this.inMortgage = inMortgage;
		firePurchasableEvent();
		if(inMortgage) {
			owner.addMoney(MORTGAGE);
		} else {
			owner.pay(MORTGAGE);
		}
	}

	/**
	 * The method will fire a PurchasableEvent
	 *
	 * @param stage the value determines the stage to be set
	 */
	void setStage(int stage) {
		this.stage = stage;
		firePurchasableEvent();
	}

	/**
	 * @param player The value determines the the player who has to pay the bill (can be null).
	 * @return The return value is the current income.
	 */
	public int getBill(Player player) {
		return INCOME[stage];
	}

	/**
	 * @return The return value is an ArrayList of the group members of the PurchasableCircularList object.
	 */
	public ArrayList<PurchasableCircularList> getGroupMembers() {
		ArrayList<PurchasableCircularList> members = new ArrayList<PurchasableCircularList>();
		PurchasableCircularList next = this;
		do {
			members.add(next);
			next = next.getNextGroupElement();
		} while(!next.equals(this));
		return members;
	}

	@Override
	public String toString() {
		return getName() + ((getOwner() != null) ? " (" + getOwner() + ")" : "");
	}

	/**
	 * @return The return value is the owner.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * The method adds this object to the new owner and removes it from the old one.
	 * The method will fire PurchasableEvent
	 *
	 * @param owner The value determines the owner.
	 */
	public void setOwner(Player owner) {
		if(this.owner != null) {
			this.owner.removeProperty(this);
		}
		this.owner = owner;
		firePurchasableEvent();
		owner.addProperty(this);
		sameOwnerCheck();
	}

	/**
	 * This method checks if all group members are owned by the same owner. If they are the stage is increased to 1.
	 */
	void sameOwnerCheck() {
		boolean sameOwner = true;
		PurchasableCircularList next = nextGroupElement;
		while(sameOwner && !next.equals(this)) {
			if(next.getOwner() == null || !next.getOwner().equals(owner)) {
				sameOwner = false;
			}
			next = next.getNextGroupElement();
		}
		next = this;
		do {
			next.setStage(sameOwner ? 1 : 0);
			next = next.getNextGroupElement();
		} while(sameOwner && !next.equals(this));
	}

	/**
	 * @return The return value is the next group member.
	 */
	PurchasableCircularList getNextGroupElement() {
		return nextGroupElement;
	}

	/**
	 * @param nextGroupElement The value determines the next group member.
	 */
	public void setNextGroupElement(PurchasableCircularList nextGroupElement) {
		nextGroupElement.nextGroupElement = this.nextGroupElement;
		this.nextGroupElement = nextGroupElement;
	}

	/**
	 * The method will transfer the money the player has to pay to the owner
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		if(!player.equals(owner) && owner != null && !inMortgage) {
			player.pay(getBill(player));
			owner.addMoney(getBill(player));
		}
	}

	@Override public abstract FieldData toFieldData();

	/**
	 * @param listener the value determines the listener to be added
	 */
	public void addPurchasableEventListener(PurchasableEventListener listener) {
		this.listener.add(listener);
	}

	/**
	 * @param listener the value determines the listener to be removed
	 */
	public void removePurchasableEventListener(PurchasableEventListener listener) {
		this.listener.remove(listener);
	}

	/**
	 * The method will fire PurchasableEvent.
	 */
	void firePurchasableEvent() {
		PurchasableEvent event = new PurchasableEvent(this);
		for(Object l : listener) {
			((PurchasableEventListener) l).actionPerformed(event);
		}
	}
}
