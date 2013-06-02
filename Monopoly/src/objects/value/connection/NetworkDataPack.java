package objects.value.connection;

import objects.value.connection.action.ActionData;
import objects.value.connection.data.PlayerData;
import objects.value.connection.data.map.FieldData;

//JAVADOC
public class NetworkDataPack {
	PlayerData playerData;
	FieldData  fieldData;
	ActionData actionData;

	//JAVADOC
	public NetworkDataPack(PlayerData playerData) {
		this.playerData = playerData;
	}

	//JAVADOC
	public NetworkDataPack(FieldData fieldData) {
		this.fieldData = fieldData;
	}

	//JAVADOC
	public NetworkDataPack(ActionData actionData) {
		this.actionData = actionData;
	}

	//JAVADOC
	public PlayerData getPlayerData() {
		return playerData;
	}

	//JAVADOC
	public FieldData getFieldData() {
		return fieldData;
	}

	//JAVADOC
	public ActionData getActionData() {
		return actionData;
	}
}
