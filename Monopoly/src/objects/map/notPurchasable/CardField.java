package objects.map.notPurchasable;

import objects.Player;
import objects.card.CardStack;

import java.io.Serializable;

/**
 * The CardField class is the super class of all FieldCircularList subclasses that do something with a CardStack.
 *
 * @see CardStack
 */
public abstract class CardField extends NotPurchasable implements Serializable {
	private static final long serialVersionUID = -5945920534429656837L;
	private CardStack cardStack;

	/**
	 * @param name The value determines the name of the FieldCircularList.
	 */
	CardField(String name) {
		super(name);
	}

	/**
	 * @param cardStack The value determines the CardStack of the FieldCircularList.
	 */
	public void setCardStack(CardStack cardStack) {
		this.cardStack = cardStack;
	}

	/**
	 * The method will get the next card an run its <code>action</code> method
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override public void action(Player player) {
		cardStack.nextCard().action(player);
	}
}
