package objects.map;

import objects.Player;

//JAVADOC
public class Go extends NotPurchasable {
	private final int TURNMONEY;
	// TODO:   Everything

	public Go(int turnmoney) {
		super("Los");
		this.TURNMONEY = turnmoney;
	}

	//JAVADOC
	@Override
	public void action(Player player) {
		player.addMoney(TURNMONEY);
	}
}
