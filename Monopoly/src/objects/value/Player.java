package objects.value;

import objects.value.map.Field;

//TODO Doc
public class Player {
	private final String NAME;
	private       int    money;
	private       Field  position;

	public Player(String name, int money, Field position) {
		this.NAME = name;
		this.money = money;
		this.position = position;
	}
	// TODO:   Everything
}
