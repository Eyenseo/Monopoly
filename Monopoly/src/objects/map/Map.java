package objects.map;

import objects.Bank;
import objects.Player;

import java.util.HashMap;

//JAVADOC
public class Map {
	private final Field[] MAP;
	private final Bank    BANK;
	private HashMap<Player, Integer> playerPosition = new HashMap<Player, Integer>();

	//JAVADOC
	public Map(Field[] map, Bank bank) {
		this.MAP = map;
		this.BANK = bank;
	}

	//JAVADOC
	public void addPlayer(Player player) {
		this.playerPosition.put(player, 0);
	}

	//JAVADOC
	public void addPlayer(Player player, int position) {
		this.playerPosition.put(player, position);
	}

	//JAVADOC
	public boolean movePlayer(Player player) {
		int[] dices = player.throwDice();
		boolean doubles = dices[0] == dices[1];
		if(!player.isInJail() || doubles) {
			int pos = playerPosition.get(player) + dices[0] + dices[1];
			if(pos >= MAP.length) {
				pos -= MAP.length;
				player.addMoney(4000);
			}
			playerPosition.put(player, pos);
		}
		return doubles;
	}

	//JAVADOC
	public Field getField(Player player) {
		return MAP[playerPosition.get(player)];
	}
}
