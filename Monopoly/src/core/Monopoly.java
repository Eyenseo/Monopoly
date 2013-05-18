package core;

import objects.Player;
import objects.exceptions.MessageStackException;
import objects.map.FieldCircularList;
import ui.Menu;
import ui.cui.ConsoleMenu;

import java.util.Vector;

/**
 * Monopoly is the main Class. It initialises the game and hold pieces of the mechanic.
 *
 * @author Eyenseo
 * @version 1
 */
public class Monopoly {
	private final int     STARTMONEY = 50000;
	private       boolean gameOver   = false;
	private FieldCircularList go;
	private FieldCircularList jail;
	private Menu              menu;
	private Vector<Player>    playerVector;

	//JAVADOC
	private Monopoly(Loader loader) {
		go = loader.getGo();
		jail = loader.getJail();
		playerVector = loader.getPlayerVector();
		menu = loader.getMenu();
	}

	/**
	 * @param player The value determines the Player object to be added to the game.
	 */
	private void addPlayer(Player player) {
		playerVector.add(player);
		player.setPosition(go);
	}

	/**
	 * The method executes the next round of the game.
	 */
	private void nextRound() {
		for(Player player : playerVector) {
			nextTurn(player);
			if(gameOver) {
				break;
			}
		}
	}

	/**
	 * The method executes the next turn of the game for the player.
	 *
	 * @param player The value determines the Player object of the turn.
	 */
	private void nextTurn(Player player) {
		nextTurn(player, 0);
	}

	/**
	 * The method executes the next turn of the game for the player.
	 *
	 * @param player      The value determines the Player object of the turn.
	 * @param doublesTime The value determines the times doubles where thrown by the player.
	 */
	private void nextTurn(Player player, int doublesTime) {
		int turnState = 0; // [0]Start of turn [1]After moving [2]End of Turn [3]Doubles turn [4] gameOver
		while(turnState != 2 && !gameOver) {
			turnState = menu.mainMenu(player, playerVector, turnState);
			if(doublesTime == 3) {
				player.setPosition(jail);
				player.setInJail(true);
				menu.showInJail();
				turnState = 2;
			} else if(turnState == 3) {
				doublesTime++;
			}
			gameOver = turnState == 4;
		}
	}

	/**
	 * @return The return value is true if the game is over and false if it's not.
	 */
	private boolean isGameOver() {
		return gameOver;
	}

	public static void main(String[] args) {
		try {
			Menu menu = new ConsoleMenu();
			Monopoly m = new Monopoly(new Loader(menu));
			//		for(int i = menu.playerAmount(); i > 0; i--) {
			//			m.addPlayer(new Player(menu.getName(), m.STARTMONEY));
			//		}
			m.addPlayer(new Player("Julia", m.STARTMONEY));
			m.addPlayer(new Player("Markus", m.STARTMONEY));
			while(!m.isGameOver()) {
				m.nextRound();
			}
		} catch(MessageStackException e) {
			System.err.println(e.getMessageStack());
		}
	}
}