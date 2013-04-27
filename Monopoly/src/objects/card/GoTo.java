package objects.card;

import objects.map.Field;

/**
 * GoTo is the go to card (Ruecke vor bis auf ...).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class GoTo extends Card {
	private final String  FIELDNAME;
	private final boolean OVERGO;
	private       Field   field;

	//JAVADOC
	public GoTo(String name, String text, String field, boolean overGo) {
		super(name, text);
		FIELDNAME = field;
		OVERGO = overGo;
	}

	//JAVADOC
	public void setField(Field field) {
		this.field = field;
	}

	//JAVADOC
	public String getFieldName() {
		return FIELDNAME;
	}
}
