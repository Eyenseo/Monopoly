package objects.events;

import objects.value.PlayerData;

import java.util.EventObject;

/**
 * The ClientOperatorPlayerDataEvent is thrown at the client if there is new PlayerData
 */
public class ClientOperatorPlayerDataEvent extends EventObject {
	private final PlayerData playerData;

	/**
	 * @param source     the value determines the source of the event
	 * @param playerData the value determines the PlayerData of the event
	 */
	public ClientOperatorPlayerDataEvent(Object source, PlayerData playerData) {
		super(source);
		this.playerData = playerData;
	}

	/**
	 * @return the return value is the PlayerData of the event
	 */
	public PlayerData getPlayerData() {
		return playerData;
	}
}
