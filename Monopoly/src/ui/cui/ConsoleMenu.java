package ui.cui;

import objects.Player;
import objects.map.Field;
import objects.map.purchasable.FacilityCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.StreetCircularList;
import ui.Menu;

import java.util.ArrayList;

//JAVADOC
public class ConsoleMenu implements Menu {
	private final int   MENUOPTIONS = 13;
	private final Input in          = new Input();

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
	private void println(String text) {
		System.out.print("\n" + text + "\n> ");
	}

	@Override
	//JAVADOC
	public void playerPropertiesDetails(Player player) {
		String print = propertiesDetails(player, player.getProperties());
		if(print.equals("")) {
			System.out.println("Ihnen gehört nichts!");
		} else {
			System.out.println(print);
		}
	}

	//JAVADOC
	public void groupDetails(PurchasableCircularList property) {
		System.out.println(propertiesDetails(null, property.getGroupMembers()));
	}

	//JAVADOC
	private String propertiesDetails(Player player, ArrayList<PurchasableCircularList> properties) {
		String[] details = new String[properties.size()];
		for(int i = 0; i < details.length; i++) {
			details[i] = propertyDetail(player, properties.get(i));
		}
		return joinDetailLines(details);
	}

	//JAVADOC
	private String propertyDetail(Player player, PurchasableCircularList property) {
		int stage = property.getStage();
		String detail = "---------------------------------------------";
		detail += detailLine("Name: " + property.getName());
		if(property.getOwner() != null) {
			if(player != null) {
				detail += detailLine(
						"Besitzer: " + (player.equals(property.getOwner()) ? "Sie" : property.getOwner().getName()));
			} else {
				detail += detailLine("Besitzer: " + property.getOwner().getName());
			}
		} else {
			detail += detailLine("Besitzer: -");
		}
		if(!(property instanceof FacilityCircularList)) {
			detail += detailLine(
					"Einkommen: " + (property.isInMortgage() ? "Ist gepfändet!" : property.getIncome()[stage]));
		} else {
			detail += detailLine("Einkommen: " + (property.isInMortgage() ? "Ist gepfändet!" :
					property.getIncome()[stage] + " mal die Augenanzahl."));
		}
		if(property instanceof StreetCircularList) {
			if(stage == 0) {
				detail += detailLine("Häuser: Die Straße ist nicht monopolisiert.");
			} else if(stage < 6) {
				detail += detailLine("Häuser: " + (stage - 1) + "");
			} else {
				detail += detailLine("Hotel: 1.");
			}
		}
		detail += detailLine("Preis: " + property.getPrice());
		detail += detailLine("Hypotek: " + (property.getMortgage() < 0 ? "-" : "+") + property.getMortgage());
		return detail + "\n---------------------------------------------";
	}

	//JAVADOC
	private String detailLine(String line) {
		String box = "\n|";
		box += line;
		for(int i = 43 - line.length(); i > 0; i--) {
			box += " ";
		}
		return box + "|";
	}

	//JAVADOC
	private String joinDetailLines(String[] details) {
		String print = "";
		String[] one;
		String[] two;
		int length = details.length;
		int lengthEven = ((length % 2 == 0) ? length : length - 1);
		for(int i = 0, k = 1; k < lengthEven; i = i + 2, k = k + 2) {
			one = details[i].split("\n");
			two = details[k].split("\n");
			for(int j = 0; j < one.length; j++) {
				print += one[j] + " | " + two[j] + "\n";
			}
		}
		if(length > 1 && length % 2 != 0) {
			one = details[length - 1].split("\n");
			for(String anOne : one) {
				print += anOne + " |\n";
			}
		} else if(length > 0) {
			print += details[length - 1];
		}
		return print;
	}

	//JAVADOC
	//TODO probably improve
	public void inJail() {
		System.out.print("Sie sind im Gefängnis!");
	}

	//JAVADOC
	@Override
	public int nextTurn(Player player, int turnState) {
		int userInt;
		int[] options = getPlayerOptions(player, turnState);
		String menuOptions = getNextTurnOptions(options);
		String playerStatus = getPlayerStatus(player, turnState == 0);
		println(playerStatus + "\n\nSie haben nun folgende Optionen:\n" + menuOptions);
		userInt = in.userInt(options, "Bitte wählen Sie eine der Optionen aus:\n" + menuOptions);
		for(int i = 0; i < options.length; i++) {
			if(userInt == options[i]) {
				userInt = i;
				break;
			}
		}
		if(userInt == MENUOPTIONS - 1) {
			println("Sind Sie sich sicher, dass Sie aufgeben wollen? j/n");
			if(in.userChar(new char[]{'j', 'n', '\0'}, "Bitte geben Sie 'j' oder 'n' ein.") == 'n') {
				return nextTurn(player, turnState);
			}
		}
		return userInt;
	}

	//JAVADOC
	private String getPlayerStatus(Player player, boolean beginOfTurn) {
		String playerStatus = "";
		Field field = player.getField();
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
				                ((PurchasableCircularList) field).getBill(player);
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
		for(int i = 0; i < MENUOPTIONS; i++) {
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
						menuOptions += "[" + options[i] + "] Hypotek abzahlen.\n";
						break;
					case 5:
						menuOptions += "[" + options[i] + "] Mit anderen Spielern zu handeln.\n";
						break;
					case 6:
						menuOptions += "[" + options[i] + "] Zahlen um aus dem Gefängnis frei zu kommen.\n";
						break;
					case 7:
						menuOptions += "[" + options[i] +
						               "] Gemeinschaftskarte nutzen um aus dem Gefängnis frei zu kommen.\n";
						break;
					case 8:
						menuOptions += "[" + options[i] +
						               "] Ereigniskarte nutzen um aus dem Gefängnis frei zu kommen.\n";
						break;
					case 9:
						menuOptions += "[" + options[i] + "] Den Zug beenden.\n";
						break;
					case 10:
						menuOptions += "[" + options[i] + "] Den nächsten Zug beginnen.\n";
						break;
					case 11:
						menuOptions += "[" + options[i] + "] Ihren Besitztum ansehen.\n";
						break;
					case MENUOPTIONS - 1:
						menuOptions += "[" + options[i] + "] Aufgeben.\n";
				}
			}
		}
		return menuOptions;
	}

	//JAVADOC
	//TODO maybe use a hashmap for better performance see ConsoleMenu.nextTurn()
	private int[] getPlayerOptions(Player player, int turnState) {
		int[] playerOptions = new int[MENUOPTIONS];
		int index = 0;
		Field field = player.getField();
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
			if(((PurchasableCircularList) field).getOwner() == player &&
			   ((PurchasableCircularList) field).isInMortgage() &&
			   ((PurchasableCircularList) field).getMortgage() <= player.getMoney()) {
				playerOptions[4] = ++index;
			}
			if(((PurchasableCircularList) field).getOwner() != player &&
			   ((PurchasableCircularList) field).getOwner() != null) {
				playerOptions[5] = ++index;
			}
		}
		if(player.isInJail()) {
			playerOptions[6] = ++index;
			if(player.isJailbaitCommunity()) {
				playerOptions[7] = ++index;
			}
			if(player.isJailbaitEvent()) {
				playerOptions[8] = ++index;
			}
		}
		if(turnState == 1) {
			playerOptions[9] = ++index;
		} else if(turnState == 3) {
			playerOptions[10] = ++index;
		}
		playerOptions[11] = ++index;
		playerOptions[12] = ++index;
		return playerOptions;
	}
}