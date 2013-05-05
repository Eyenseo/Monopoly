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

/**
 * The Connector class connects the FieldCircularList objects and the CardStack objects.
 *
 * @author Eyenseo
 * @version 1
 */
public class Connector {
	/**
	 * @param map       The value determines the Array of FieldCircularList objects that shall be connected.
	 * @param chance    The value determines the CardStack which has the Card objects from the chance file.
	 * @param community The value determines the CardStack which has the Card objects from the community file.
	 * @throws NoInstanceException     The Exception is thrown if either no Jail, Parking, Go object exists.
	 * @throws CardConnectionException The Exception is thrown if the Street for the GoTo Card is not found.
	 */
	public void connect(FieldCircularList[] map, CardStack chance, CardStack community)
			throws NoInstanceException, CardConnectionException {
		makeCircularFieldList(map);
		connectMatching(arrayToArrayList(map), chance, community);
		setDiceArray(map);
		connectCards(chance, map, community);
		connectCards(community, map, community);
	}

	/**
	 * The method connects the FieldCircularList objects with each other.
	 *
	 * @param map The value determines the Array of FieldCircularList objects that shall be connected.
	 * @throws NoInstanceException The Exception is thrown if either no Jail, Parking, Go object exists.
	 */
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

	/**
	 * @param map The value determines the Array of FieldCircularList objects that shall be connected.
	 * @return The return value is a ArrayList with all elements of the map parameter.
	 */
	private ArrayList<FieldCircularList> arrayToArrayList(FieldCircularList[] map) {
		ArrayList<FieldCircularList> fieldArrayList = new ArrayList<FieldCircularList>();
		Collections.addAll(fieldArrayList, map);
		return fieldArrayList;
	}

	/**
	 * The method connects the
	 * <ul>
	 * <li>Street objects of one color</li>
	 * <li>Station objects</li>
	 * <li>Facility objects</li>
	 * <li>Chance objects with the chance CardStack</li>
	 * <li>Community objects with the community CardStack</li>
	 * <li>Tax objects with Parking object</li>
	 * <li>GoToJail objects with Jail object</li>
	 * <li>Jail object with Parking object</li>
	 * </ul>
	 *
	 * @param fieldArrayList The value determines the ArrayList of FieldCircularList objects that shall be connected.
	 * @param chance         The value determines the CardStack which has the Card objects from the chance file.
	 * @param community      The value determines the CardStack which has the Card objects from the community file.
	 */
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

	/**
	 * The method connects all Street objects of the same color.
	 *
	 * @param street         The value determines the Street object to check against and connect with.
	 * @param fieldArrayList The value determines the FieldCircularList objects to check.
	 */
	private void makeCircularStreetList(Street street, ArrayList<FieldCircularList> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof Street && ((Street) fieldArrayList.get(i)).isSameColor(street)) {
				street.setNextGroupElement(((Street) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	/**
	 * The method connects all Station objects.
	 *
	 * @param station        The value determines the Station object to check against and connect with.
	 * @param fieldArrayList The value determines the FieldCircularList objects to check.
	 */
	private void makeCircularStationList(Station station, ArrayList<FieldCircularList> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof Station) {
				station.setNextGroupElement(((Station) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	/**
	 * The method connects all Facility objects.
	 *
	 * @param facility       The value determines the Facility object to check against and connect with.
	 * @param fieldArrayList The value determines the FieldCircularList objects to check.
	 */
	private void makeCircularFacilityList(Facility facility, ArrayList<FieldCircularList> fieldArrayList) {
		for(int i = 0; i < fieldArrayList.size(); i++) {
			if(fieldArrayList.get(i) instanceof Facility) {
				facility.setNextGroupElement(((Facility) fieldArrayList.get(i)));
				fieldArrayList.remove(i);
				i--;
			}
		}
	}

	/**
	 * The method connects Tax objects with the Parking object.
	 *
	 * @param tax            The value determines the Tax object to check against and connect.
	 * @param fieldArrayList The value determines the FieldCircularList objects to check.
	 */
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

	/**
	 * The method connects Jail objects with the Parking object.
	 *
	 * @param jail           The value determines the Jail object to check against and connect.
	 * @param fieldArrayList The value determines the FieldCircularList objects to check.
	 */
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

	/**
	 * The method connects GoToJail objects with the Jail object.
	 *
	 * @param goToJail       The value determines the GoToJail object to check against and connect.
	 * @param fieldArrayList The value determines the FieldCircularList objects to check.
	 */
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

	/**
	 * The method sets the diceArray for the FieldCircularList objects. The array is for the next second to twelves FieldCircularList object after this.
	 *
	 * @param map The value determines the FieldCircularList objects set the array for.
	 */
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

	/**
	 * @param current   The value determines the current CardStack.
	 * @param map       The value determines the FieldCircularList objects to check.
	 * @param community The value determines the community CardStack.
	 * @throws CardConnectionException The Exception is thrown if the Street for the GoTo Card is not found.
	 */
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
