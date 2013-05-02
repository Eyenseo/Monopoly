package objects.card;

import objects.map.FieldCircularList;

/**
 * Arrest is the jail card (Gehe ins Gefaengnis Karte).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class Arrest extends Card {
	FieldCircularList jail;

	/**
	 * @param name Name of the card
	 * @param text Text of the card
	 */
	public Arrest(String name, String text) {
		super(name, text);
	}

	//JAVADOC
	public void setJail(FieldCircularList jail) {
		this.jail = jail;
	}
}
