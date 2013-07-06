package ui.gui;

import core.ClientOperator;
import objects.events.*;
import objects.listeners.*;
import objects.value.InitializeMapData;
import objects.value.MassageData;
import objects.value.PlayerData;
import objects.value.action.HaggleData;
import objects.value.map.FieldData;
import objects.value.map.PurchasableData;
import objects.value.map.StreetData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Model holds all information that any ui will need. It will fire following Model events: <ul> <li>DICE</li>
 * <li>ISINJAIL</li> <li>MONEY</li> <li>POSITION</li> <li>TURNSTATE</li> <li>THREWDICE</li> <li>INMORTAGE</li>
 * <li>STAGE</li> <li>PROPERTY</li> <li>TURNOPTION</li> <li>BUYOPTION</li> <li>MAINPANEL</li> <li>HAGGLE</li> </ul>
 */
public class Model {
	//Event listeners
	private final HashMap<ModelEventName, ArrayList<ModelEventListener>> listener;
	//Data
	private final ArrayList<FieldData>                                   map;
	private       HashMap<Integer, ArrayList<PurchasableData>>           purchasableHashMap;
	private       HashMap<Integer, PlayerData>                           playerHashMap;
	private       PlayerData                                             user; //TODO change to int
	private       ArrayList<MassageData>                                 massageDataArrayList; // Todo is this needed?
	private       int                                                    oldMoney;
	//Action Data
	private       HaggleData                                             haggleData;
	//States
	private       MainPanelName                                          currentMainPanelName;
	private       MainPanelName                                          previousMainPanelName;
	private       TurnOptionState                                        turnOptionState;
	private       BuyOptionState                                         buyOptionState;

	/**
	 * @param clientOperator    the value determines the client operator of the Model
	 * @param initializeMapData the value determines the map data to be displayed
	 */
	public Model(ClientOperator clientOperator, InitializeMapData initializeMapData) {
		//Event listener
		listener = new HashMap<ModelEventName, ArrayList<ModelEventListener>>();
		for(ModelEventName eventName : ModelEventName.values()) {
			listener.put(eventName, new ArrayList<ModelEventListener>());
		}

		//MassageData
		massageDataArrayList = new ArrayList<MassageData>();

		//Map
		map = initializeMapData.getMap();

		//States
		currentMainPanelName = MainPanelName.GAME;
		turnOptionState = TurnOptionState.DEACTIVATED;
		buyOptionState = BuyOptionState.DEACTIVATED;

		//Register EventListeners
		clientOperator.addFieldDataEventListener(new ClientOperatorPurchasableDataEventListener() {
			@Override public void actionPerformed(ClientOperatorPurchasableDataEvent event) {
				analysePurchasableData(event.getPurchasableData());
			}
		});

		clientOperator.addPlayerDataEventListener(new ClientOperatorPlayerDataEventListener() {
			@Override public void actionPerformed(ClientOperatorPlayerDataEvent event) {
				analysePlayerData(event.getPlayerData());
			}
		});

		//The listener will set the new HaggleData and fire a haggle ModelEvent
		clientOperator.addHaggleDataEventListener(new ClientOperatorHaggleDataEventListener() {
			@Override public void actionPerformed(ClientOperatorHaggleDataEvent event) {
				if(haggleData == null || haggleData.getId().equals(event.getHaggleData().getId()) ||
				   haggleData.getHaggleState() == HaggleData.HaggleState.DECLINE ||
				   haggleData.getHaggleState() == HaggleData.HaggleState.ACCEPT) {
					haggleData = new HaggleData(event.getHaggleData());
				}
				fireModelEvent(ModelEventName.HAGGLE);
			}
		});

		clientOperator.addMessageDataEventListener(new ClientOperatorMessageDataEventListener() {
			@Override public void actionPerformed(ClientOperatorMessageDataEvent event) {
				switch(event.getMassageData().getTyp()) {
					case COMMUNITY:
					case EVENT:
						massageDataArrayList.add(event.getMassageData());
						fireModelEvent(ModelEventName.CARD);
						break;
					case CHAT://TODO implement chat
						break;
				}
			}
		});
	}

	/**
	 * @param playerHashMap the value determines all players participate in the game
	 * @param userId        the value determines the id of the player of the model
	 */
	public void setPlayer(HashMap<Integer, PlayerData> playerHashMap, int userId) {
		//Initialise lists for each player's purchasables
		purchasableHashMap = new HashMap<Integer, ArrayList<PurchasableData>>();
		for(PlayerData player : playerHashMap.values()) {
			purchasableHashMap.put(player.getId(), new ArrayList<PurchasableData>());
		}

		//player
		this.playerHashMap = playerHashMap;

		//save player of this client
		user = playerHashMap.get(userId);
	}

	/**
	 * @param listener the value determines the listener to be added
	 */
	public void addModelEventListener(ModelEventName eventName, ModelEventListener listener) {
		this.listener.get(eventName).add(listener);
	}

	/**
	 * @return the return value is the current HaggleData
	 */
	public HaggleData getHaggleData() {
		return haggleData;
	}

	/**
	 * @param haggleData the value determines the new HaggleData
	 */
	public void setHaggleData(HaggleData haggleData) {
		this.haggleData = new HaggleData(haggleData);
	}

	/**
	 * If it is the player of the model the method will update the PlayerData  fire following ModelEvents: <ul>
	 * <li>DICE</li> <li>ISINJAIL</li> <li>MONEY</li> <li>POSITION</li> <li>TURNSTATE</li> <li>TURNOPTION</li>
	 * <li>BUYOPTION</li> </ul>
	 *
	 * @param playerData the value determines the PlayerData to be analysed
	 */
	private void analysePlayerData(PlayerData playerData) {
		if(playerData.equals(user)) {
			if(user.getFirstDice() != playerData.getFirstDice() || user.getSecondDice() != playerData.getSecondDice()) {
				user.setFirstDice(playerData.getFirstDice());
				user.setSecondDice(playerData.getSecondDice());
				fireModelEvent(ModelEventName.DICE);
			}
			if(user.isInJail() != playerData.isInJail()) {
				user.setInJail(playerData.isInJail());
				fireModelEvent(ModelEventName.ISINJAIL);
			}
			if(user.getMoney() != playerData.getMoney()) {
				oldMoney = user.getMoney();
				user.setMoney(playerData.getMoney());
				fireModelEvent(ModelEventName.MONEY);
			}
			if(!user.getPosition().equals(playerData.getPosition())) {
				user.setPosition(playerData.getPosition());

				fireModelEvent(ModelEventName.POSITION);
			}
			if(user.isTurnEnd() != playerData.isTurnEnd()) {
				user.setTurnEnd(playerData.isTurnEnd());
				fireModelEvent(ModelEventName.TURNSTATE);
			}
			if(user.getThrewDice() != playerData.getThrewDice()) {
				user.setThrewDice(playerData.getThrewDice());
			}
			if(user.getTrading() != playerData.getTrading()) {
				user.setTrading(playerData.getTrading());
			}
			if(user.getNeededMoney() != playerData.getNeededMoney()) {
				if(playerData.getNeededMoney() > 0) {
					setCurrentMainPanelName(MainPanelName.FINANCIAL);
				}
				user.setNeededMoney(playerData.getNeededMoney());
				fireModelEvent(ModelEventName.FINANCIAL);
			}
			if(playerData.isGiveUp()) {
				user.setGiveUp(true);
				fireModelEvent(ModelEventName.PLAYERREMOVED);
			}
			updateTurnOption();
			updateBuyOption();
		} else {
			if(playerData.isGiveUp()) {
				playerHashMap.remove(playerData.getId());
				fireModelEvent(ModelEventName.PLAYERREMOVED);
			}
		}
	}

	/**
	 * the method will update the purchasable data and if the purchasable is or was owned by the player, or it is the
	 * current position of the player of the model it will fire either ModelEvent: <ul> <li>PROPERTY</li>
	 * <li>INMORTAGE</li> <li>STAGE</li> <li>POSITION</li> </ul>
	 *
	 * @param purchasableData the value determines the PurchasableData to be analysed
	 */
	//TODO check method
	private void analysePurchasableData(PurchasableData purchasableData) {

		if(!purchasableHashMap.get(purchasableData.getOwner().getId()).contains(purchasableData)) {
			//TODO check if the for loop is needed
			for(int i = 0; i < purchasableHashMap.size(); i++) {
				ArrayList<PurchasableData> list = purchasableHashMap.get(i);

				if(list.contains(purchasableData) && i != purchasableData.getOwner().getId()) {
					list.remove(list.indexOf(purchasableData));

					if(i == user.getId()) {
						fireModelEvent(ModelEventName.PROPERTY);
					}
				}

				if(!list.contains(purchasableData) && i == purchasableData.getOwner().getId()) {
					list.add(purchasableData);

					if(i == user.getId()) {
						fireModelEvent(ModelEventName.PROPERTY);
					}
				}
			}
		} else if(purchasableData.getOwner().getId() == user.getId()) {
			ArrayList<PurchasableData> userPurchasable = purchasableHashMap.get(user.getId());

			PurchasableData property = userPurchasable.get(userPurchasable.indexOf(purchasableData));

			if(property.isInMortgage() != purchasableData.isInMortgage()) {
				property.setInMortgage(purchasableData.isInMortgage());
				fireModelEvent(ModelEventName.INMORTAGE);
			}

			if(property.getStage() != purchasableData.getStage()) {
				property.setStage(purchasableData.getStage());
				fireModelEvent(ModelEventName.STAGE);
			}

			if(purchasableData instanceof StreetData) {
				((StreetData) property).setUpgradeable(((StreetData) purchasableData).isUpgradeable());
			}
		}

		if(user.getPosition().equals(purchasableData)) {
			user.setPosition(purchasableData);
			updateBuyOption();
			fireModelEvent(ModelEventName.POSITION);
		}
	}

	//JAVADOC
	public MainPanelName getPreviousMainPanelName() {
		return previousMainPanelName;
	}

	/**
	 * @return the return value is the state of the buy option
	 */
	public BuyOptionState getBuyOptionState() {
		return buyOptionState;
	}

	/**
	 * @return the return value is the "name" of the current main panel
	 */
	public MainPanelName getCurrentMainPanelName() {
		return currentMainPanelName;
	}

	/**
	 * @param currentMainPanelName the value determines the "name" of the current main panel
	 */
	public void setCurrentMainPanelName(MainPanelName currentMainPanelName) {
		if(this.currentMainPanelName != currentMainPanelName) {
			previousMainPanelName = this.currentMainPanelName;
		}
		this.currentMainPanelName = currentMainPanelName;
		fireModelEvent(ModelEventName.MAINPANEL);
	}

	/**
	 * The method will fire a ModelEvent
	 *
	 * @param eventName the value determines the "event" to be fired
	 */
	private void fireModelEvent(ModelEventName eventName) {
		ModelEvent event = new ModelEvent(this);
		for(ModelEventListener l : listener.get(eventName)) {
			l.actionPerformed(event);
		}
	}

	/**
	 * @return the return value is the money of the user
	 */
	public int getCurrentMoney() {
		return user.getMoney();
	}

	/**
	 * @return the return value is the name of the field the user ist standing on
	 */
	public String getFieldName() {
		return user.getPosition().getName();
	}

	/**
	 * @return the return value is the first dice of the cleint player
	 */
	public int getFirstDice() {
		return user.getFirstDice();
	}

	/**
	 * @return the return value is the second dice of the user
	 */
	public int getSecondDice() {
		return user.getSecondDice();
	}

	/**
	 * @return the return value is the previous amount of money from the user
	 */
	public int getOldMoney() {
		return oldMoney;
	}

	/**
	 * @return the return value is null if the purchasable has no owner or is the name of the owner
	 */
	public String getPurchasableOwner() {
		if(user.getPosition() instanceof PurchasableData) {
			if(((PurchasableData) user.getPosition()).getOwner() != null) {
				return ((PurchasableData) user.getPosition()).getOwner().getName();
			}
		}
		return null;
	}

	/**
	 * @return the return value is null if the purchasable has no owner or is the id of the owner
	 */
	public int getPurchasableOwnerId() {
		if(user.getPosition() instanceof PurchasableData) {
			if(((PurchasableData) user.getPosition()).getOwner() != null) {
				return ((PurchasableData) user.getPosition()).getOwner().getId();
			}
		}
		return -1;
	}

	/**
	 * @return the return value is -1 if the user is not standing on a purchasable field or its price
	 */
	public int getPurchasablePrice() {
		if(user.getPosition() instanceof PurchasableData) {
			return ((PurchasableData) user.getPosition()).getPRICE();
		} else {
			return -1;
		}
	}

	/**
	 * @return the return value is the stage of the street the user is currently standing on or if there is no stage -1
	 */
	public int getStreetStage() {
		if(user.getPosition() instanceof StreetData) {
			return ((StreetData) user.getPosition()).getStage();
		}
		return -1;
	}

	/**
	 * @return the return value is the price of a upgrade (house/hotel) of street or if it is not a street -1
	 */
	public int getStreetUpgrade() {
		if(user.getPosition() instanceof StreetData) {
			return ((StreetData) user.getPosition()).getUPGRADE();
		} else {
			return -1;
		}
	}

	/**
	 * @return the return value is the turn state
	 */
	public TurnOptionState getTurnOptionState() {
		return turnOptionState;
	}

	/**
	 * @return the return value is true if the cleint player is in jail
	 */
	public boolean isInJail() {
		return user.isInJail();
	}

	/**
	 * @return the return value is true if the player can throw a dice
	 */
	public boolean isMoveAction() {
		return turnOptionState == TurnOptionState.THROWDICE || turnOptionState == TurnOptionState.THROWDICEAGAIN;
	}

	/**
	 * @return the return value is true if the user is standing on a purchasable field
	 */
	public boolean isPurchasable() {
		return user.getPosition() instanceof PurchasableData;
	}

	/**
	 * @return the return value is true if the user is standing on a street field
	 */
	public boolean isStreet() {
		return user.getPosition() instanceof StreetData;
	}

	/**
	 * @param listener the value determines the listener to be removed
	 */
	public void removeModelEventListener(ModelEventName eventName, ModelEventListener listener) {
		this.listener.remove(eventName).remove(listener);
	}

	/**
	 * The method changes the buyOptionState to PURCHASABLE if the player can buy the field,
	 * to BUYHOUSE if he can buy a
	 * house, to BUYHOTEL if he can buy a hotel or DEACTIVATED if he can't buy anything. Will fire a ModelEvent
	 */
	private void updateBuyOption() {
		boolean active = false;
		if(user.getPosition() instanceof PurchasableData) {
			if(((PurchasableData) user.getPosition()).getOwner() == null) {
				buyOptionState = BuyOptionState.PURCHASABLE;
				active = true;
			} else if(user.getPosition() instanceof StreetData) {
				StreetData street = (StreetData) user.getPosition();
				if(street.getOwner().getId() == user.getId()) {
					if(street.isUpgradeable() && street.getStage() > 0) {
						if(street.getStage() < street.getINCOME().length - 2) {
							buyOptionState = BuyOptionState.BUYHOUSE;
							active = true;
						} else if(street.getStage() == street.getINCOME().length - 2) {
							buyOptionState = BuyOptionState.BUYHOTEL;
							active = true;
						}
					}
				}
			}
		}

		if(!active || turnOptionState == TurnOptionState.DEACTIVATED) {
			buyOptionState = BuyOptionState.DEACTIVATED;
		}
		fireModelEvent(ModelEventName.BUYOPTION);
	}

	/**
	 * The method will change turnOptionState to THROWDICE if the player starts his turn, to THROWDICEAGAIN if he got a
	 * double, to ENDTURN if he can only end his turn or deactivated if he cant do anything
	 */
	private void updateTurnOption() {
		if(!user.isTurnEnd()) {
			if(user.getThrewDice() == 0) {
				turnOptionState = TurnOptionState.THROWDICE;
			} else if(user.getFirstDice() == user.getSecondDice()) {
				turnOptionState = TurnOptionState.THROWDICEAGAIN;
			} else {
				turnOptionState = TurnOptionState.ENDTURN;
			}
		} else {
			turnOptionState = TurnOptionState.DEACTIVATED;
		}
		fireModelEvent(ModelEventName.TURNOPTION);
	}

	/**
	 * The method will fire all ModelEvents
	 */
	public void fireAllEvents() {
		for(ModelEventName event : ModelEventName.values()) {
			fireModelEvent(event);
		}
	}

	/**
	 * @return the return value is the user
	 */
	public PlayerData getUser() {
		return user;
	}

	/**
	 * @return the return value holds all purchaseables of the user
	 */
	public ArrayList<PurchasableData> getClientPurchasable() {
		return purchasableHashMap.get(user.getId());
	}

	/**
	 * @return the return value holds all bought purchaseables for all player
	 */
	public HashMap<Integer, ArrayList<PurchasableData>> getPurchasableHashMap() {
		return purchasableHashMap;
	}

	/**
	 * @return the return value holds all player participating player
	 */
	public HashMap<Integer, PlayerData> getPlayerHashMap() {
		return playerHashMap;
	}

	//JAVADOC
	public ArrayList<MassageData> getMassageDataArrayList() {
		return massageDataArrayList;
	}

	public ArrayList<FieldData> getFieldDataArrayList() {
		return map;
	}

	/**
	 * <ul> <li>THROWDICE: the player can throw the dice for the first time</li> <li>THROWDICEAGAIN: the player can
	 * throw
	 * the dice again</li> <li>ENDTURN: the player can only end the turn</li> <li>DEACTIVATED: the player can't do
	 * anything</li> </ul>
	 */
	public enum TurnOptionState {
		THROWDICE, THROWDICEAGAIN, ENDTURN, DEACTIVATED
	}

	/**
	 * <ul> <li>PURCHASABLE: option to buy purchasable field</li> <li>BUYHOUSE: option to buy a house</li>
	 * <li>BUYHOTEL:
	 * option to buy a hotel</li> <li>DEACTIVATED: no option at all</li> </ul>
	 */
	public enum BuyOptionState {
		PURCHASABLE, BUYHOUSE, BUYHOTEL, DEACTIVATED
	}

	/**
	 * Types of ModelEvents
	 */
	public enum ModelEventName {
		DICE, ISINJAIL, MONEY, POSITION, TURNSTATE, THREWDICE, INMORTAGE, STAGE, PROPERTY, TURNOPTION, BUYOPTION,
		MAINPANEL, HAGGLE, CARD, FINANCIAL, PLAYERREMOVED
	}

	/**
	 * "name" of the current main panel
	 */
	public enum MainPanelName {
		GAME, MORTGAGE, HAGGLE, FINANCIAL
	}
}
