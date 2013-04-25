package ui.cui;

import objects.Player;
import objects.map.Field;
import objects.map.PurchasableCircularList;
import objects.map.StreetCircularList;
import ui.Menu;

//JAVADOC
public class ConsoleMenu implements Menu {
	Input in = new Input();

	//JAVADOC
	private String getName() {
		String name;
		println("Wie heißen Sie?");
		name = in.readString();
		println("Wollen Sie \"" + name + "\" heißen? j/n");
		if(in.userChar(new char[]{'j', 'n', '\0'}, "Bitte geben Sie 'j' oder 'n' ein.") == 'n') {
			return getName();
		}
		return name;
	}

	//JAVADOC
	public int playerAmount() {
		println("Wie viele Spieler werden spielnen?");
		return in.readInt();
	}

	//JAVADOC
	//TODO probably improve
	public void inJail() {
		System.out.print("Sie sind im Gefängnis!");
	}

	//JAVADOC
	@Override
	public int nextTurn(Player player, Field field, int turnState) {
		int userInt;
		int[] options = getPlayerOptions(player, field, turnState, 11);
		String menuOptions = getNextTurnOptions(options);
		String playerStatus = getPlayerStatus(player, field, turnState == 0);
		println(playerStatus + "\n\nSie haben nun folgende Optionen:\n" + menuOptions);
		userInt = in.userInt(options, "Bitte wählen Sie eine der Optionen aus:\n" + menuOptions);
		for(int i = 0; i < options.length; i++) {
			if(userInt == options[i]) {
				userInt = i;
				break;
			}
		}
		return userInt;
	}

	//JAVADOC
	private String getPlayerStatus(Player player, Field field, boolean beginOfTurn) {
		String playerStatus = "";
		if(beginOfTurn) {
			playerStatus += player.getName() + " ist am Zug!";
		} else {
			playerStatus += "Würfel 1: " + player.getDices()[0] + "\tWürfel 2: " + player.getDices()[1];
		}
		if(field instanceof PurchasableCircularList) {
			if(((PurchasableCircularList) field).getOwner() != null &&
			   ((PurchasableCircularList) field).getOwner() != player) {
				playerStatus += "\nOrt:\t" + field.getName() + " (" +
				                ((PurchasableCircularList) field).getOwner().getName() + ")" + " Miete:\t" +
				                ((PurchasableCircularList) field).getBill();
			} else if(((PurchasableCircularList) field).getOwner() != null) {
				playerStatus += "\nOrt:\t" + field.getName() + " (Sie)";
			} else {
				playerStatus += "\nOrt:\t" + field.getName() + " Preis:\t" +
				                (((PurchasableCircularList) field).getPrice());
			}
		} else {
			playerStatus += "\nOrt:\t" + field.getName();
		}
		playerStatus += "\nGeld:\t" + player.getMoney();
		return playerStatus;
	}

	//JAVADOC
	private String getNextTurnOptions(int[] options) {
		String menuOptions = "";
		for(int i = 0; i < options.length; i++) {
			if(options[i] != 0) {
				switch(i) {
					case 0:
						menuOptions += "[" + options[i] + "] Würfeln.\n";
						break;
					case 1:
						menuOptions += "[" + options[i] + "] Anwesen kaufen.\n";
						break;
					case 2:
						menuOptions += "[" + options[i] + "] Häuser kaufen.\n";
						break;
					case 3:
						menuOptions += "[" + options[i] + "] Hypotek aufnehmen.\n";
						break;
					case 4:
						menuOptions += "[" + options[i] + "] Mit anderen Spielern zu handeln.\n";
						break;
					case 5:
						menuOptions += "[" + options[i] + "] Zahlen um aus dem Gefängnis frei zu kommen.\n";
						break;
					case 6:
						menuOptions += "[" + options[i] +
						               "] Gemeinschaftskarte nutzen um aus dem Gefängnis frei zu kommen.\n";
						break;
					case 7:
						menuOptions += "[" + options[i] +
						               "] Ereigniskarte nutzen um aus dem Gefängnis frei zu kommen.\n";
						break;
					case 8:
						menuOptions += "[" + options[i] + "] Den Zug beenden.\n";
						break;
					case 9:
						menuOptions += "[" + options[i] + "] Den nächsten Zug beginnen.\n";
						break;
					case 10:
						menuOptions += "[" + options[i] + "] Aufgeben.\n";
				}
			}
		}
		return menuOptions;
	}

	//JAVADOC
	//TODO use maybe a hashmap for better performance see ConsoleMenu.nextTurn()
	private int[] getPlayerOptions(Player player, Field field, int turnState, int playerOptionsMax) {
		int[] playerOptions = new int[playerOptionsMax];
		int index = 0;
		if(turnState == 0) {
			playerOptions[0] = ++index;
		}
		if(field instanceof PurchasableCircularList) {
			if(((PurchasableCircularList) field).getOwner() == null &&
			   ((PurchasableCircularList) field).getPrice() <= player.getMoney()) {
				playerOptions[1] = ++index;
			}
			if(field instanceof StreetCircularList && ((PurchasableCircularList) field).getStage() >= 1 &&
			   ((PurchasableCircularList) field).getStage() < 5 &&
			   ((PurchasableCircularList) field).getOwner() == player &&
			   !((PurchasableCircularList) field).isInMortgage() &&
			   ((StreetCircularList) field).getUpgrade() <= player.getMoney()) {
				playerOptions[2] = ++index;
			}
			if(((PurchasableCircularList) field).getOwner() == player &&
			   !((PurchasableCircularList) field).isInMortgage()) {
				playerOptions[3] = ++index;
			}
			if(((PurchasableCircularList) field).getOwner() != player &&
			   ((PurchasableCircularList) field).getOwner() != null) {
				playerOptions[4] = ++index;
			}
		}
		if(player.isInJail()) {
			playerOptions[5] = ++index;
			if(player.isJailbaitCommunity()) {
				playerOptions[6] = ++index;
			}
			if(player.isJailbaitEvent()) {
				playerOptions[7] = ++index;
			}
		}
		if(turnState == 1) {
			playerOptions[8] = ++index;
		} else if(turnState == 3) {
			playerOptions[9] = ++index;
		}
		playerOptions[10] = ++index;
		return playerOptions;
	}

	//JAVADOC
	private void println(String text) {
		System.out.print("\n" + text + "\n> ");
	}
}