package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;

import java.io.Serializable;

/**
 * The Community Class is the FieldCircularList subclass.
 *
 * @author Eyenseo
 * @version 1
 */
public class GoToJail extends NotPurchasable implements Serializable {
	private static final long    serialVersionUID = 2310428836358458471L;
	private static       boolean justOneInstance  = false;
	private Jail jail;

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
	 * @param jail The value determines the Jail object.
	 */
	public void setJail(Jail jail) {
		this.jail = jail;
	}

	@Override
	//JAVADOC
	public void action(Player player) {
		player.setPosition(jail);
		player.setInJail(true);
	}
}
