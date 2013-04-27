package objects.map.notPurchasable;

import objects.exceptions.core.MoreThanOneInstanceException;

//JAVADOC
public class Jail extends NotPurchasable {
	private static boolean justOneInstance = false;

	//JAVADOC
	public Jail(String name) throws MoreThanOneInstanceException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneInstanceException(name);
		}
		Jail.justOneInstance = true;
	}
}
