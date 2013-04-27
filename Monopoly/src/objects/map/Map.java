package objects.map;

import objects.Player;
import objects.exceptions.core.NoInstanceException;
import objects.map.notPurchasable.Go;
import objects.map.notPurchasable.GoToJail;
import objects.map.notPurchasable.Jail;
import objects.map.notPurchasable.Parking;

import java.util.HashMap;

//JAVADOC
public class Map {
	private final Field[] MAP;
	private int                      goPosition       = -1;
	private int                      jailPosition     = -1;
	private int                      parkingPosition  = -1;
	private HashMap<String, Integer> fieldNameToIndex = new HashMap<String, Integer>();

	//JAVADOC
	public Map(Field[] map) throws NoInstanceException {
		this.MAP = map;
		GoToJail goToJail = null;
		for(int i = 0; i < map.length; i++) {
			this.fieldNameToIndex.put(map[i].toString(), i);
			if(map[i] instanceof Jail) {
				this.jailPosition = i;
			} else if(map[i] instanceof Go) {
				this.goPosition = i;
			} else if(map[i] instanceof Parking) {
				this.parkingPosition = i;
			} else if(map[i] instanceof GoToJail) {
				goToJail = (GoToJail) map[i];
			}
		}
		if(goPosition == -1) {
			throw new NoInstanceException("Go");
		} else if(jailPosition == -1) {
			throw new NoInstanceException("Jail");
		} else if(parkingPosition == -1) {
			throw new NoInstanceException("Parking");
		} else if(goToJail != null) {
			goToJail.setJail(map[jailPosition]);
		}
	}

	//JAVADOC
	public boolean movePlayer(Player player) {
		return movePlayer(player, player.throwDice(), true);
	}

	//JAVADOC
	public boolean movePlayer(Player player, int[] dices, boolean overGo) {
		boolean doubles = dices[0] == dices[1];
		if(!player.isInJail() || doubles) {
			int pos = fieldNameToIndex.get(player.getField().toString()) + dices[0] + dices[1];
			if(pos >= MAP.length) {
				pos -= MAP.length;
				if(overGo) {
					MAP[goPosition].action(player);
				}
			}
			player.setField(MAP[pos]);
		}
		return doubles;
	}

	//JAVADOC
	public void putInJail(Player player) {
		player.setField(MAP[jailPosition]);
		player.setInJail(true);
	}

	//JAVADOC
	public void setPlayerTo(Player player, String name) {
		setPlayerTo(player, name, true);
	}

	//JAVADOC
	public void setPlayerTo(Player player, String name, boolean overGo) {
		movePlayer(player, new int[]{0, fieldNameToIndex.get(name) - fieldNameToIndex.get(
				player.getField().toString())}, overGo);
	}

	public void setPlayerToStart(Player player) {
		player.setField(MAP[goPosition]);
	}
}
