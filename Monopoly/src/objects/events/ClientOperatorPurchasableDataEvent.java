package objects.events;

import objects.value.connection.data.map.PurchasableData;

import java.util.EventObject;

//JAVADOC
public class ClientOperatorPurchasableDataEvent extends EventObject {
	PurchasableData purchasableData;

	//JAVADOC
	public ClientOperatorPurchasableDataEvent(Object source, PurchasableData purchasableData) {
		super(source);
		this.purchasableData = purchasableData;
	}

	//JAVADOC
	public PurchasableData getPurchasableData() {
		return purchasableData;
	}
}
