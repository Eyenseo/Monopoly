package objects.map.notPurchasable;

import objects.Player;
import objects.card.CardStack;

/**
 * The CardField class is the super class of all FieldCircularList subclasses that do something with a CardStack.
 *
 * @author Eyenseo
 * @version 1
 * @see CardStack
 */
public abstract class CardField extends NotPurchasable {
	CardStack cardStack;

	/**
	 * @param name The value determines the name of the FieldCircularList.
	 */
	protected CardField(String name) {
		super(name);
	}

	/**
	 * @param cardStack The value determines the CardStack of the FieldCircularList.
	 */
	public void setCardStack(CardStack cardStack) {
		this.cardStack = cardStack;
	}

	@Override
	//JAVADOC
	public void action(Player player) {
		cardStack.nextCard().action(player);
	}
}
