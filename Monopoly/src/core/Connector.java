package core;

import objects.card.*;
import objects.exceptions.core.CardConnectionException;
import objects.exceptions.core.NoInstanceException;
import objects.map.Field;
import objects.map.notPurchasable.*;
import objects.map.purchasable.FacilityCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.StationCircularList;
import objects.map.purchasable.StreetCircularList;

import java.util.ArrayList;
import java.util.Collections;

//JAVADOC
public class Connector {

	//JAVADOC
	public Field connect(Field[] map, CardStack chance, CardStack community)
			throws NoInstanceException, CardConnectionException {
		makeCircularFieldList(map);
		connectMatching(arrayToArrayList(map), chance, community);
		setDiceArray(map);
		connectCards(chance.toArray(), map, community);
		connectCards(community.toArray(), map, community);
		return map[0];
	}

	//JAVADOC
	private void makeCircularFieldList(Field[] map) throws NoInstanceException {
		boolean jailField = false;
		boolean parkingField = false;
		Field prev = map[0];
		if(prev instanceof Go) {
			for(int i = 1; i < map.length; i++) {
				map[i].add(prev, i);
				prev = map[i];
				if(map[i] instanceof Jail) {
					jailField = true;
				} else if(map[i] instanceof Parking) {
					parkingField = true;
				}
			}
			if(!jailField) {
				throw new NoInstanceException("Jail");
			} else if(!parkingField) {
				throw new NoInstanceException("Parking");
			}
		} else {
			throw new NoInstanceException("Go");
		}
	}

	//JAVADOC
	private ArrayList<Field> arrayToArrayList(Field[] map) {
		ArrayList<Field> fieldArrayList = new ArrayList<Field>();
		Collections.addAll(fieldArrayList, map);
		return fieldArrayList;
	}

	//JAVADOC
	private void connectMatching(ArrayList<Field> fieldArrayList, CardStack chance, CardStack community) {
		Field a = fieldArrayList.get(0);
		fieldArrayList.remove(0);
		if(a instanceof PurchasableCircularList) {
			if(a instanceof StreetCircularList) {
				makeCircularStreetList((StreetCircularList) a, fieldArrayList);
			} else if(a instanceof StationCircularList) {
				makeCircularStationList((StationCircularList) a, fieldArrayList);
			} else if(a instanceof FacilityCircularList) {
				makeCircularFacilityList((FacilityCircularList) a, fieldArrayList);
			}
		} else if(a instanceof CardField) {
			if(a instanceof Chance) {
				((CardField) a).setCardStack(chance);
			} else {
				((CardField) a).setCardStack(community);
			}
		} else if(a instanceof GoToJail) {
			connectGoToJail((GoToJail) a, fieldArrayList);
		}
		if(!fieldArrayList.isEmpty()) {
			connectMatching(fieldArrayList, chance, community);
		}
	}

	//JAVADOC
	private void makeCircularStreetList(StreetCircularList street, ArrayList<Field> fieldArrayList) {
		Field next;
		for(int i = 0; i < fieldArrayList.size(); i++) {
			next = fieldArrayList.get(i);
			if(next instanceof StreetCircularList && ((StreetCircularList) next).isSameColor(street)) {
				((StreetCircularList) next).setNextGroupElement(street);
				fieldArrayList.remove(i);
			}
		}
	}

	//JAVADOC
	private void makeCircularStationList(StationCircularList station, ArrayList<Field> fieldArrayList) {
		Field next;
		for(int i = 0; i < fieldArrayList.size(); i++) {
			next = fieldArrayList.get(i);
			if(next instanceof StationCircularList) {
				((StationCircularList) next).setNextGroupElement(station);
				fieldArrayList.remove(i);
			}
		}
	}

	//JAVADOC
	private void makeCircularFacilityList(FacilityCircularList facility, ArrayList<Field> fieldArrayList) {
		Field next;
		for(int i = 0; i < fieldArrayList.size(); i++) {
			next = fieldArrayList.get(i);
			if(next instanceof FacilityCircularList) {
				((FacilityCircularList) next).setNextGroupElement(facility);
				fieldArrayList.remove(i);
			}
		}
	}

	//JAVADOC
	private void connectGoToJail(GoToJail goToJail, ArrayList<Field> fieldArrayList) {
		Field next;
		for(int i = 0; i < fieldArrayList.size(); i++) {
			next = fieldArrayList.get(i);
			if(next instanceof Jail) {
				goToJail.setJail(next);
				fieldArrayList.remove(i);
			}
		}
	}

	//JAVADOC
	private void setDiceArray(Field[] map) {
		int index;
		Field[] diceArray = new Field[12];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < diceArray.length; j++) {
				index = i + 2 + j;
				if(index >= map.length) {
					index -= map.length;
				}
				diceArray[j] = map[index];
			}
			map[i].setDiceArray(diceArray);
		}
	}

	//JAVADOC
	private void connectCards(Card[] cardArray, Field[] map, CardStack community) throws CardConnectionException {
		boolean found;
		for(Card c : cardArray) {
			if(c instanceof GoTo) {
				found = false;
				String name = ((GoTo) c).getFieldName();
				for(Field f : map) {
					if(f.getName().equals(name)) {
						((GoTo) c).setField(f);
						found = true;
					}
				}
				if(!found) {
					throw new CardConnectionException(c);
				}
			} else if(c instanceof Arrest) {
				for(Field f : map) {
					if(f instanceof Jail) {
						((Arrest) c).setJail(f);
					}
				}
			} else if(c instanceof PayFineTakeCard) {
				((PayFineTakeCard) c).setCommunity(community);
			}
		}
	}
}
