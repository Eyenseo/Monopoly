package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.data.MoreThanOneDataSetException;

//JAVADOC
public class Jail extends NotPurchasable {
	private static boolean justOneInstance = false;

	//JAVADOC
	public Jail(String name) throws MoreThanOneDataSetException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneDataSetException(name);
		}
		Jail.justOneInstance = true;
	}

	@Override
	public void action(Player player) {
		//TODO Check if here has something to be done ... ?
	}
}
