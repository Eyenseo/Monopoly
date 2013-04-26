package objects.map;

import objects.card.CardStack;

import java.util.Vector;

//JAVADOC
public class MapConnector {

	//JAVADOC
	public Field make(Field[] map) {
		connectAllFields(map);
		connetGroups(map);
		setDiceArray(map);
		return map[0];
	}

	//JAVADOC
	public Field make(Field[] map, CardStack event, CardStack community) {
		connectAllFields(map);
		connetGroups(map);
		return map[0];
	}

	//JAVADOC
	private void connectAllFields(Field[] map) {
		Field prev = map[0];
		for(int i = 1; i < map.length; i++) {
			map[i].add(prev);
			prev = map[i];
		}
	}

	//JAVADOC
	private void connetGroups(Field[] map) {
		Vector<Field> fieldVector = new Vector<Field>();
		for(Field f : map) {
			fieldVector.addElement(f);
		}
		connetGroups(fieldVector);
	}

	//JAVADOC
	private void connetGroups(Vector<Field> fieldVector) {
		Field a = fieldVector.firstElement();
		fieldVector.remove(fieldVector.indexOf(a));
		if(a instanceof PurchasableCircularList) {
			if(a instanceof StreetCircularList) {
				for(Field b : fieldVector) {
					if(b instanceof StreetCircularList && ((StreetCircularList) b).isSameColor(
							(StreetCircularList) a)) {
						((PurchasableCircularList) b).setNextGroupElement((PurchasableCircularList) a);
					}
				}
			} else if(a instanceof StationCircularList) {
				for(Field b : fieldVector) {
					if(b instanceof StationCircularList) {
						((PurchasableCircularList) b).setNextGroupElement((PurchasableCircularList) a);
					}
				}
			} else if(a instanceof FacilityCircularList) {
				for(Field b : fieldVector) {
					if(b instanceof FacilityCircularList) {
						((PurchasableCircularList) b).setNextGroupElement((PurchasableCircularList) a);
					}
				}
			}
		}
		if(!fieldVector.isEmpty()) {
			connetGroups(fieldVector);
		}
	}

	//JAVADOC
	private void setDiceArray(Field[] map) {
	}
}
