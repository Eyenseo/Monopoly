package objects;

import objects.card.Jailbait;
import objects.events.PlayerEvent;
import objects.listeners.PlayerEventListener;
import objects.map.FieldCircularList;
import objects.map.notPurchasable.Go;
import objects.map.purchasable.PurchasableCircularList;
import objects.value.PlayerData;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * The Player class represents the player.
 */
//JAVADOC
public class Player implements Serializable {
	private static final long   serialVersionUID = 3764102750636389385L;
	private static final Random random           = new Random();
	private static       int    classId          = 0;
	private final int                                playerId;
	private final String                             NAME;
	private final int[]                              dices;
	private final Color                              color;
	private       boolean                            inJail;
	private       int                                money;
	private       int                                neededMoney;
	private       int                                timeInJail;
	private       FieldCircularList                  position;
	private       boolean                            turnEnd;
	private       int                                threwDice;
	private       boolean                            giveUp;
	private       int                                trading;
	private final Random                             randomGenerator;
	private final Vector<Jailbait>                   jailbait;
	private final ArrayList<PurchasableCircularList> property;
	private final ArrayList<PlayerEventListener>     playerEventListeners;

	/**
	 * @param name  The value determines the name of the Player.
	 * @param color
	 * @param money The value determines the amount of money the Player has.
	 */
	public Player(String name, int money) {
		playerId = classId++;
		randomGenerator = random;
		NAME = name;
		this.money = money;
		neededMoney = 0;
		inJail = false;
		dices = new int[2];
		dices[0] = 4;
		dices[1] = 2;
		turnEnd = true;
		giveUp = false;
		threwDice = 0;
		timeInJail = 0;
		trading = -1;

		color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

		jailbait = new Vector<Jailbait>();
		property = new ArrayList<PurchasableCircularList>();
		playerEventListeners = new ArrayList<PlayerEventListener>();
	}

	/**
	 * @return The return value is the array with the last dices.
	 */
	public int[] getDices() {
		return dices;
	}

	/**
	 * @return The return value is the money amount the Player has.
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @return The return value is the name of the Player.
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * @return the return value is the classId of the Player
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @return The return value is the FieldCircularList object the player is standing on.
	 */
	public FieldCircularList getPosition() {
		return position;
	}

	/**
	 * The method will fire a PlayerEvent.
	 *
	 * @param position The value determines the new FieldCircularList object the player is standing on.
	 */
	public void setPosition(FieldCircularList position) {
		this.position = position;
		firePlayerEvent();
	}

	/**
	 * The method will fire PlayerEvents for all listeners.
	 */
	private void firePlayerEvent() {
		PlayerEvent event = new PlayerEvent(this);
		for(PlayerEventListener l : playerEventListeners) {
			l.actionPerformed(event);
		}
	}

	/**
	 * @return The return value is a ArrayList of the properties from the player.
	 */
	public ArrayList<PurchasableCircularList> getProperties() {
		return property;
	}

	/**
	 * @return the return value is the amount of how often the player threw the dice
	 */
	public int getThrewDice() {
		return threwDice;
	}

	/**
	 * @param threwDice the value determines how often the player threw the dice
	 */
	private void setThrewDice(int threwDice) {
		this.threwDice = threwDice;
	}

	/**
	 * @return the return value is the classId the Player the player is currently trading with
	 */
	public int getTrading() {
		return trading;
	}

	/**
	 * @param trading the value determines the classId the Player the player is currently trading with
	 */
	public void setTrading(int trading) {
		this.trading = trading;
	}

	/**
	 * @return the return value is true if the Player forfeits
	 */
	public boolean isGiveUp() {
		return giveUp;
	}

	public void setGiveUp(boolean giveUp) {
		this.giveUp = giveUp;
		firePlayerEvent();
	}

	/**
	 * @return The return value is true if the player is in jail else false.
	 */
	public boolean isInJail() {
		return inJail;
	}

	/**
	 * The method will set time in jail to 0 and will fire a PlayerEvent.
	 *
	 * @param inJail The value determines the state of the player (true) in Jail.
	 */
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
		timeInJail = 0;
		firePlayerEvent();
	}

	/**
	 * @param purchasable The value determines the PurchasableCircularList object that will be owned by this Player.
	 */
	public void setOwnership(PurchasableCircularList purchasable) {
		purchasable.setOwner(this);
	}

	/**
	 * The method will fire a PlayerEvent.
	 *
	 * @param purchasable The value determines the PurchasableCircularList object the property that will be added to the
	 *                    player.
	 */
	public void addProperty(PurchasableCircularList purchasable) {
		property.add(purchasable);
		firePlayerEvent();
	}

	/**
	 * The method will fire a PlayerEvent.
	 *
	 * @param purchasable The value determines the PurchasableCircularList object the property that will be removed to the
	 *                    player.
	 */
	public void removeProperty(PurchasableCircularList purchasable) {
		property.remove(purchasable);
		firePlayerEvent();
	}

	/**
	 * The method will fire a PlayerEvent.
	 *
	 * @param amount The value determines the
	 */
	public void addMoney(int amount) {
		updateNeededMoney(amount);
		money += amount;
		firePlayerEvent();
	}

	/**
	 * @return The return value is true if the player has a Jailbait Card.
	 */
	public boolean isJailbait() {
		return !jailbait.isEmpty();
	}

	/**
	 * The method will fire a PlayerEvent.
	 *
	 * @param jailbait The value determines a Jailbait car to be added to the Player.
	 */
	public void addJailbait(Jailbait jailbait) {
		this.jailbait.add(jailbait);
		firePlayerEvent();
	}

	/**
	 * The method will fire a PlayerEvent. The method removes a jailbait Card from the Player and adds it back to its
	 * CardStack and sets the player free.
	 */
	public void useJailbait() {
		jailbait.firstElement().freePlayer(this);
		jailbait.remove(jailbait.firstElement());
		firePlayerEvent();
	}

	/**
	 * The method moves the Player to the new Field. It will move the player also if he is in jail but threw a double
	 * or if
	 * he was already three turns in jail.
	 *
	 * @return The return value is true if the Player got doubles.
	 */
	public boolean move() {
		throwDice();
		boolean doubles = dices[0] == dices[1];
		if(!inJail || doubles) {
			inJail = false;
			move(dices[0] + dices[1], true);
			position.action(this);
		} else {
			timeInJail++;
			if(timeInJail > 2) {
				inJail = false;
			}
		}
		return doubles;
	}

	/**
	 * The method will fire a PlayerEvent.
	 *
	 * @return The return value is a array with two random numbers between 1 and 6.
	 */
	private int[] throwDice() {
		dices[0] = randomGenerator.nextInt(6) + 1;
		dices[1] = randomGenerator.nextInt(6) + 1;
		threwDice++;
		firePlayerEvent();
		return dices;
	}

	/**
	 * The method moves the Player to the new Field also the method will fire a PlayerEvent.
	 *
	 * @param by     The value determines the amount the Player shall go.
	 * @param overGo The value determines if the Player will get the bonus of Go if he passed it.
	 */
	public void move(int by, boolean overGo) {
		if(by > 0) {
			if(position.getDiceNext(by) != null) {
				position = position.getDiceNext(by);
			} else {
				for(int i = 0; i < by; i++) {
					position = position.getNext();
					if(overGo && position instanceof Go) {
						position.action(this);
					}
				}
			}
		} else {
			for(int i = by; i < 0; i++) {
				position = position.getPrevious();
				if(overGo && position instanceof Go) {
					position.action(this);
				}
			}
		}
		firePlayerEvent();
	}

	/**
	 * @param listener the value determines the playerEventListeners to be added
	 */
	public void addPlayerEventListener(PlayerEventListener listener) {
		playerEventListeners.add(listener);
	}

	/**
	 * @param listener the value determines the playerEventListeners to be removed
	 */
	public void removePlayerEventListener(PlayerEventListener listener) {
		playerEventListeners.remove(listener);
	}

	/**
	 * The method will fire a PlayerEvent.
	 *
	 * @param turnEnd the value determines if the player is currently at turn (false)
	 */
	public void setTurnEnd(boolean turnEnd) {
		if(!turnEnd) {
			setThrewDice(0);
		}
		this.turnEnd = turnEnd;
		firePlayerEvent();
	}

	/**
	 * @param purchasable the value determines the PurchasableCircularList object to be bought
	 */
	public void buyPurchasable(PurchasableCircularList purchasable) {
		pay(purchasable.getPrice());
		purchasable.setOwner(this);
	}

	/**
	 * The method will fire a PlayerEvent.
	 *
	 * @param amount The value determines the money to be removed from the player.
	 */
	public void pay(int amount) {
		updateNeededMoney(-amount);
		while(neededMoney != 0) {

			firePlayerEvent();
			try {
				synchronized(Thread.currentThread()) {
					Thread.currentThread().wait();
				}
			} catch(InterruptedException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
		money -= amount;
		firePlayerEvent();
	}

	//JAVADOC
	private void updateNeededMoney(int amount) {
		int money = this.money + amount;

		if(money < 0) {
			neededMoney -= money;
		} else if(neededMoney > 0) {
			neededMoney -= money;
			if(neededMoney < 0) {
				neededMoney = 0;
			}
		}
	}

	public Vector<Jailbait> getJailbait() {
		return jailbait;
	}

	/**
	 * @return the return value is a PlayerData object with the position information included
	 */
	public PlayerData toPlayerData() {
		return toPlayerData(true);
	}

	public boolean isTurnEnd() {
		return turnEnd;
	}

	/**
	 * @param withPosition the value determines if the position information will be included
	 * @return the return value is a PlayerData object with all information included
	 */
	public PlayerData toPlayerData(boolean withPosition) {
		if(withPosition) {
			return new PlayerData(playerId, NAME, color, dices[0], dices[1], inJail, money, neededMoney,
			                      position.toFieldData(), turnEnd, threwDice, trading, giveUp);
		} else {
			return new PlayerData(playerId, NAME, color, dices[0], dices[1], inJail, money, neededMoney, null, turnEnd,
			                      threwDice, trading, giveUp);
		}
	}
}
