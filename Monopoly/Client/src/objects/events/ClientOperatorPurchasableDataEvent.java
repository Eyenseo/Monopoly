package objects.events;

import objects.value.map.PurchasableData;

import java.util.EventObject;

/**
 * The ClientOperatorPurchasableDataEvent is thrown at the client if there is new PurchasableData
 */
public class ClientOperatorPurchasableDataEvent extends EventObject {
	private final PurchasableData purchasableData;

	/**
	 * @param source          the value determines the source of the event
	 * @param purchasableData the value determines the PurchasableData of the event
	 */
	public ClientOperatorPurchasableDataEvent(Object source, PurchasableData purchasableData) {
		super(source);
		this.purchasableData = purchasableData;
	}

	/**
	 * @return the return value is the PurchasableData of the event
	 */
	public PurchasableData getPurchasableData() {
		return purchasableData;
	}
}
