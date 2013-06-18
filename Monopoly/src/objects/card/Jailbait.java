package objects.card;

import objects.Player;

import java.io.Serializable;

/**
 * Jailbait is the jailbait card.
 *
 * @version 0.1
 */
public class Jailbait extends Card implements Serializable {
	private static final long serialVersionUID = 1422468157314847166L;
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

	/**
	 * --- TODO
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	//TODO Use th GUI / use a event
	@Override
	public void action(Player player) {
		fireCardEvent(player.getName());
		player.addJailbait(this);
		cardStack.removeCard(index);
	}
}
