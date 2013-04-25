package ui;

import objects.Player;
import objects.map.Field;

//JAVADOC
public interface Menu {
	//JAVADOC
	public int nextTurn(Player player, Field field, int turnState);

	public void inJail();
}
