package objects.value;

import objects.value.map.FieldData;

import java.util.ArrayList;

/**
 * InitializeMapData holds the data to draw the map
 */
public class InitializeMapData {
	private final ArrayList<FieldData> map;

	/**
	 * @param map the value determines the map
	 */
	public InitializeMapData(ArrayList<FieldData> map) {
		this.map = map;
	}

	/**
	 * @return the return value is the map
	 */
	public ArrayList<FieldData> getMap() {
		return map;
	}
}
