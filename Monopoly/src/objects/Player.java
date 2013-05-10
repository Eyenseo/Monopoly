package objects;

import objects.card.Jailbait;
import objects.map.FieldCircularList;
import objects.map.notPurchasable.Go;
import objects.map.purchasable.PurchasableCircularList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * The Player class represents the player.
 *
 * @author Eyenseo
 * @version 1
 */
public class Player implements Serializable {
	private static final long serialVersionUID = 3764102750636389385L;
	private final String            NAME;
	private       boolean           inJail;
	private       int               money;
	private       int[]             dices;
	private       FieldCircularList field;
	private Random                             randomGenerator = new Random();
	private Vector<Jailbait>                   jailbait        = new Vector<Jailbait>();
	private ArrayList<PurchasableCircularList> property        = new ArrayList<PurchasableCircularList>();
	//TODO have only one Random Generator for all Player

	/**
	 * @param name  The value determines the name of the Player.
	 * @param money The value determines the amount of money the Player has.
	 */
	public Player(String name, int money) {
		this.NAME = name;
		this.money = money;
		this.inJail = false;
		dices = new int[2];
	}

	/**
	 * @return The return value is the name of the Player.
	 */
	public String getName() {
		return this.NAME;
	}

	/**
	 * @return The return value is true if the player is in jail else false.
	 */
	public boolean isInJail() {
		return this.inJail;
	}

	/**
	 * @param inJail The value determines the state of the player (true) in Jail.
	 */
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	/**
	 * The method removes the money for the PurchasableCircularList object and stets the owner of it.
	 *
	 * @param purchasable The value determines the PurchasableCircularList object, that is being brought.
	 */
	public void buy(PurchasableCircularList purchasable) {
		this.money -= purchasable.getPrice();
		purchasable.setOwner(this);
	}

	/**
	 * @param purchasable The value determines the PurchasableCircularList object that will be owned by this Player.
	 */
	public void setOwnership(PurchasableCircularList purchasable) {
		purchasable.setOwner(this);
	}

	/**
	 * @param purchasable The value determines the PurchasableCircularList object the property that will be added to the player.
	 */
	public void addProperty(PurchasableCircularList purchasable) {
		this.property.add(purchasable);
	}

	/**
	 * @param purchasable The value determines the PurchasableCircularList object the property that will be removed to the player.
	 */
	public void removeProperty(PurchasableCircularList purchasable) {
		this.property.remove(purchasable);
	}

	/**
	 * @param amount The value determines the
	 */
	public void addMoney(int amount) {
		this.money += amount;
	}

	/**
	 * @return The return value is the money amount the Player has.
	 */
	public int getMoney() {
		return this.money;
	}

	/**
	 * @return The return value is the array with the last dices.
	 */
	public int[] getDices() {
		return this.dices;
	}

	/**
	 * @return The return value is true if the player has a Jailbait Card.
	 */
	public boolean isJailbait() {
		return !jailbait.isEmpty();
	}

	/**
	 * @param amount The value determines the money to be removed from the player.
	 */
	public void pay(int amount) {
		this.money -= amount;
	}

	/**
	 * @return The return value is the FieldCircularList object the player is standing on.
	 */
	public FieldCircularList getField() {
		return this.field;
	}

	/**
	 * @param field The value determines the new FieldCircularList object the player is standing on.
	 */
	public void setField(FieldCircularList field) {
		this.field = field;
	}

	/**
	 * @param jailbait The value determines a Jailbait car to be added to the Player.
	 */
	public void addJailbait(Jailbait jailbait) {
		this.jailbait.add(jailbait);
	}

	/**
	 * The method removes a jailbait Card from the Player and adds it back to its CardStack and sets the player free.
	 */
	public void useJailbait() {
		jailbait.firstElement().freePlayer(this);
		jailbait.remove(jailbait.firstElement());
	}

	/**
	 * The method moves the Player to the new Field.
	 *
	 * @return The return value is true is the Player got doubles.
	 */
	public boolean move() {
		throwDice();
		boolean doubles = dices[0] == dices[1];
		if(!inJail || !doubles) {
			move(dices[0] + dices[1], true);
		}
		field.action(this);
		return doubles;
	}

	/**
	 * The method moves the Player to the new Field.
	 *
	 * @param by     The value determines the amount the Player shall go.
	 * @param overGo The value determines if the Player will get the bonus of Go if he passed it.
	 */
	public void move(int by, boolean overGo) {
		if(by > 0) {
			if(field.getDiceNext(by) != null) {
				field = field.getDiceNext(by);
			} else {
				for(int i = 0; i < by; i++) {
					field = field.getNext();
					if(overGo && field instanceof Go) {
						field.action(this);
					}
				}
			}
		} else {
			for(int i = by; i < 0; i++) {
				field = field.getPrevious();
				if(overGo && field instanceof Go) {
					field.action(this);
				}
			}
		}
	}

	/**
	 * @return The return value is a array with two random numbers between 1 and 6.
	 */
	public int[] throwDice() {
		dices[0] = randomGenerator.nextInt(6) + 1;
		dices[1] = randomGenerator.nextInt(6) + 1;
		return dices;
	}

	/**
	 * @return The return value is a ArrayList of the properties from the player.
	 */
	public ArrayList<PurchasableCircularList> getProperties() {
		return property;
	}
}
