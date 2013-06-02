package objects.value.connection.data;

import objects.value.connection.data.map.FieldData;

//JAVADOC
public class PlayerData {
	private int       id;
	private String    name;
	private int       firstDice;
	private int       secondDice;
	private boolean   inJail;
	private int       money;
	private FieldData position;
	private boolean   turnEnd;
	private int       threwDice;

	//JAVADOC
	public PlayerData(int id, String name, int firstDice, int secondDice, boolean inJail, int money, FieldData position,
	                  boolean turnEnd, int threwDice) {
		this.id = id;
		this.name = name;
		this.firstDice = firstDice;
		this.secondDice = secondDice;
		this.inJail = inJail;
		this.money = money;
		this.position = position;
		this.turnEnd = turnEnd;
		this.threwDice = threwDice;
	}

	//JAVADOC
	public int getId() {
		return id;
	}

	//JAVADOC
	public String getName() {
		return name;
	}

	//JAVADOC
	public int getFirstDice() {
		return firstDice;
	}

	//JAVADOC
	public void setFirstDice(int firstDice) {
		this.firstDice = firstDice;
	}

	//JAVADOC
	public int getSecondDice() {
		return secondDice;
	}

	//JAVADOC
	public void setSecondDice(int secondDice) {
		this.secondDice = secondDice;
	}

	//JAVADOC
	public boolean isInJail() {
		return inJail;
	}

	//JAVADOC
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	//JAVADOC
	public int getMoney() {
		return money;
	}

	//JAVADOC
	public void setMoney(int money) {
		this.money = money;
	}

	//JAVADOC
	public FieldData getPosition() {
		return position;
	}

	//JAVADOC
	public void setPosition(FieldData position) {
		this.position = position;
	}

	//JAVADOC
	public boolean isTurnEnd() {
		return turnEnd;
	}

	//JAVADOC
	public void setTurnEnd(boolean turnEnd) {
		this.turnEnd = turnEnd;
	}

	//JAVADOC
	public int getThrewDice() {
		return threwDice;
	}

	//JAVADOC
	public void setThrewDice(int threwDice) {
		this.threwDice = threwDice;
	}

	//JAVADOC
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
