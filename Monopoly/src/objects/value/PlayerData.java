package objects.value;

import objects.value.map.FieldData;

import java.awt.*;


public class PlayerData {
	private final int       id;
	private final String    name;
	private final Color     color;
	private       int       firstDice;
	private       int       secondDice;
	private       boolean   inJail;
	private       int       money;
	private       int       neededMoney;
	private       FieldData position;
	private       boolean   turnEnd;
	private       int       threwDice;
	private       int       trading;
	private       boolean   giveUp;

	/**
	 * @param id         the value determines the id of the player
	 * @param name       the value determines the name of the player
	 * @param color
	 * @param firstDice  the value determines the first dice
	 * @param secondDice the value determines the second dice
	 * @param inJail     the value determines if the player is in jail
	 * @param money      the value determines the money the player has
	 * @param position   the value determines the position of the player
	 * @param turnEnd    the value determines if the player is currently at turn
	 * @param threwDice  the value determines the amount of times the player threw the dice
	 * @param trading    the value determines the id of the Player the player is currently trading with
	 */
	public PlayerData(int id, String name, Color color, int firstDice, int secondDice, boolean inJail, int money,
	                  int neededMoney, FieldData position, boolean turnEnd, int threwDice, int trading,
	                  boolean giveUp) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.firstDice = firstDice;
		this.secondDice = secondDice;
		this.inJail = inJail;
		this.neededMoney = neededMoney;
		this.money = money;
		this.position = position;
		this.turnEnd = turnEnd;
		this.threwDice = threwDice;
		this.trading = trading;
		this.giveUp = giveUp;
	}

	/**
	 *
	 * @return The return value is true if the player gave up
	 */
	public boolean isGiveUp() {
		return giveUp;
	}

	/**
	 *
	 * @param giveUp The value determines if the player gave up or not
	 */
	public void setGiveUp(boolean giveUp) {
		this.giveUp = giveUp;
	}

	/**
	 * @return the return value is the player Id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the return value is the player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the return value is the first dice
	 */
	public int getFirstDice() {
		return firstDice;
	}

	/**
	 * @param firstDice the value determines the first dice
	 */
	public void setFirstDice(int firstDice) {
		this.firstDice = firstDice;
	}

	/**
	 * @return the return value is the second dice
	 */
	public int getSecondDice() {
		return secondDice;
	}

	/**
	 * @param secondDice the value determines the second dice
	 */
	public void setSecondDice(int secondDice) {
		this.secondDice = secondDice;
	}

	/**
	 * @return the return value is true if the player is in jail
	 */
	public boolean isInJail() {
		return inJail;
	}

	/**
	 * @param inJail the value is true if the player is in jail
	 */
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	/**
	 * @return the return value is the amount the player has
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money the value determines the amount of money the player has
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the return value is the position of the player
	 */
	public FieldData getPosition() {
		return position;
	}

	/**
	 * @param position the value determines the position the player is at
	 */
	public void setPosition(FieldData position) {
		this.position = position;
	}

	/**
	 * @return the return value is false if the player is currently at turn
	 */
	public boolean isTurnEnd() {
		return turnEnd;
	}

	/**
	 * @param turnEnd the value is false if the player is at turn
	 */
	public void setTurnEnd(boolean turnEnd) {
		this.turnEnd = turnEnd;
	}

	/**
	 * @return the return value is the amount of time the player threw the dice
	 */
	public int getThrewDice() {
		return threwDice;
	}

	/**
	 * @param threwDice the value determines how often the player threw the dice
	 */
	public void setThrewDice(int threwDice) {
		this.threwDice = threwDice;
	}

	/**
	 * @return the return value is the is the id of the Player the player is trading with
	 */
	public int getTrading() {
		return trading;
	}

	/**
	 * @param trading the value determines the id of the Player the player is currently trading with
	 */
	public void setTrading(int trading) {
		this.trading = trading;
	}

	/**
	 *
	 * @return the return value is the neededMoney (money that the player has to obtain)
	 */
	public int getNeededMoney() {
		return neededMoney;
	}

	/**
	 *
	 * @param neededMoney  The value determines the amount of needed money of the player
	 */
	public void setNeededMoney(int neededMoney) {
		this.neededMoney = neededMoney;
	}

	/**
	 *
	 * @return the value determines the color of the player
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * two PlayerData objects are the same if their Ids and names are the same
	 *
	 * @param o the value determines the object to be checked against
	 * @return the return value is true if the objects are the same
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}

		PlayerData playerData = (PlayerData) o;

		if(id != playerData.id) {
			return false;
		}
		if(!name.equals(playerData.name)) {
			return false;
		}

		return true;
	}
}
