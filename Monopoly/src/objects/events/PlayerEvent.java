package objects.events;

import objects.Player;

import java.util.EventObject;

//JAVADOC
public class PlayerEvent extends EventObject {
	//JAVADOC
	public PlayerEvent(Player source) {
		super(source);
	}
}
