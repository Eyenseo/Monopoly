package objects.map;

import objects.Player;
import objects.value.map.FieldData;

import java.io.Serializable;

/**
 * The structure of FieldCircularList and the subclasses is a circular list.
 */
public abstract class FieldCircularList implements Serializable {
	private static final long serialVersionUID = -5568733184014294625L;
	private static  int                 number;
	protected final int                 FIELDNUMBER;
	protected final String              NAME;
	private         FieldCircularList   next;
	private         FieldCircularList   previous;
	// The diceArray is an array in which the next eleven Fields from the next field are stored. This will increase
	// the performance of the player moving forward when using the dices since the player can only go only 2 to 12
	// fields.
	private         FieldCircularList[] diceArray;

	/**
	 * @param name The value determines the name of the Field.
	 */
	protected FieldCircularList(String name) {
		NAME = name;
		FIELDNUMBER = number++;
		next = this;
		previous = this;
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
		return NAME;
	}

	/**
	 * The method will do what ever the objects propose is
	 *
	 * @param player The value determines the Player who caused the method call
	 */
	public abstract void action(Player player);

	/**
	 * @param previous The value determines the previous FieldCircularList object in the circular list.
	 */
	public void add(FieldCircularList previous) {
		if(previous.previous.equals(previous)) {
			previous.next = this;
			previous.previous = this;
			next = previous;
		} else {
			next = previous.next;
			next.previous = this;
			previous.next = this;
		}
		this.previous = previous;
	}

	/**
	 * @return the return value is a new FieldData object with the current attributes of the FieldCircularList
	 */
	public abstract FieldData toFieldData();

	/**
	 * @return The return value is the next FieldCircularList object in the circular list.
	 */
	public FieldCircularList getNext() {
		return next;
	}

	/**
	 * @return The return value is the previous FieldCircularList object in the circular list.
	 */
	public FieldCircularList getPrevious() {
		return previous;
	}

	/**
	 * @param index The value determines the amount of fields that have to be gone.
	 * @return The return value is the FieldCircularList object index times forward,
	 * if the index is to high or negative
	 *         the return value is null.
	 */
	public FieldCircularList getDiceNext(int index) {
		index -= 2;
		if(index >= 0 && FIELDNUMBER < diceArray[index].getFieldNumber() && diceArray[index].getFieldNumber() != 0) {
			return diceArray[index];
		}
		return null;
	}

	/**
	 * @return The return value is the field number.
	 */
	public int getFieldNumber() {
		return FIELDNUMBER;
	}
}
