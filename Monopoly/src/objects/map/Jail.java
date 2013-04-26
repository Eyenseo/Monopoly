package objects.map;

import objects.exceptions.map.JustOneInstanceException;

//JAVADOC
public class Jail extends NotPurchasable {
	private static boolean justOneInstance = false;

	//JAVADOC
	public Jail(String name) throws JustOneInstanceException {
		super(name);
		if(justOneInstance) {
			throw new JustOneInstanceException(name);
		}
		Jail.justOneInstance = true;
	}
}
