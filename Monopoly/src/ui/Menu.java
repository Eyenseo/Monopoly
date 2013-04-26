package ui;

import objects.Player;

//JAVADOC
public interface Menu {
	//JAVADOC
	public int nextTurn(Player player, int turnState);

	public void inJail();
}
