package objects.card;
//JAVADOC

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
	private int       dm;
	private String    option;
	private CardStack community;
	private Parking   parking;

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
	 * @return The return value is the community CardStack.
	 */
	public CardStack getCommunity() {
		return community;
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

	//JAVADOC
	@Override
	public void action(Player player) {
		if(menu.showCardText(this)) {
			community.nextCard().action(player);
		} else {
			player.pay(dm);
			parking.addMoney(dm);
		}
	}
}
