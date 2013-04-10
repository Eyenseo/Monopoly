package objects.value.card;

/**
 * Created with IntelliJ IDEA.
 * User: eyenseo
 * Date: 10.04.13
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
public abstract class Card {
	private final String NAME;
	private final String TEXT;

	Card(String name, String text) {
		this.NAME = name;
		this.TEXT = text;
	}

	abstract void action();

	public String getName() {
		return this.NAME;
	}

	public String getText() {
		return this.TEXT;
	}
}
