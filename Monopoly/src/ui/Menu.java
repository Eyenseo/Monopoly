package ui;

import objects.Player;
import objects.map.purchasable.PurchasableCircularList;

//JAVADOC
public interface Menu {
	//JAVADOC
	public int nextTurn(Player player, int turnState);

	//JAVADOC
	public void inJail();

	//JAVADOC
	public void playerPropertiesDetails(Player player);

	//JAVADOC
	public void groupDetails(PurchasableCircularList property);
}
