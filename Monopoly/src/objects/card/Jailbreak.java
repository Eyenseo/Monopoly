package objects.card;

import objects.Player;

import java.io.Serializable;

/**
 * Jailbreak is the jailbreak card.
 *
 * @version 0.1
 */
public class Jailbreak extends Card implements Serializable {
	private static final long serialVersionUID = 1422468157314847166L;
	private CardStack cardStack;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public Jailbreak(String name, String text) {
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
		player.setInJail(false);
	}

	public void putBack() {
		cardStack.addCard(this, index);
	}

	/**
	 * The method will fire a MessageEvent
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		fireMessageEvent(player.getPlayerId());
		player.addJailbreak(this);
		cardStack.removeCard(index);
	}
}
