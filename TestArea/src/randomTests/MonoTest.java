package randomTests;

import core.Connector;
import core.data.MapArrayCreator;
import objects.Player;
import objects.card.Card;
import objects.card.CardStack;
import objects.card.Jailbait;
import objects.exceptions.core.CardConnectionException;
import objects.exceptions.core.NoInstanceException;
import objects.exceptions.data.StorageReaderException;
import objects.map.FieldCircularList;
import ui.cui.ConsoleMenu;

import java.util.ArrayList;
import java.util.Vector;

public class MonoTest {
	private final int     STARTMONEY = 50000;
	private       boolean gameOver   = false;
	private FieldCircularList go;
	private FieldCircularList jail;
	private ConsoleMenu       menu;
	private Vector<Player> playerVector = new Vector<Player>();
	private CardStack c;

	MonoTest(ConsoleMenu menu) {
		try {
			MapArrayCreator mac = new MapArrayCreator();
			CardStack event = new CardStack("events.txt", "Event Karte");
			c = event;
			CardStack community = new CardStack("community.txt", "Gemeinschafts Karte");
			new Connector().connect(mac.getMap(), event, community, playerVector, menu);
			this.go = mac.getGo();
			this.jail = mac.getJail();
			this.menu = menu;
		} catch(StorageReaderException e) {
			System.err.println(e.getMessageStack());
		} catch(NoInstanceException e) {
			System.err.println(e.getMessage());
		} catch(CardConnectionException e) {
			System.err.println(e.getMessage());
		}
	}

	private void addPlayer(Player player) {
		playerVector.add(player);
		player.setPosition(go);
	}

	private void nextRound() {
		for(Player player : playerVector) {
			nextTurn(player);
			if(gameOver) {
				break;
			}
		}
	}

	private void nextTurn(Player player) {
		nextTurn(player, 0);
	}

	private void nextTurn(Player player, int doublesTime) {
		int turnState = 0; // [0]Start of turn [1]After moving [2]End of Turn [3]Doubles turn [4] gameOver
		while(turnState != 2 && !gameOver) {
			turnState = menu.mainMenu(player, playerVector, turnState);
			gameOver = turnState == 4;
		}
		if(doublesTime == 3) {
			player.setPosition(jail);
			player.setInJail(true);
			menu.showInJail();
		} else if(turnState == 3) {
			nextTurn(player, doublesTime + 1);
		}
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public static void main(String[] args) {
		ConsoleMenu menu = new ConsoleMenu();
		MonoTest m = new MonoTest(menu);
		//		for(int i = menu.playerAmount(); i > 0; i--) {
		//			m.addPlayer(menu.newHuman());
		//		}
		Player julia = new Player("Julia", m.STARTMONEY);
		m.addPlayer(julia);
		m.addPlayer(new Player("Markus", m.STARTMONEY));
		julia.setInJail(true);
		julia.setPosition(m.jail);
		ArrayList<Card> v = m.c.getStack();
		for(int i = 0; i < v.size(); i++) {
			if(v.get(i) instanceof Jailbait) {
				v.get(i).action(julia);
			}
		}
		while(!m.isGameOver()) {
			m.nextRound();
		}
		System.out.print("Set stop - check the map in debug.");
	}
}