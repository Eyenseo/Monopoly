package objects.card;
//JAVADOC

import objects.Player;

import java.io.Serializable;
import java.util.Vector;

/**
 * SpecialPayment is a special transaction card (player based transactions).
 *
 * @version 1
 */
public class SpecialPayment extends Card implements Serializable {
	private static final long serialVersionUID = 4614526083952983870L;
	private int dm;
	Vector<Player> playerVector;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public SpecialPayment(String name, String text, int dm) {
		super(name, text);
		this.dm = dm;
	}

	//JavaDoc
	@Override
	public void action(Player player) {
		menu.showCardText(this);
		for(Player p : playerVector) {
			player.pay(dm);
			p.addMoney(dm);
		}
	}

	public void setPlayerVector(Vector<Player> playerVector) {
		this.playerVector = playerVector;
	}
}
