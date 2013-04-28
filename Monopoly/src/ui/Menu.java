package ui;

import objects.Player;

//JAVADOC
public interface Menu {
	//JAVADOC
	public int nextTurn(Player player, int turnState);

	//JAVADOC
	public void inJail();
}
