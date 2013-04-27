package objects.map.notPurchasable;

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
}
