package objects.value;

import java.util.HashMap;

/**
 * The class is used to send all Player's playerData to the clients
 */
public class InitializePlayer {
	private final HashMap<Integer, PlayerData> playerData;
	private final int                          userId;

	/**
	 * @param playerData the value determines the PlayerData of all players
	 * @param userId     the value determines the player id of the player of this client
	 */
	public InitializePlayer(HashMap<Integer, PlayerData> playerData, int userId) {
		this.playerData = playerData;
		this.userId = userId;
	}

	/**
	 * @return the return value is the PlayerData of all players
	 */
	public HashMap<Integer, PlayerData> getPlayerData() {
		return playerData;
	}

	/**
	 * @return the return value is the player id of the player of this client
	 */
	public int getUserId() {
		return userId;
	}
}

