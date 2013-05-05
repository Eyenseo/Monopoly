package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;
import objects.map.FieldCircularList;

/**
 * The Community Class is the FieldCircularList subclass.
 *
 * @author Eyenseo
 * @version 1
 */
public class GoToJail extends NotPurchasable {
	private static boolean justOneInstance = false;
	private FieldCircularList jail;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public GoToJail(String name) throws MoreThanOneDataSetException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneDataSetException(name);
		}
		GoToJail.justOneInstance = true;
	}

	/**
	 * @param field The value determines the Jail FieldCircularList object.
	 */
	//TODO change to Jail
	public void setJail(FieldCircularList field) {
		this.jail = field;
	}

	@Override
	//JAVADOC
	public void action(Player player) {
		player.setField(jail);
	}
}
