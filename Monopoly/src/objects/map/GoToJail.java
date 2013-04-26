package objects.map;

import objects.Player;
import objects.exceptions.map.JustOneInstanceException;

//JAVADOC
public class GoToJail extends NotPurchasable {
	private static boolean justOneInstance = false;
	private Field jail;

	//JAVADOC
	public GoToJail(String name) throws JustOneInstanceException {
		super(name);
		if(justOneInstance) {
			throw new JustOneInstanceException(name);
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
