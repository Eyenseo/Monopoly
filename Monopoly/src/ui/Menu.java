package ui;

import objects.Player;
import objects.card.Card;

import java.util.Vector;

//JAVADOC
public interface Menu {
	//JAVADOC
	public boolean showCardText(Card card);

	//javaDoc
	public void showInJail();

	//JAVADOC
	public int mainMenu(Player player, Vector<Player> playerVector, int turnState);

	//JAVADOC
	public int playerAmount();

	//JAVADOC
	public String getName();
}
