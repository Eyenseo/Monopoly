package ui;

import objects.Player;
import objects.map.purchasable.PurchasableCircularList;

import java.util.HashMap;
import java.util.Vector;

/**
 * The TradeManager stores the information about a trade between two player.
 *
 * @author Eyenseo
 * @version 1
 */
public class TradeManager {
	private HashMap<Player, Vector<PurchasableCircularList>> property =
			new HashMap<Player, Vector<PurchasableCircularList>>();
	private HashMap<Player, Integer>                         money    = new HashMap<Player, Integer>();

	/**
	 * @param buyer  The value determines the Player who wants to buy a PurchasableCircularList.
	 * @param seller The value determines the Player who is asked to sell or trade a PurchasableCircularList.
	 */
	public TradeManager(Player buyer, Player seller) {
		property.put(buyer, new Vector<PurchasableCircularList>());
		property.put(seller, new Vector<PurchasableCircularList>());
		money.put(seller, 0);
		money.put(buyer, 0);
	}

	/**
	 * @param player   The value determines the Player who wants a PurchasableCircularList.
	 * @param property The value determines the PurchasableCircularList to get of the other player.
	 */
	public void addTrade(Player player, PurchasableCircularList property) {
		this.property.get(player).addElement(property);
	}

	/**
	 * @param player The value determines the Player who wants a PurchasableCircularList.
	 * @param money  The value determines the amount of money to get of the other player.
	 */
	public void addTrade(Player player, Integer money) {
		this.money.put(player, money);
	}

	/**
	 * @param player   The value determines the Player who wants a  of the other player.
	 * @param property The value determines the PurchasableCircularList of the other player to remove from the trade.
	 */
	public void removeTrade(Player player, PurchasableCircularList property) {
		if(this.property.get(player).contains(property)) {
			this.property.get(player).remove(property);
		}
	}

	/**
	 * The method adds/removes the chosen amount of money to the players and sets the PurchasableCircularList object to the new owner.
	 *
	 * @return The return value is true if both player have enough money for the trade, false if not
	 */
	public boolean deal() {
		Player[] player = new Player[2];
		Vector<PurchasableCircularList> vector;
		money.keySet().toArray(player);
		if(player[0].getMoney() + money.get(player[0]) < money.get(player[1]) &&
		   player[1].getMoney() + money.get(player[1]) < money.get(player[0])) {
			return false;
		}
		vector = property.get(player[0]);
		for(PurchasableCircularList v : vector) {
			if(v != null) {
				player[1].setOwnership(v);
			}
		}
		vector = property.get(player[1]);
		for(PurchasableCircularList v : vector) {
			if(v != null) {
				player[0].setOwnership(v);
			}
		}
		player[0].addMoney(money.get(player[0]));
		player[1].addMoney(money.get(player[1]));
		player[0].pay(money.get(player[1]));
		player[1].pay(money.get(player[0]));
		return true;
	}

	/**
	 * @param property The value determines the PurchasableCircularList object to check against.
	 * @return The return value is true if the PurchasableCircularList object is already subject to the trade.
	 */
	public boolean isInTrade(PurchasableCircularList property) {
		for(Vector<PurchasableCircularList> v : this.property.values()) {
			if(v.contains(property)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param player The value determines the the Player to get the information from.
	 * @return The return value is a Vector of PurchasableCircularList objects that the player will get by the trade.
	 */
	public Vector<PurchasableCircularList> getProperty(Player player) {
		if(property.containsKey(player)) {
			return property.get(player);
		}
		return null;
	}

	/**
	 * @param player The value determines the the Player to get the information from.
	 * @return The return value is a the money that the player will get by the trade.
	 */
	public int getMoney(Player player) {
		return money.get(player);
	}
}
