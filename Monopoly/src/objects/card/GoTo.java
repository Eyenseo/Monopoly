package objects.card;

/**
 * GoTo is the go to card (Ruecke vor bis auf ...).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class GoTo extends Card {
	private final String  FIELD;
	private final boolean OVERGO;

	//JAVADOC
	public GoTo(String name, String text, String field, boolean overGo) {
		super(name, text);
		FIELD = field;
		OVERGO = overGo;
	}
}
