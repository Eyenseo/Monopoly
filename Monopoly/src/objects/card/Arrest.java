package objects.card;

import objects.map.FieldCircularList;
//JAVADOC

/**
 * Arrest is the jail card.
 *
 * @version 1
 */
public class Arrest extends Card {
	FieldCircularList jail;

	/**
	 * @param name The value determines the name of the Card.
	 * @param text The value determines the text of the Card.
	 */
	public Arrest(String name, String text) {
		super(name, text);
	}

	/**
	 * @param jail The value determines the FieldCircularList object that is the Jail.
	 */
	public void setJail(FieldCircularList jail) {
		this.jail = jail;
	}
}
