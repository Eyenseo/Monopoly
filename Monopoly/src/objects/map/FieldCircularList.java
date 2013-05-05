package objects.map;

import objects.Player;

/**
 * The structure of FieldCircularList and the subclasses is a circular list.
 *
 * @author Eyenseo
 * @version 1
 */
public abstract class FieldCircularList {
	private int                 fieldNumber;
	private String              name;
	private FieldCircularList   next;
	private FieldCircularList   previous;
	private FieldCircularList[] diceArray;

	/**
	 * @param name The value determines the name of the Field.
	 */
	public FieldCircularList(String name) {
		this.name = name;
		this.next = this;
		this.previous = this;
	}

	/**
	 * @param diceArray The value determines the diceArray.
	 */
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

	/**
	 * @param previous    The value determines the previous FieldCircularList object in the circular list.
	 * @param fieldNumber The value determines the number of the FieldCircularList object in the circular list.
	 */
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

	/**
	 * @return The return value is the next FieldCircularList object in the circular list.
	 */
	public FieldCircularList getNext() {
		return next;
	}

	/**
	 * @return The return value is the field number.
	 */
	public int getFieldNumber() {
		return fieldNumber;
	}

	/**
	 * @param index The value determines the amount of fields that have to be gone.
	 * @return The return value is the FieldCircularList object index times forward, if the index is to high or negative the return value is null.
	 */
	public FieldCircularList getDiceNext(int index) {
		index -= 2;
		if(fieldNumber < diceArray[index].getFieldNumber() && diceArray[index].getFieldNumber() != 0) {
			return diceArray[index];
		}
		return null;
	}
}
