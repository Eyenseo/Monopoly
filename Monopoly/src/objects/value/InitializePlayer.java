package objects.value;

import java.util.ArrayList;

public class InitializePlayer {
	private final ArrayList<PlayerData> playerData;
	private final int                   playerId;

	/**
	 * @param playerData the value determines the PlayerData of all players
	 * @param playerId   the value determines the player id of the player of this client
	 */
	public InitializePlayer(ArrayList<PlayerData> playerData, int playerId) {
		this.playerData = playerData;
		this.playerId = playerId;
	}

	/**
	 * @return the return value is the PlayerData of all players
	 */
	public ArrayList<PlayerData> getPlayerData() {
		return playerData;
	}

	/**
	 * @return the return value is the player id of the player of this client
	 */
	public int getPlayerId() {
		return playerId;
	}
}

