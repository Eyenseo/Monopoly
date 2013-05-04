package ui.cui;

import core.TradeManager;
import objects.Player;
import objects.map.FieldCircularList;
import objects.map.notPurchasable.Jail;
import objects.map.purchasable.Facility;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.Street;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

//JAVADOC
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
		System.out.println("Sie haben " + player.getMoney() + " Geld.");
		playerPropertiesDetails(player, 0);
	}

	//JAVADOC
	public void playerPropertiesDetails(Player player, int index) {
		String print = propertiesDetails(player, player.getProperties(), index);
		if(print.equals("")) {
			System.out.println("---");
		} else {
			System.out.println(print);
		}
	}

	//JAVADOC
	public void groupDetails(PurchasableCircularList property) {
		System.out.println(propertiesDetails(null, property.getGroupMembers(), 0));
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
	private String propertyDetail(Player player, PurchasableCircularList property, int index) {
		int stage = property.getStage();
		String detail = "-----------------------------------------------";
		if(index != 0) {
			if(index < 10) {
				detail = "----------------------" + index + "------------------------";
			} else {
				detail = "----------------------" + index + "-----------------------";
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
		if(!(property instanceof Facility)) {
			detail += detailLine(
					"Einkommen: " + (property.isInMortgage() ? "Ist gepfaendet!" : property.getIncome()[stage]));
		} else {
			detail += detailLine("Einkommen: " + (property.isInMortgage() ? "Ist gepfaendet!" :
					property.getIncome()[stage] + " mal die Augenanzahl."));
		}
		if(property instanceof Street) {
			if(stage == 0) {
				detail += detailLine("Haeuser: Die Strasse ist nicht monopolisiert.");
			} else if(stage < property.getMaxStage()) {
				detail += detailLine("Haeuser: " + (stage - 1) + "");
			} else {
				detail += detailLine("Hotel: 1.");
			}
		}
		detail += detailLine("Preis: " + (property.getPrice() == -1 ? "-" : property.getPrice()));
		detail += detailLine("Hypotek: " + (property.getMortgage() < 0 ? "-" : "+") + property.getMortgage());
		if(!(property instanceof Street)) {
			detail += detailLine("");
		}
		return detail + "\n-----------------------------------------------\n";
	}

	//JAVADOC
	private String detailLine(String line) {
		String box = "\n|";
		box += line;
		for(int i = 45 - line.length(); i > 0; i--) {
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
		} else if(length == 1) {
			print += details[0];
		}
		return print;
	}

	//JAVADOC
	private String propertyDetail(Player player, PurchasableCircularList property) {
		return propertyDetail(player, property, 0);
	}

	//JAVADOC
	//TODO probably improve
	public void inJail() {
		System.out.print("Sie sind im Gefaengnis!");
	}

	//JAVADOC
	private String getTurnDetails(Player player, boolean beginOfTurn) {
		String turnDetails = "";
		FieldCircularList field = player.getField();
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
				case 3:
					menuOptions += "[" + (i + 1) + "] Mit Spielern handeln.\n";
					break;
				case 22:
					menuOptions += "[" + (i + 1) + "] Hypotek aufnehmen.\n";
					break;
				case 23:
					menuOptions += "[" + (i + 1) + "] Hypotek abzahlen.\n";
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
		FieldCircularList field = player.getField();
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
					menuOptions += "[" + (i + 1) + "] 1000 zahlen um aus dem Gefaengnis frei zu kommen.\n";
					break;
				case 31:
					menuOptions += "[" + (i + 1) + "] Karte nutzen um aus dem Gefaengnis frei zu kommen.\n";
					break;
			}
		}
		println(menuOptions);
		return options[in.userInt(1, options.length, "Bitte waehlen Sie eine der Optionen aus:\n" + menuOptions) - 1];
	}

	//JAVADOC
	private int[] buildActionMenu(Player player, int turnState) {
		FieldCircularList field = player.getField();
		ArrayList<Integer> options = new ArrayList<Integer>();
		options.add(1);
		if(turnState == 0) {
			options.add(10);
		}
		if(player.isInJail()) {
			options.add(30);
			if(player.isJailbait()) {
				options.add(31);
			}
		}
		if(field instanceof PurchasableCircularList) {
			if(((PurchasableCircularList) field).getOwner() == null &&
			   ((PurchasableCircularList) field).getPrice() <= player.getMoney()) {
				options.add(20);
			} else if(field instanceof Street && ((PurchasableCircularList) field).getOwner().equals(player) &&
			          ((PurchasableCircularList) field).getPrice() > 0 &&
			          ((PurchasableCircularList) field).getPrice() <= player.getMoney() &&
			          ((PurchasableCircularList) field).getStage() > 0 &&
			          ((PurchasableCircularList) field).getStage() < ((PurchasableCircularList) field).getMaxStage()) {
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
					getTurnDetails(player, turnState == 0);
					choice = showActionMenu(buildActionMenu(player, turnState));
					break;
				case 1: //ShowOrganisationMenu
					getTurnDetails(player, turnState == 0);
					choice = showOrganisationMenu(buildOrganisationMenu(player));
					break;
				case 2: //ViewProperty
					playerPropertiesDetails(player);
					choice = 1;
					break;
				case 3: //Trade with other player
					tradeMenu(player, playerVector);
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
					((Street) player.getField()).nextStage();
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
					((Jail) player.getField()).payFine(player);
					choice = 0;
					break;
				case 31: //Use Community jailbait card
					player.useJailbait();
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

	//JAVADOC
	private Player showPlayer(Player currentPlayer, Vector<Player> playerVector) {
		int index = 0;
		int[] options = new int[playerVector.size() - 1];
		String menuOptions = "";
		for(int i = 0; i < playerVector.size(); i++) {
			if(!playerVector.get(i).equals(currentPlayer)) {
				options[index] = i;
				menuOptions += "[" + ++index + "] " + playerVector.get(i).getName();
			}
		}
		println(menuOptions);
		return playerVector
				.get(options[in.userInt(1, options.length, "Bitte waehlen Sie eine der Optionen aus:\n" + menuOptions) -
				             1]);
	}

	//JAVADOC
	private int showHaggleMenu(int[] options) {
		String menuOptions = "";
		for(int i = 0; i < options.length; i++) {
			switch(options[i]) {
				case 1:
					menuOptions += "[" + (i + 1) + "] Zeige den Handel.\n";
					break;
				case 2:
					menuOptions += "[" + (i + 1) + "] Fuege Grundstueck zum Handel hinzu.\n";
					break;
				case 3:
					menuOptions += "[" + (i + 1) + "] Setze Preis vom Handel.\n";
					break;
				case 4:
					menuOptions += "[" + (i + 1) + "] Handel abschließen.\n";
					break;
				case 5:
					menuOptions += "[" + (i + 1) + "] Handel vorschlagen.\n";
					break;
				case 6:
					menuOptions += "[" + (i + 1) + "] Handel abbrechen.\n";
					break;
				case 7:
					menuOptions += "[" + (i + 1) + "] Entferne Grundstueck vom Handel.\n";
					break;
				case 8:
					menuOptions += "[" + (i + 1) + "] Handel ueberarbeiten.\n";
					break;
				case 9:
					menuOptions += "[" + (i + 1) + "] Ihren Besitz ansehen.\n";
			}
		}
		println(menuOptions);
		return options[in.userInt(1, options.length, "Bitte waehlen Sie eine der Optionen aus:\n" + menuOptions) - 1];
	}

	//JAVADOC
	private int[] buildHaggleMenu(boolean seller, boolean confirm) {
		ArrayList<Integer> options = new ArrayList<Integer>();
		options.add(1);
		options.add(9);
		if(confirm) {
			options.add(8);
			options.add(4);
		} else {
			options.add(2);
			options.add(7);
			if(seller) {
				options.add(3);
				options.add(4);
			} else {
				options.add(5);
			}
		}
		options.add(6);
		return integerToInt(options);
	}

	//JAVADOC
	//TODO Improver parameter
	private int executeHaggleMenu(TradeManager tm, Player currentPlayer, Player counterPlayer, int choice,
	                              int tradeState) {
		PurchasableCircularList property;
		switch(choice) {
			case 1:
				showTrade(tm, counterPlayer, currentPlayer);
				break;
			case 2:
				property = showSelectProperty(buildSelectProperty(tm, currentPlayer, false), currentPlayer);
				if(currentPlayer != null) {
					tm.addTrade(currentPlayer, property);
				}
				break;
			case 3:
				println("Bitte geben Sie den gewuenschten Geldbetrag ein.");
				tm.addTrade(currentPlayer,
				            in.userInt(0, 1000000, "Bitte geben Sie den gewuenschten Geldbetrag (0-1000000) ein."));
				break;
			case 4:
				if(tradeState == 5) {
					tradeState = 3;
				} else {
					tradeState = 5;
				}
				break;
			case 5:
				tradeState = 2;
				break;
			case 6:
				tradeState = 0;
				break;
			case 7:
				property = showSelectProperty(buildSelectProperty(tm, currentPlayer, true), currentPlayer);
				if(currentPlayer != null) {
					tm.removeTrade(currentPlayer, property);
				}
				break;
			case 8:
				tradeState = 1;
				break;
			case 9:
				playerPropertiesDetails(counterPlayer);
				break;
		}
		return tradeState;
	}

	//JAVADOC
	private int[] buildSelectProperty(TradeManager tm, Player player, boolean remove) {
		ArrayList<Integer> options = new ArrayList<Integer>();
		ArrayList<PurchasableCircularList> property = player.getProperties();
		for(int i = 0; i < property.size(); i++) {
			if(remove) {
				if(tm.isInTrade(property.get(i))) {
					options.add(i);
				}
			} else {
				if(!tm.isInTrade(property.get(i))) {
					options.add(i);
				}
			}
		}
		options.add(-1);
		return integerToInt(options);
	}

	//JAVADOC
	private PurchasableCircularList showSelectProperty(int[] options, Player player) {
		int choice;
		String[] menuArray = new String[options.length - 1];
		String menuOptions;
		ArrayList<PurchasableCircularList> property = player.getProperties();
		for(int i = 0; i < menuArray.length; i++) {
			if(options[i] != -1) {
				menuArray[i] = propertyDetail(null, property.get(options[i]), i + 1);
			}
		}
		menuOptions = joinDetailLines(menuArray);
		menuOptions += "[" + options.length + "] Zurueck.\n";
		println(menuOptions);
		choice = in.userInt(1, options.length, "Bitte waehlen Sie eine der Optionen aus:\n" + menuOptions) - 1;
		if(choice == options.length - 1) {
			return null;
		}
		return property.get(options[choice]);
	}

	//JAVADOC
	private void showTrade(TradeManager tm, Player buyer, Player seller) {
		String temp = "";
		String show = buyer.getName() + " bekommt:\n";
		for(int i = 0; i < tm.getProperty(seller).size(); i++) {
			if(tm.getProperty(seller).get(i) != null) {
				temp += propertyDetail(null, tm.getProperty(seller).get(i));
			}
		}
		if(temp.equals("")) {
			show += "-\n";
		} else {
			show += temp;
		}
		temp = "";
		show += "" + tm.getMoney(seller) + " Geld.\n";
		show += seller.getName() + " bekommt:\n";
		for(int i = 0; i < tm.getProperty(buyer).size(); i++) {
			if(tm.getProperty(seller).get(i) != null) {
				temp += propertyDetail(null, tm.getProperty(buyer).get(i));
			}
		}
		if(temp.equals("")) {
			show += "-\n";
		} else {
			show += temp;
		}
		show += "" + tm.getMoney(buyer) + " Geld.";
		System.out.println(show);
	}

	//JAVADOC
	public void tradeMenu(Player buyer, Vector<Player> players) {
		int tradeState = 1;
		Player seller = showPlayer(buyer, players);
		TradeManager tm = new TradeManager(buyer, seller);
		while(tradeState != 0) {
			System.out.println(buyer.getName() + " waehlen Sie:");
			while(tradeState != 0 && tradeState == 1) {
				tradeState =
						executeHaggleMenu(tm, seller, buyer, showHaggleMenu(buildHaggleMenu(false, false)), tradeState);
			}
			if(tradeState != 0) {
				System.out.println(seller.getName() + " waehlen Sie:");
			}
			while(tradeState != 0 && tradeState == 2) {
				tradeState =
						executeHaggleMenu(tm, buyer, seller, showHaggleMenu(buildHaggleMenu(true, false)), tradeState);
			}
			if(tradeState == 5) {
				System.out.println(buyer.getName() + " waehlen Sie:");
				while(tradeState != 0 && tradeState == 5) {
					tradeState = executeHaggleMenu(tm, seller, buyer, showHaggleMenu(buildHaggleMenu(true, true)),
					                               tradeState);
				}
			}
			if(tradeState == 3) {
				if(!tm.deal()) {
					System.out.println("Der Deal ist an Geldmangel geplatzt!");
					tradeState = 1;
				} else {
					break;
				}
			}
		}
	}
}