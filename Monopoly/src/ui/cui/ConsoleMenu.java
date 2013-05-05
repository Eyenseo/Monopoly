package ui.cui;

import core.TradeManager;
import objects.Player;
import objects.card.Card;
import objects.card.PayFineTakeCard;
import objects.map.FieldCircularList;
import objects.map.notPurchasable.Jail;
import objects.map.purchasable.Facility;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.Street;
import ui.Menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * The ConsoleMenu class provides all interaction with the Human over the console.
 *
 * @author Eyenseo
 * @version 1
 */
public class ConsoleMenu implements Menu {
	private final Input in = new Input();

	/**
	 * The method asks the human for a name of a Player.
	 *
	 * @return The return value is the the name of a Player.
	 */
	@Override
	public String getName() {
		String name;
		println("Wie heissen Sie?");
		name = in.readString();
		println("Wollen Sie \"" + name + "\" heissen? j/n");
		if(in.userChar(new char[]{'j', 'n', '\0'}, "Bitte geben Sie 'j' oder 'n' ein.") == 'n') {
			return getName();
		}
		return name;
	}

	/**
	 * The method asks the human how many player will play the game.
	 *
	 * @return The return value is the number of player that want to play the game.
	 */
	@Override
	public int playerAmount() {
		println("Wie viele Spieler werden spielnen?");
		return in.readInt();
	}

	/**
	 * The method prints the String in a way that the Human will input something.
	 *
	 * @param text The value determines the text to be printed on the screen.
	 */
	private void println(String text) {
		System.out.print("\n" + text + "\n> ");
	}

	/**
	 * @param integers The value determines an ArrayList that will be converted to an Array.
	 * @return The return value is the converted ArrayList.
	 */
	private int[] integerToInt(ArrayList<Integer> integers) {
		int[] re = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for(int i = 0; i < re.length; i++) {
			re[i] = iterator.next();
		}
		return re;
	}

	/**
	 * The method prints the Details of the group members without index.
	 *
	 * @param player   The value determines the payer to check against for the owner of the PurchasableCircularList objects.
	 * @param property The value determines the PurchasableCircularList object of the group to be shown.
	 */
	private void groupDetails(Player player, PurchasableCircularList property) {
		System.out.println(propertiesDetails(player, property.getGroupMembers(), 0));
	}

	/**
	 * @param player   The value determines the payer to check against for the owner of the PurchasableCircularList objects.
	 * @param property The value determines the PurchasableCircularList object of the group to be shown.
	 * @param index    The value determines the start index to be shown.
	 * @return The return value is a, in two column arranged String of the propertyDetail method.
	 */
	private String propertiesDetails(Player player, ArrayList<PurchasableCircularList> property, int index) {
		String[] details = new String[property.size()];
		for(int i = 0; i < details.length; i++) {
			details[i] = propertyDetail(player, property.get(i), index);
			if(index != 0) {
				index++;
			}
		}
		return joinDetailLines(details);
	}

	/**
	 * @param player   The value determines the payer to check against for the owner of the PurchasableCircularList objects.
	 * @param property The value determines the PurchasableCircularList object of the group to be shown.
	 * @param index    The value determines the start index to be shown.
	 * @return The return value is a box with all information about a PurchasableCircularList object.
	 */
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
		detail += detailLine("Hypotek: " + (property.isInMortgage() ? "-" : "+") + property.getMortgage());
		if(!(property instanceof Street)) {
			detail += detailLine("");
		}
		return detail + "\n-----------------------------------------------\n";
	}

	/**
	 * @param line The value determines the String that will be encased by '\n|' and '|'.
	 * @return The return value is the encased string
	 */
	private String detailLine(String line) {
		String box = "\n|";
		box += line;
		for(int i = 45 - line.length(); i > 0; i--) {
			box += " ";
		}
		return box + "|";
	}

	/**
	 * @param details The value determines the String of the propertyDetail method to be rearranged.
	 * @return The return value is the, to two columns rearranged String.
	 */
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

	/**
	 * @param player   The value determines the payer to check against for the owner of the PurchasableCircularList objects.
	 * @param property The value determines the PurchasableCircularList object of the group to be shown.
	 * @return The return value is a box with all information about a PurchasableCircularList object.
	 */
	private String propertyDetail(Player player, PurchasableCircularList property) {
		return propertyDetail(player, property, 0);
	}

	/**
	 * Prints "Sie sind im Gefaengnis!"
	 */
	//TODO probably improve
	@Override
	public void showInJail() {
		System.out.println("Sie sind im Gefaengnis!");
	}

	/**
	 * @param player      The value determines the player of whom the information shall be shown.
	 * @param beginOfTurn The value determines if it is the begin of the turn.
	 * @return The return value is a String with all information about the turn.
	 */
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

	/**
	 * The method prints the Menu for the Player properties.
	 *
	 * @param player The value determines the player who is currently at turn.
	 */
	private void playerPropertiesMenu(Player player) {
		int choice;
		ArrayList<PurchasableCircularList> property = player.getProperties();
		String menuOptions = "Sie haben " + player.getMoney() + " Geld.\n";
		menuOptions += "Waehlen Sie ein Grundstuek um die anderen Grundstueke der Gruppe zu sehen.\n";
		menuOptions += propertiesDetails(player, property, 1);
		menuOptions += "[" + (property.size() + 1) + "] Zurueck \n";
		println(menuOptions);
		choice = in.userInt(1, property.size() + 1, "Bitte waehlen Sie eine der Optionen aus:\n" + menuOptions) - 1;
		if(choice != property.size()) {
			groupDetails(player, property.get(choice));
		}
	}

	/**
	 * @param player    The value determines the player who is currently at turn.
	 * @param turnState The value determines the turn state.
	 * @return The return value is a Array - the index is the Menu point that the human has to enter, the value is the method number to be started.
	 */
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

	/**
	 * The method asks what the player wants to do.
	 *
	 * @param player    The value determines the player who is currently at turn.
	 * @param turnState The value determines the turn state.
	 * @return The return value is the number of which method shall be executed.
	 */
	private int showActionMenu(Player player, int turnState) {
		int[] options = buildActionMenu(player, turnState);
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
					menuOptions += "[" + (i + 1) + "] Erneut wuerfeln.\n";
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

	/**
	 * @param player The value determines the player who is currently at turn.
	 * @return The return value is a Array - the index is the Menu point that the human has to enter, the value is the method number to be started.
	 */
	private int[] buildOrganisationMenu(Player player) {
		ArrayList<Integer> options = new ArrayList<Integer>();
		options.add(0);
		options.add(2);
		for(PurchasableCircularList property : player.getProperties()) {
			if(!property.isInMortgage()) {
				options.add(22);
				break;
			}
		}
		for(PurchasableCircularList property : player.getProperties()) {
			if(property.isInMortgage()) {
				options.add(23);
				break;
			}
		}
		options.add(3);
		options.add(13);
		return integerToInt(options);
	}

	/**
	 * The method asks what the player wants to do.
	 *
	 * @param player The value determines the player who is currently at turn.
	 * @return The return value is the number of which method shall be executed.
	 */
	private int showOrganisationMenu(Player player) {
		int choice;
		int[] options = buildOrganisationMenu(player);
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
				return showOrganisationMenu(player);
			}
		}
		return options[choice];
	}

	/**
	 * @param player The value determines the player who is currently at turn.
	 * @param take   The value determines if it's a menu to take mortgage or to remove mortgage.
	 * @return The return value is the number of which method shall be executed.
	 */
	private int[] buildMortageMenu(Player player, boolean take) {
		ArrayList<Integer> options = new ArrayList<Integer>();
		ArrayList<PurchasableCircularList> property = player.getProperties();
		for(int i = 0; i < property.size(); i++) {
			if(take) {
				if(!property.get(i).isInMortgage()) {
					options.add(i);
				}
			} else {
				if(property.get(i).isInMortgage() && player.getMoney() >= property.get(i).getMortgage()) {
					options.add(i);
				}
			}
		}
		options.add(-1);
		return integerToInt(options);
	}

	/**
	 * The method asks the human which PurchasableCircularList that shall be added or removed from mortgage
	 *
	 * @param player The value determines the player who is currently at turn.
	 * @param take   The value determines if it's a menu to take mortgage or to remove mortgage.
	 * @return The return value is the PurchasableCircularList that will be added or removed from mortgage.
	 */
	private PurchasableCircularList showMortageMenu(Player player, boolean take) {
		int choice;
		int[] options = buildMortageMenu(player, take);
		String menuOptions;
		String[] menuArray = new String[options.length - 1];
		ArrayList<PurchasableCircularList> property = player.getProperties();
		if(take) {
			System.out.println("Hypotek aufnehmen:");
		} else {
			System.out.println("Hypotek abbezahlen:");
		}
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

	/**
	 * The method asks the human to select a Player to trade with.
	 *
	 * @param currentPlayer The value determines the player who is currently at turn.
	 * @param playerVector  The value determines a Vector of all player.
	 * @return The return value is the selected Player object.
	 */
	private Player showPlayer(Player currentPlayer, Vector<Player> playerVector) {
		int index = 0;
		int choice;
		int[] options = new int[playerVector.size() - 1];
		String menuOptions = "";
		for(int i = 0; i < playerVector.size(); i++) {
			if(!playerVector.get(i).equals(currentPlayer)) {
				options[index] = i;
				menuOptions += "[" + ++index + "] " + playerVector.get(i).getName();
			}
		}
		println(menuOptions);
		choice = in.userInt(1, options.length, "Bitte waehlen Sie eine der Optionen aus:\n" + menuOptions) - 1;
		return playerVector.get(options[choice]);
	}

	/**
	 * The method shows the Human what the trade looks like.
	 *
	 * @param tm            The value determines the used TradeManager.
	 * @param currentPlayer The value determines the player who is currently at turn.
	 * @param counterPlayer The value determines the player who is not currently at turn.
	 */
	private void showTrade(TradeManager tm, Player currentPlayer, Player counterPlayer) {
		String temp = "";
		String show = "Sie bekommen:\n";
		for(int i = 0; i < tm.getProperty(counterPlayer).size(); i++) {
			if(tm.getProperty(counterPlayer).get(i) != null) {
				temp += propertyDetail(null, tm.getProperty(counterPlayer).get(i));
			}
		}
		if(temp.equals("")) {
			show += "-\n";
		} else {
			show += temp;
		}
		temp = "";
		show += "" + tm.getMoney(counterPlayer) + " Geld.\n";
		show += counterPlayer.getName() + " bekommt:\n";
		for(int i = 0; i < tm.getProperty(currentPlayer).size(); i++) {
			if(tm.getProperty(currentPlayer).get(i) != null) {
				temp += propertyDetail(null, tm.getProperty(currentPlayer).get(i));
			}
		}
		if(temp.equals("")) {
			show += "-\n";
		} else {
			show += temp;
		}
		show += "" + tm.getMoney(currentPlayer) + " Geld.";
		System.out.println(show);
	}

	/**
	 * @param tm     The value determines the used TradeManager.
	 * @param player The value determines the player who is currently at turn.
	 * @param remove The value determines if the player wants to remove or add a PurchasableCircularList object.
	 * @return The return value is the number of which method shall be executed.
	 */
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

	/**
	 * The method asks what the player wants to do.
	 *
	 * @param tm     The value determines the used TradeManager.
	 * @param player The value determines the player who is currently at turn.
	 * @param remove The value determines if the player wants to remove or add a PurchasableCircularList object.
	 * @return The return value is the selected PurchasableCircularList object or null.
	 */
	private PurchasableCircularList showSelectProperty(TradeManager tm, Player player, boolean remove) {
		int choice;
		int[] options = buildSelectProperty(tm, player, remove);
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

	/**
	 * @param seller  The value determines if the menu is for the seller
	 * @param confirm The value determines if the menu is only to confirm the trade.
	 * @return The return value is the number of which method shall be executed.
	 */
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

	/**
	 * The method asks what the player wants to do.
	 *
	 * @param seller  The value determines if the menu is for the seller
	 * @param confirm The value determines if the menu is only to confirm the trade.
	 * @return The return value is the number of which method shall be executed.
	 */
	private int showHaggleMenu(boolean seller, boolean confirm) {
		int[] options = buildHaggleMenu(seller, confirm);
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
					menuOptions += "[" + (i + 1) + "] Handel abschliessen.\n";
					break;
				case 5:
					menuOptions += "[" + (i + 1) + "] Handel erbitten.\n";
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

	/**
	 * @param tm            The value determines the used TradeManager.
	 * @param currentPlayer The value determines the player who is currently at turn.
	 * @param counterPlayer The value determines the player who is not currently at turn.
	 * @param choice        The value determines the choice the player made.
	 * @param tradeState    The value determines the trade state.
	 * @return The return value is the updated trade state.
	 */
	private int haggleMenu(TradeManager tm, Player currentPlayer, Player counterPlayer, int choice, int tradeState) {
		PurchasableCircularList property;
		switch(choice) {
			case 1:
				showTrade(tm, counterPlayer, currentPlayer);
				break;
			case 2:
				property = showSelectProperty(tm, currentPlayer, false);
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
				property = showSelectProperty(tm, currentPlayer, true);
				if(currentPlayer != null) {
					tm.removeTrade(currentPlayer, property);
				}
				break;
			case 8:
				tradeState = 1;
				break;
			case 9:
				playerPropertiesMenu(counterPlayer);
				break;
		}
		return tradeState;
	}

	/**
	 * The method starts the trade.
	 *
	 * @param buyer        The value determines the player who is the one to buy something.
	 * @param playerVector The value determines the all playerVector
	 */
	private void tradeMenu(Player buyer, Vector<Player> playerVector) {
		int tradeState = 1;
		Player seller = showPlayer(buyer, playerVector);
		TradeManager tm = new TradeManager(buyer, seller);
		while(tradeState != 0) {
			System.out.println(buyer.getName() + " waehlen Sie:");
			while(tradeState != 0 && tradeState == 1) {
				tradeState = haggleMenu(tm, seller, buyer, showHaggleMenu(false, false), tradeState);
			}
			if(tradeState != 0) {
				System.out.println(seller.getName() + " waehlen Sie:");
			}
			while(tradeState != 0 && tradeState == 2) {
				tradeState = haggleMenu(tm, buyer, seller, showHaggleMenu(true, false), tradeState);
			}
			if(tradeState == 5) {
				System.out.println(buyer.getName() + " waehlen Sie:");
				while(tradeState != 0 && tradeState == 5) {
					tradeState = haggleMenu(tm, seller, buyer, showHaggleMenu(true, true), tradeState);
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

	/**
	 * @param player       The value determines the player who is currently at active.
	 * @param playerVector The value determines the all playerVector
	 * @param turnState    The value determines the current turn state.
	 * @return The return value is updated turn state.
	 */
	@Override
	public int mainMenu(Player player, Vector<Player> playerVector, int turnState) {
		boolean end = false;
		int choice = 0;
		PurchasableCircularList property;
		while(!end) {
			switch(choice) {
				case 0: //ShowActionMenu
					System.out.println(getTurnDetails(player, turnState == 0));
					choice = showActionMenu(player, turnState);
					break;
				case 1: //ShowOrganisationMenu
					System.out.println(getTurnDetails(player, turnState == 0));
					choice = showOrganisationMenu(player);
					break;
				case 2: //ViewProperty
					playerPropertiesMenu(player);
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
					property = showMortageMenu(player, true);
					if(property != null) {
						property.setInMortgage(true);
					}
					choice = 1;
					break;
				case 23: //Pay Mortgage
					property = showMortageMenu(player, false);
					if(property != null) {
						property.setInMortgage(false);
					}
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
	@Override
	public boolean showCardText(Card card) {
		System.out.println("\n" + card.getText() + "\n");
		if(card instanceof PayFineTakeCard) {
			println(((PayFineTakeCard) card).getOption());
			return in.userBoolean('j', 'n', ((PayFineTakeCard) card).getOption());
		}
		return true;
	}
}