package objects.card;

import objects.Player;
import objects.map.notPurchasable.Parking;

import java.io.Serializable;

/**
 * PayFineTakeCard is special card (pay fine or draw a card).
 *
 * @version 1
 */
public class PayFineTakeCard extends Card implements Serializable {
	private static final long serialVersionUID = -6500337756850536820L;
	private final int       dm;
	private final String    option;
	private       CardStack community;
	private       Parking   parking;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 * @param dm   The value determines the amount to pay by the Player.
	 */
	public PayFineTakeCard(String name, String text, int dm) {
		super(name, text);
		this.dm = dm;
		option = "Wollen Sie eine Gemeinschaftskarte Karte aufnehmen? j/n";
	}

	/**
	 * @return The return value is the Options String.
	 */
	public String getOption() {
		return option;
	}

	/**
	 * @param parking The value determines parking.
	 */
	public void setParking(Parking parking) {
		this.parking = parking;
	}

	/**
	 * @param community The value determines the community Card Stack.
	 */
	public void setCommunity(CardStack community) {
		this.community = community;
	}

	/**
	 * The method will fire a CardEvent
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	@Override
	public void action(Player player) {
		fireCardEvent(player.getName());
		community.nextCard().action(player);
		//TODO wait for the player to return a choice  - whether to take a community card or to pay a fine.
	}
}
