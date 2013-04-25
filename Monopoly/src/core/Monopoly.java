package core;

import core.data.MapCreator;
import objects.Human;
import objects.Player;
import objects.card.CardStack;
import objects.exceptions.StorageReaderException;
import objects.map.Map;
import objects.map.PurchasableCircularList;
import objects.map.StreetCircularList;
import ui.Menu;
import ui.cui.ConsoleMenu;

import java.util.Vector;

//JAVADOC
public class Monopoly {
	private final int     STARTMONEY = 10000;
	private       boolean gameOver   = false;
	private Map  map;
	private Menu menu;
	private Vector<Player> playerVector = new Vector<Player>();

	//JAVADOC
	Monopoly(Menu menu) {
		try {
			CardStack event = new CardStack("events.txt", "Event Karte");
			CardStack community = new CardStack("community.txt", "Gemeinschafts Karte");
			this.map = new Map(new MapCreator().createMap());
			this.menu = menu;
		} catch(StorageReaderException e) {
			System.err.println(e.getMessageStack());
		}
	}

	//JAVADOC
	private void addPlayer(Player player) {
		playerVector.add(player);
		map.addPlayer(player);
	}

	//JAVADOC
	private void nextRound() {
		for(Player player : playerVector) {
			nextTurn(player);
			if(gameOver) {
				break;
			}
		}
	}

	//JAVADOC
	private void nextTurn(Player player) {
		nextTurn(player, 0);
	}

	//JAVADOC
	private void nextTurn(Player player, int doublesTime) {
		boolean doubles = false;
		int turnState = 0; // [0]Start of turn [1]After moving [2]End of Turn [3]Doubles turn
		while(turnState != 2 && !gameOver) {
			switch(menu.nextTurn(player, map.getField(player), turnState)) {
				case 0:
					doubles = map.movePlayer(player);
					map.getField(player).action(player);
					if(doubles) {
						turnState = 3;
					} else {
						turnState = 1;
					}
					break;
				case 1:
					player.buy((PurchasableCircularList) map.getField(player));
					break;
				case 2:
					((StreetCircularList) map.getField(player)).nextStage();
					break;
				case 3:
					System.out.print("To be implemented ...");
					break;
				case 4:
					System.out.print("To be implemented ...");
					break;
				case 5:
					System.out.print("To be implemented ...");
					break;
				case 6:
					System.out.print("To be implemented ...");
					break;
				case 7:
					System.out.print("To be implemented ...");
					break;
				case 8:
				case 9:
					turnState = 2;
					break;
				case 10:
					gameOver = true;
			}
		}
		if(doublesTime == 3) {
			//TODO implement
			System.err.println("GoToJail\nTo implement ...");
		} else if(doubles) {
			nextTurn(player, doublesTime + 1);
		}
	}

	//JAVADOC
	public boolean isGameOver() {
		return gameOver;
	}

	public static void main(String[] args) {
		Menu menu = new ConsoleMenu();
		Monopoly m = new Monopoly(menu);
		//		for(int i = menu.playerAmount(); i > 0; i--) {
		//			m.addPlayer(menu.newHuman());
		//		}
		m.addPlayer(new Human("Julia", m.STARTMONEY));
		m.addPlayer(new Human("Markus", m.STARTMONEY));
		while(!m.isGameOver()) {
			m.nextRound();
		}
		System.out.print("Set stop - check the map in debug.");
	}
}