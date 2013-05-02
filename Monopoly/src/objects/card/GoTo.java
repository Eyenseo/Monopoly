package objects.card;

import objects.map.FieldCircularList;

/**
 * GoTo is the go to card (Ruecke vor bis auf ...).
 * A card is a trigger for a specific event defined in the gameplay mechanics
 */
public class GoTo extends Card {
	private final String            FIELDNAME;
	private final boolean           OVERGO;
	private       FieldCircularList field;
	private       FieldCircularList go;

	//JAVADOC
	public GoTo(String name, String text, String field, boolean overGo) {
		super(name, text);
		FIELDNAME = field;
		OVERGO = overGo;
	}

	//JAVADOC
	public void setField(FieldCircularList field) {
		this.field = field;
	}

	//JAVADOC
	public String getFieldName() {
		return FIELDNAME;
	}

	//JAVADOC
	public void setGo(FieldCircularList field) {
		go = field;
	}
}
