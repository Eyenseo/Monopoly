package objects.value.map;

/**
 * Created with IntelliJ IDEA.
 * User: Eyenseo
 * Date: 26.03.13
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
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
