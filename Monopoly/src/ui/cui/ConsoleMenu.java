package ui.cui;

import objects.Player;
import objects.map.Field;
import objects.map.purchasable.FacilityCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.StreetCircularList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class ConsoleMenu {
	private final Input in = new Input();

	//JAVADOC
	private String getName() {
		String name;
		println("Wie heissen Sie?");
		name = in.readString();
		println("Wollen Sie \"" + name + "\" heissen? j/n");
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

	//JAVADOC
	public void playerPropertiesDetails(Player player) {
		String print = propertiesDetails(player, player.getProperties());
		if(print.equals("")) {
			System.out.println("Ihnen gehoert nichts!");
		} else {
			System.out.println(print);
		}
	}

	//JAVADOC
	public void groupDetails(PurchasableCircularList property) {
		System.out.println(propertiesDetails(null, property.getGroupMembers()));
	}

	//JAVADOC
	private String propertiesDetails(Player player, ArrayList<PurchasableCircularList> properties, int index) {
		String[] details = new String[properties.size()];
		for(int i = 0; i < details.length; i++) {
			details[i] = propertyDetail(player, properties.get(i), index);
		}
		return joinDetailLines(details);
	}

	//JAVADOC
	private String propertiesDetails(Player player, ArrayList<PurchasableCircularList> properties) {
		return propertiesDetails(player, properties, 0);
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
		} else if(length == 1) {
			print += details[0];
		}
		return print;
	}

	//JAVADOC
	private String propertyDetail(Player player, PurchasableCircularList property, int index) {
		int stage = property.getStage();
		String detail = "---------------------------------------------";
		if(index != 0) {
			if(index < 10) {
				detail = "---------------------" + index + "-----------------------";
			} else {
				detail = "---------------------" + index + "----------------------";
			}
		}
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
					"Einkommen: " + (property.isInMortgage() ? "Ist gepfaendet!" : property.getIncome()[stage]));
		} else {
			detail += detailLine("Einkommen: " + (property.isInMortgage() ? "Ist gepfaendet!" :
					property.getIncome()[stage] + " mal die Augenanzahl."));
		}
		if(property instanceof StreetCircularList) {
			if(stage == 0) {
				detail += detailLine("Haeuser: Die Strasse ist nicht monopolisiert.");
			} else if(stage < 6) {
				detail += detailLine("Haeuser: " + (stage - 1) + "");
			} else {
				detail += detailLine("Hotel: 1.");
			}
		}
		detail += detailLine("Preis: " + (property.getPrice() == -1 ? "-" : property.getPrice()));
		detail += detailLine("Hypotek: " + (property.getMortgage() < 0 ? "-" : "+") + property.getMortgage());
		if(!(property instanceof StreetCircularList)) {
			detail += detailLine("");
		}
		return detail + "\n---------------------------------------------";
	}

	//JAVADOC
	private String propertyDetail(Player player, PurchasableCircularList property) {
		return propertyDetail(player, property, 0);
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
	//TODO probably improve
	public void inJail() {
		System.out.print("Sie sind im Gefaengnis!");
	}

	//JAVADOC
	private String getTurnDetails(Player player, boolean beginOfTurn) {
		String turnDetails = "";
		Field field = player.getField();
		if(beginOfTurn) {
			turnDetails += player.getName() + " ist am Zug!";
		} else {
			turnDetails += "Wuerfel 1: " + player.getDices()[0] + "\tWuerfel 2: " + player.getDices()[1];
		}
		if(field instanceof PurchasableCircularList) {
			if(((PurchasableCircularList) field).getOwner() != null &&
			   ((PurchasableCircularList) field).getOwner() != player) {
				turnDetails += "\nOrt:\t" + field.getName() + " (" +
				               ((PurchasableCircularList) field).getOwner().getName() + ")" + " Miete:\t" +
				               ((PurchasableCircularList) field).getBill(player);
			} else if(((PurchasableCircularList) field).getOwner() != null) {
				turnDetails += "\nOrt:\t" + field.getName() + " (Sie)";
			} else {
				turnDetails += "\nOrt:\t" + field.getName() + " Preis:\t" +
				               (((PurchasableCircularList) field).getPrice());
			}
		} else {
			turnDetails += "\nOrt:\t" + field.getName();
		}
		turnDetails += "\nGeld:\t" + player.getMoney();
		return turnDetails;
	}

	//JAVADOC
	private int showOrganisationMenu(int[] options) {
		int choice;
		String menuOptions = "";
		for(int i = 0; i < options.length; i++) {
			switch(options[i]) {
				case 0:
					menuOptions += "[" + (i + 1) + "] Zum Aktionsmenue wechseln.\n";
					break;
				case 2:
					menuOptions += "[" + (i + 1) + "] Ihren Besitz ansehen.\n";
					break;
				case 22:
					menuOptions += "[" + (i + 1) + "] Hypotek aufnehmen.\n";
					break;
				case 23:
					menuOptions += "[" + (i + 1) + "] Hypotek abzahlen.\n";
					break;
				case 24:
					menuOptions += "[" + (i + 1) + "] Besitz zum Verkauf freigeben.\n";
					break;
				case 3:
					menuOptions += "[" + (i + 1) + "] Mit Spielern handeln.\n";
					break;
				case 13:
					menuOptions += "[" + (i + 1) + "] Aufgeben.\n";
					break;
			}
		}
		println(menuOptions);
		choice = in.userInt(1, options.length, "Bitte waehlen Sie eine der Optionen aus:\n" + menuOptions) - 1;
		if(options[choice] == 13) {
			println("Sind Sie sich sicher, dass Sie aufgeben wollen? j/n");
			if(in.userChar(new char[]{'j', 'n', '\0'}, "Bitte geben Sie 'j' oder 'n' ein.") == 'n') {
				return showOrganisationMenu(options);
			}
		}
		return options[choice];
	}

	//JAVADOC
	private int[] buildOrganisationMenu(Player player) {
		Field field = player.getField();
		ArrayList<Integer> options = new ArrayList<Integer>();
		options.add(0);
		options.add(2);
		if(field instanceof PurchasableCircularList) {
			if(!((PurchasableCircularList) field).isInMortgage()) {
				options.add(22);
			} else if(((PurchasableCircularList) field).getMortgage() <= player.getMoney()) {
				options.add(23);
			}
		}
		if(!player.getProperties().isEmpty()) {
			options.add(24);
		}
		options.add(3);
		options.add(13);
		return integerToInt(options);
	}

	//JAVADOC
	private int showActionMenu(int[] options) {
		String menuOptions = "";
		for(int i = 0; i < options.length; i++) {
			switch(options[i]) {
				case 1:
					menuOptions += "[" + (i + 1) + "] Zum Verwaltungsmenue wechseln.\n";
					break;
				case 10:
					menuOptions += "[" + (i + 1) + "] Wuerfeln.\n";
					break;
				case 11:
					menuOptions += "[" + (i + 1) + "] Den Zug beenden.\n";
					break;
				case 12:
					menuOptions += "[" + (i + 1) + "] Den naechsten Zug beginnen.\n";
					break;
				case 20:
					menuOptions += "[" + (i + 1) + "] Anwesen kaufen.\n";
					break;
				case 21:
					menuOptions += "[" + (i + 1) + "] Haeuser kaufen.\n";
					break;
				case 30:
					menuOptions += "[" + (i + 1) + "] Zahlen um aus dem Gefaengnis frei zu kommen.\n";
					break;
				case 31:
					menuOptions +=
							"[" + (i + 1) + "] Gemeinschaftskarte nutzen um aus dem Gefaengnis frei zu kommen.\n";
					break;
				case 32:
					menuOptions += "[" + (i + 1) + "] Ereigniskarte nutzen um aus dem Gefaengnis frei zu kommen.\n";
					break;
			}
		}
		println(menuOptions);
		return options[in.userInt(1, options.length, "Bitte waehlen Sie eine der Optionen aus:\n" + menuOptions) - 1];
	}

	//JAVADOC
	private int[] buildActionMenu(Player player, int turnState) {
		Field field = player.getField();
		ArrayList<Integer> options = new ArrayList<Integer>();
		options.add(1);
		if(turnState == 0) {
			options.add(10);
		}
		if(player.isInJail()) {
			options.add(30);
			if(player.isJailbaitCommunity()) {
				options.add(31);
			}
			if(player.isJailbaitEvent()) {
				options.add(32);
			}
		}
		if(field instanceof PurchasableCircularList) {
			if(((PurchasableCircularList) field).getOwner() == null &&
			   ((PurchasableCircularList) field).getPrice() <= player.getMoney()) {
				options.add(20);
			} else if(((PurchasableCircularList) field).getOwner().equals(player) &&
			          ((PurchasableCircularList) field).getPrice() > 0 &&
			          ((PurchasableCircularList) field).getPrice() <= player.getMoney() &&
			          ((PurchasableCircularList) field).getStage() > 0) {
				options.add(21);
			}
		}
		if(turnState == 1) {
			options.add(11);
		} else if(turnState == 3) {
			options.add(12);
		}
		return integerToInt(options);
	}

	//JAVADOC
	public int mainMenu(Player player, Vector<Player> playerVector, int turnState) {
		boolean end = false;
		int choice = 0;
		System.out.println(getTurnDetails(player, turnState == 0));
		while(!end) {
			switch(choice) {
				case 0: //ShowActionMenu
					choice = showActionMenu(buildActionMenu(player, turnState));
					break;
				case 1: //ShowOrganisationMenu
					choice = showOrganisationMenu(buildOrganisationMenu(player));
					break;
				case 2: //ViewProperty
					playerPropertiesDetails(player);
					choice = 1;
					break;
				case 3: //Trade with other player
					//TODO implement
					System.err.print("To be implemented ...");
					choice = 1;
					break;
				case 12: //Throw dice after doubles
				case 10: //Throw dice
					turnState = player.move() ? 3 : 1;
					end = true;
					break;
				case 11: //End turn
					turnState = 2;
					end = true;
					break;
				case 13: //Give up
					turnState = 4;
					end = true;
					break;
				case 20: //Buy
					player.buy((PurchasableCircularList) player.getField());
					System.out.println(propertyDetail(player, (PurchasableCircularList) player.getField()));
					choice = 0;
					break;
				case 21: //Buy upgrade (House/Hotel)
					((StreetCircularList) player.getField()).nextStage();
					choice = 0;
					break;
				case 22: //Get Mortgage
					((PurchasableCircularList) player.getField()).setInMortgage(true);
					choice = 1;
					break;
				case 23: //Pay Mortgage
					((PurchasableCircularList) player.getField()).setInMortgage(false);
					choice = 1;
					break;
				case 24: //Set price of property
					//TODO implement
					System.err.print("To be implemented ...");
					choice = 1;
					break;
				case 30: //Pay to get out of jail
					//TODO implement
					System.err.print("To be implemented ...");
					choice = 0;
					break;
				case 31: //Use Community jailbait card
					//TODO implement
					System.err.print("To be implemented ...");
					choice = 0;
					break;
				case 32: //Use Choice jailbait card
					//TODO implement
					System.err.print("To be implemented ...");
					choice = 0;
					break;
			}
		}
		return turnState;
	}

	//JAVADOC
	private int[] integerToInt(ArrayList<Integer> integers) {
		int[] re = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for(int i = 0; i < re.length; i++) {
			re[i] = iterator.next();
		}
		return re;
	}
}