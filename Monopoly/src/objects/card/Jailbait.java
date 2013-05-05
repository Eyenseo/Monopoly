package objects.card;

import objects.Player;
//JAVADOC

/**
 * Jailbait is the jailbait card.
 *
 * @version 0.1
 */
public class Jailbait extends Card {
	private CardStack cardStack;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public Jailbait(String name, String text) {
		super(name, text);
	}

	/**
	 * @param cardStack The value determines the CardStack of this Card.
	 */
	public void setCardStack(CardStack cardStack) {
		this.cardStack = cardStack;
	}

	/**
	 * @param player The value determines the Player object which is to be freed from Jail.
	 */
	public void freePlayer(Player player) {
		cardStack.addCard(this, index);
		player.setInJail(false);
	}

	//JAVADOC
	@Override
	public void action(Player player) {
		player.addJailbait(this);
		cardStack.removeCard(index);
	}
}
