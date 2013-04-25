package objects.map;
//JAVADOC

import objects.Player;

/**
 * @author Eyenseo
 * @version 0.1
 */
public abstract class Field {
	private final String NAME;

	/**
	 * @param name The value determines the name of the object.
	 */
	Field(String name) {
		this.NAME = name;
	}

	/**
	 * @return The return value is the name.
	 */
	public String getName() {
		return this.NAME;
	}

	//JAVADOC
	@Override
	public String toString() {
		return this.NAME;
	}

	//TODO this has to be replaced in each individual Method and set to abstract
	public void action(Player player) {
		System.err.println("This is a placeholder lalala~lalaLA");
	}
}
