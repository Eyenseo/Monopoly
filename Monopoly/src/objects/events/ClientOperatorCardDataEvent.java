package objects.events;

import objects.value.CardData;

import java.util.EventObject;

//JAVADOC
public class ClientOperatorCardDataEvent extends EventObject {
	CardData cardData;

	public ClientOperatorCardDataEvent(Object source, CardData cardData) {
		super(source);
		this.cardData = cardData;
	}

	public CardData getCardData() {
		return cardData;
	}
}
