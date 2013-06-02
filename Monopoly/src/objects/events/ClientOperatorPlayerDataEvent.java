package objects.events;

import objects.value.connection.data.PlayerData;

import java.util.EventObject;

//JAVADOC
public class ClientOperatorPlayerDataEvent extends EventObject {
	private PlayerData playerData;

	//JAVADOC
	public ClientOperatorPlayerDataEvent(Object source, PlayerData playerData) {
		super(source);
		this.playerData = playerData;
	}

	//JAVADOC
	public PlayerData getPlayerData() {
		return playerData;
	}
}
