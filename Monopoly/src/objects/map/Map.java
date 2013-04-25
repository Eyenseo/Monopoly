package objects.map;

import objects.Player;

import java.util.HashMap;

//JAVADOC
public class Map {
	private final Field[] MAP;
	private       int     jailPosition;
	private HashMap<Player, Integer> playerPosition = new HashMap<Player, Integer>();

	//JAVADOC
	public Map(Field[] map) {
		this.MAP = map;
		for(int i = 0; i < map.length; i++) {
			if(map[i] instanceof Jail) {
				this.jailPosition = i;
				break;
			}
		}
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
			MAP[pos].action(player);
		}
		return doubles;
	}

	//JAVADOC
	public Field getField(Player player) {
		return MAP[playerPosition.get(player)];
	}

	//JAVADOC
	public void putInJail(Player player) {
		playerPosition.put(player, jailPosition);
		player.setInJail(true);
	}
}
