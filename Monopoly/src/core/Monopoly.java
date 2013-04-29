package core;

import core.data.MapArrayCreator;
import objects.Player;
import objects.card.CardStack;
import objects.exceptions.core.CardConnectionException;
import objects.exceptions.core.NoInstanceException;
import objects.exceptions.data.StorageReaderException;
import objects.map.Field;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.StreetCircularList;
import ui.Menu;
import ui.cui.ConsoleMenu;

import java.util.Vector;

//JAVADOC
public class Monopoly {
	private final int     STARTMONEY = 50000;
	private       boolean gameOver   = false;
	private Field go;
	private Field jail;
	private Menu  menu;
	private Vector<Player> playerVector = new Vector<Player>();

	//JAVADOC
	Monopoly(Menu menu) {
		try {
			MapArrayCreator mac = new MapArrayCreator();
			CardStack event = new CardStack("events.txt", "Event Karte");
			CardStack community = new CardStack("community.txt", "Gemeinschafts Karte");
			new Connector().connect(mac.getMap(), event, community);
			this.go = mac.getGo();
			this.jail = mac.getJail();
			this.menu = menu;
		} catch(StorageReaderException e) {
			//TODO catch the exceptions properly
			System.err.println(e.getMessageStack());
		} catch(NoInstanceException e) {
			System.err.println(e.getMessage());
		} catch(CardConnectionException e) {
			System.err.println(e.getMessage());
		}
	}

	//JAVADOC
	private void addPlayer(Player player) {
		playerVector.add(player);
		player.setField(go);
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
			switch(menu.nextTurn(player, turnState)) {
				case 0:
					doubles = player.move();
					if(doubles) {
						turnState = 3;
					} else {
						turnState = 1;
					}
					break;
				case 1:
					player.buy((PurchasableCircularList) player.getField());
					break;
				case 2:
					((StreetCircularList) player.getField()).nextStage();
					break;
				case 3:
					((PurchasableCircularList) player.getField()).setInMortgage(true);
					break;
				case 4:
					((PurchasableCircularList) player.getField()).setInMortgage(false);
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
					System.out.print("To be implemented ...");
					break;
				case 9:
				case 10:
					turnState = 2;
					break;
				case 11:
					menu.playerPropertiesDetails(player);
					break;
				case 12:
					gameOver = true;
			}
		}
		if(doublesTime == 3) {
			player.setField(jail);
			player.setInJail(true);
			menu.inJail();
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
		m.addPlayer(new Player("Julia", m.STARTMONEY));
		m.addPlayer(new Player("Markus", m.STARTMONEY));
		while(!m.isGameOver()) {
			m.nextRound();
		}
		System.out.print("Set stop - check the map in debug.");
	}
}