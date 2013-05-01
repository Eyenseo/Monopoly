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
	public void connect(Field[] map, CardStack chance, CardStack community)
			throws NoInstanceException, CardConnectionException {
		makeCircularFieldList(map);
		connectMatching(arrayToArrayList(map), chance, community);
		setDiceArray(map);
		connectCards(chance.toArray(), map, community);
		connectCards(community.toArray(), map, community);
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
		Field a;
		Parking parking = null;
		Jail jail = null;
		while(!fieldArrayList.isEmpty()) {
			a = fieldArrayList.get(0);
			if(a instanceof Parking) {
				parking = (Parking) a;
			} else if(a instanceof Jail) {
				jail = (Jail) a;
			}
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
			} else if(a instanceof Tax) {
				connectTax((Tax) a, fieldArrayList, parking);
			} else if(a instanceof GoToJail) {
				connectGoToJail((GoToJail) a, fieldArrayList, jail);
			}
		}
	}

	//JAVADOC
	private void makeCircularStreetList(StreetCircularList street, ArrayList<Field> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof StreetCircularList &&
			   ((StreetCircularList) fieldArrayList.get(i)).isSameColor(street)) {
				street.setNextGroupElement(((StreetCircularList) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	//JAVADOC
	private void makeCircularStationList(StationCircularList station, ArrayList<Field> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof StationCircularList) {
				station.setNextGroupElement(((StationCircularList) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	//JAVADOC
	private void makeCircularFacilityList(FacilityCircularList facility, ArrayList<Field> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof FacilityCircularList) {
				facility.setNextGroupElement(((FacilityCircularList) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	//JAVADOC
	private void connectTax(Tax tax, ArrayList<Field> fieldArrayList, Parking parking) {
		if(parking == null) {
			for(Field next : fieldArrayList) {
				if(next instanceof Parking) {
					tax.setParking((Parking) next);
					break;
				}
			}
		} else {
			tax.setParking(parking);
		}
	}

	//JAVADOC
	private void connectGoToJail(GoToJail goToJail, ArrayList<Field> fieldArrayList, Jail jail) {
		if(jail == null) {
			for(Field aFieldArrayList : fieldArrayList) {
				if(aFieldArrayList instanceof Jail) {
					goToJail.setJail(aFieldArrayList);
					break;
				}
			}
		} else {
			goToJail.setJail(jail);
		}
	}

	//JAVADOC
	private void setDiceArray(Field[] map) {
		int index;
		Field[] diceArray;
		for(int i = 0; i < map.length; i++) {
			diceArray = new Field[11];
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
						((GoTo) c).setGo(map[0]);
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
