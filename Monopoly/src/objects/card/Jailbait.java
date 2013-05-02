package objects.card;

import objects.Player;

/**
 * Jailbait is the jailbait card.
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class Jailbait extends Card {
	private CardStack cardStack;

	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public Jailbait(String name, String text) {
		super(name, text);
	}

	//JAVADOC
	public void setCardStack(CardStack cardStack) {
		this.cardStack = cardStack;
	}

	//JAVADOC
	public void freePlayer(Player player) {
		cardStack.addCard(this, index);
		player.setInJail(false);
	}

	@Override
	public void action(Player player) {
		player.addJailbait(this);
		cardStack.removeCard(index);
	}
}
