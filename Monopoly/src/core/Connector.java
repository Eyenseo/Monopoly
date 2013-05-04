package core;

import objects.card.*;
import objects.exceptions.core.CardConnectionException;
import objects.exceptions.core.NoInstanceException;
import objects.map.FieldCircularList;
import objects.map.notPurchasable.*;
import objects.map.purchasable.Facility;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.Station;
import objects.map.purchasable.Street;

import java.util.ArrayList;
import java.util.Collections;

//JAVADOC
public class Connector {
	//JAVADOC
	public void connect(FieldCircularList[] map, CardStack chance, CardStack community)
			throws NoInstanceException, CardConnectionException {
		makeCircularFieldList(map);
		connectMatching(arrayToArrayList(map), chance, community);
		setDiceArray(map);
		connectCards(chance, map, community);
		connectCards(community, map, community);
	}

	//JAVADOC
	private void makeCircularFieldList(FieldCircularList[] map) throws NoInstanceException {
		boolean jailField = false;
		boolean parkingField = false;
		FieldCircularList prev = map[0];
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
	private ArrayList<FieldCircularList> arrayToArrayList(FieldCircularList[] map) {
		ArrayList<FieldCircularList> fieldArrayList = new ArrayList<FieldCircularList>();
		Collections.addAll(fieldArrayList, map);
		return fieldArrayList;
	}

	//JAVADOC
	private void connectMatching(ArrayList<FieldCircularList> fieldArrayList, CardStack chance, CardStack community) {
		FieldCircularList a;
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
				if(a instanceof Street) {
					makeCircularStreetList((Street) a, fieldArrayList);
				} else if(a instanceof Station) {
					makeCircularStationList((Station) a, fieldArrayList);
				} else if(a instanceof Facility) {
					makeCircularFacilityList((Facility) a, fieldArrayList);
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
			} else if(a instanceof Jail) {
				connectJail((Jail) a, fieldArrayList, parking);
			}
		}
	}

	//JAVADOC
	private void makeCircularStreetList(Street street, ArrayList<FieldCircularList> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof Street && ((Street) fieldArrayList.get(i)).isSameColor(street)) {
				street.setNextGroupElement(((Street) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	//JAVADOC
	private void makeCircularStationList(Station station, ArrayList<FieldCircularList> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof Station) {
				station.setNextGroupElement(((Station) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	//JAVADOC
	private void makeCircularFacilityList(Facility facility, ArrayList<FieldCircularList> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof Facility) {
				facility.setNextGroupElement(((Facility) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	//JAVADOC
	private void connectTax(Tax tax, ArrayList<FieldCircularList> fieldArrayList, Parking parking) {
		if(parking == null) {
			for(FieldCircularList next : fieldArrayList) {
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
	private void connectJail(Jail jail, ArrayList<FieldCircularList> fieldArrayList, Parking parking) {
		if(parking == null) {
			for(FieldCircularList next : fieldArrayList) {
				if(next instanceof Parking) {
					jail.setParking((Parking) next);
					break;
				}
			}
		} else {
			jail.setParking(parking);
		}
	}

	//JAVADOC
	private void connectGoToJail(GoToJail goToJail, ArrayList<FieldCircularList> fieldArrayList, Jail jail) {
		if(jail == null) {
			for(FieldCircularList aFieldArrayList : fieldArrayList) {
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
	private void setDiceArray(FieldCircularList[] map) {
		int index;
		FieldCircularList[] diceArray;
		for(int i = 0; i < map.length; i++) {
			diceArray = new FieldCircularList[11];
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
	private void connectCards(CardStack current, FieldCircularList[] map, CardStack community)
			throws CardConnectionException {
		boolean found;
		for(Card c : current.getStack()) {
			if(c instanceof GoTo) {
				found = false;
				String name = ((GoTo) c).getFieldName();
				for(FieldCircularList f : map) {
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
				for(FieldCircularList f : map) {
					if(f instanceof Jail) {
						((Arrest) c).setJail(f);
					}
				}
			} else if(c instanceof PayFineTakeCard) {
				((PayFineTakeCard) c).setCommunity(community);
			} else if(c instanceof Jailbait) {
				((Jailbait) c).setCardStack(current);
			}
		}
	}
}
