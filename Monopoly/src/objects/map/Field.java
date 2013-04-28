package objects.map;

import objects.Player;

//JAVADOC
public abstract class Field {
	private int     fieldNumber;
	private String  name;
	private Field   next;
	private Field   previous;
	private Field[] diceArray;

	//JAVADOC
	public Field(String name) {
		this.name = name;
		this.next = this;
		this.previous = this;
	}

	//JAVADOC
	public void setDiceArray(Field[] diceArray) {
		this.diceArray = diceArray;
	}

	/**
	 * @return The return value is the name.
	 */
	public String getName() {
		return this.name;
	}

	//JAVADOC
	public abstract void action(Player player);

	//JAVADOC
	public void add(Field previous, int fieldNumber) {
		this.fieldNumber = fieldNumber;
		if(previous.previous.equals(previous)) {
			previous.next = this;
			previous.previous = this;
			this.next = previous;
		} else {
			this.next = previous.next;
			this.next.previous = this;
			previous.next = this;
		}
		this.previous = previous;
	}

	//JAVADOC
	public Field getNext() {
		return next;
	}

	//JAVADOC
	public int getFieldNumber() {
		return fieldNumber;
	}

	//JAVADOC
	public Field getDiceNext(int index) {
		index -= 2;
		if(fieldNumber < diceArray[index].getFieldNumber() && diceArray[index].getFieldNumber() != 0) {
			return diceArray[index];
		}
		return null;
	}
}
