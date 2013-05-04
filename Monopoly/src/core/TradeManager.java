package core;

import objects.Player;
import objects.map.purchasable.PurchasableCircularList;

import java.util.HashMap;
import java.util.Vector;

//JAVADOC
public class TradeManager {
	private HashMap<Player, Vector<PurchasableCircularList>> property =
			new HashMap<Player, Vector<PurchasableCircularList>>();
	private HashMap<Player, Integer>                         money    = new HashMap<Player, Integer>();

	//JAVADOC
	public TradeManager(Player buyer, Player seller) {
		property.put(buyer, new Vector<PurchasableCircularList>());
		property.put(seller, new Vector<PurchasableCircularList>());
		money.put(seller, 0);
		money.put(buyer, 0);
	}

	//JAVADOC
	public void addTrade(Player player, PurchasableCircularList property) {
		this.property.get(player).addElement(property);
	}

	//JAVADOC
	public void addTrade(Player player, Integer money) {
		this.money.put(player, money);
	}

	//JAVADOC
	public void removeTrade(Player player, PurchasableCircularList property) {
		if(this.property.get(player).contains(property)) {
			this.property.get(player).remove(property);
		}
	}

	//JAVADOC
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
				player[1].setOwnship(v);
			}
		}
		vector = property.get(player[1]);
		for(PurchasableCircularList v : vector) {
			if(v != null) {
				player[0].setOwnship(v);
			}
		}
		player[0].addMoney(money.get(player[0]));
		player[1].addMoney(money.get(player[1]));
		player[0].pay(money.get(player[1]));
		player[1].pay(money.get(player[0]));
		return true;
	}

	//JAVADOC
	public boolean isInTrade(PurchasableCircularList property) {
		for(Vector<PurchasableCircularList> v : this.property.values()) {
			if(v.contains(property)) {
				return true;
			}
		}
		return false;
	}

	//JAVADOC
	public Vector<PurchasableCircularList> getProperty(Player player) {
		if(property.containsKey(player)) {
			return property.get(player);
		}
		return null;
	}

	//JAVADOC
	public int getMoney(Player player) {
		return money.get(player);
	}
}
