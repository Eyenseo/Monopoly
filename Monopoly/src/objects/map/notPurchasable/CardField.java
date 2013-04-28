package objects.map.notPurchasable;

import objects.Player;
import objects.card.CardStack;

//JAVADOC
public abstract class CardField extends NotPurchasable {
	CardStack cardStack;

	//JAVADOC
	protected CardField(String name) {
		super(name);
	}

	//JAVADOC
	public void setCardStack(CardStack cardStack) {
		this.cardStack = cardStack;
	}

	@Override
	public void action(Player player) {
		cardStack.nextCard().action(player);
	}
}
