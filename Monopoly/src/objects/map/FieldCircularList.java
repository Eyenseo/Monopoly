package objects.map;

import objects.Player;

//JAVADOC
public abstract class FieldCircularList {
	private int                 fieldNumber;
	private String              name;
	private FieldCircularList   next;
	private FieldCircularList   previous;
	private FieldCircularList[] diceArray;

	//JAVADOC
	public FieldCircularList(String name) {
		this.name = name;
		this.next = this;
		this.previous = this;
	}

	//JAVADOC
	public void setDiceArray(FieldCircularList[] diceArray) {
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
	public void add(FieldCircularList previous, int fieldNumber) {
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
	public FieldCircularList getNext() {
		return next;
	}

	//JAVADOC
	public int getFieldNumber() {
		return fieldNumber;
	}

	//JAVADOC
	public FieldCircularList getDiceNext(int index) {
		index -= 2;
		if(fieldNumber < diceArray[index].getFieldNumber() && diceArray[index].getFieldNumber() != 0) {
			return diceArray[index];
		}
		return null;
	}
}
