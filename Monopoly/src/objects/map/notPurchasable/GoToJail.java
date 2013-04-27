package objects.map.notPurchasable;

import objects.Player;
import objects.exceptions.core.MoreThanOneInstanceException;
import objects.map.Field;

//JAVADOC
public class GoToJail extends NotPurchasable {
	private static boolean justOneInstance = false;
	private Field jail;

	//JAVADOC
	public GoToJail(String name) throws MoreThanOneInstanceException {
		super(name);
		if(justOneInstance) {
			throw new MoreThanOneInstanceException(name);
		}
		GoToJail.justOneInstance = true;
	}

	//JAVADOC
	public void setJail(Field field) {
		this.jail = field;
	}

	@Override
	//JAVADOC
	public void action(Player player) {
		player.setField(jail);
	}
}
