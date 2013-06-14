package ui.gui;

import core.ClientOperator;
import objects.events.ClientOperatorHaggleDataEvent;
import objects.events.ClientOperatorPlayerDataEvent;
import objects.events.ClientOperatorPurchasableDataEvent;
import objects.events.ModelEvent;
import objects.listeners.ClientOperatorHaggleDataEventListener;
import objects.listeners.ClientOperatorPlayerDataEventListener;
import objects.listeners.ClientOperatorPurchasableDataEventListener;
import objects.listeners.ModelEventListener;
import objects.value.InitializeMapData;
import objects.value.PlayerData;
import objects.value.action.HaggleData;
import objects.value.map.FieldData;
import objects.value.map.PurchasableData;
import objects.value.map.StreetData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Model holds all information that any ui will need.
 * It will fire following Model events:
 * <ul>
 * <li>DICE</li>
 * <li>ISINJAIL</li>
 * <li>MONEY</li>
 * <li>POSITION</li>
 * <li>TURNSTATE</li>
 * <li>THREWDICE</li>
 * <li>INTMORTAGE</li>
 * <li>STAGE</li>
 * <li>PROPERTY</li>
 * <li>TURNOPTION</li>
 * <li>BUYOPTION</li>
 * <li>MAINPANEL</li>
 * <li>HAGGLE</li>
 * </ul>
 */
public class Model {
	//Event listeners
	private final HashMap<ModelEventName, ArrayList<ModelEventListener>> listener;
	//Data
	private final ArrayList<FieldData>                                   map;
	private       HashMap<Integer, ArrayList<PurchasableData>>           purchasableHashMap;
	private       ArrayList<PlayerData>                                  playerArrayList;
	private       PlayerData                                             clientPlayer; //TODO change to int
	private       int                                                    oldMoney;
	//Action Data
	private       HaggleData                                             haggleData;
	//States
	private       CurrentMainPanelName                                   currentMainPanelName;
	//not necessary for the cui
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

		//Map
		map = initializeMapData.getMap();

		//States
		currentMainPanelName = CurrentMainPanelName.GAME;
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
				haggleData = new HaggleData(event.getHaggleData());
				fireModelEvent(ModelEventName.HAGGLE);
			}
		});
	}

	/**
	 * @param playerArrayList the value determines all players participate in the game
	 * @param clientId        the value determines the id of the player of the model
	 */
	public void setPlayer(ArrayList<PlayerData> playerArrayList, int clientId) {
		//Initialise lists for each player's purchasables
		purchasableHashMap = new HashMap<Integer, ArrayList<PurchasableData>>();
		for(PlayerData player : playerArrayList) {
			purchasableHashMap.put(player.getId(), new ArrayList<PurchasableData>());
		}

		//player
		this.playerArrayList = playerArrayList;

		//save player of this client
		clientPlayer = playerArrayList.get(clientId);
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
	 * If it is the player of the model the method will update the PlayerData  fire following ModelEvents:
	 * <ul>
	 * <li>DICE</li>
	 * <li>ISINJAIL</li>
	 * <li>MONEY</li>
	 * <li>POSITION</li>
	 * <li>TURNSTATE</li>
	 * <li>TURNOPTION</li>
	 * <li>BUYOPTION</li>
	 * </ul>
	 *
	 * @param playerData the value determines the PlayerData to be analysed
	 */
	private void analysePlayerData(PlayerData playerData) {
		if(playerData.equals(clientPlayer)) {
			if(clientPlayer.getFirstDice() != playerData.getFirstDice() ||
			   clientPlayer.getSecondDice() != playerData.getSecondDice()) {
				clientPlayer.setFirstDice(playerData.getFirstDice());
				clientPlayer.setSecondDice(playerData.getSecondDice());
				fireModelEvent(ModelEventName.DICE);
			}
			if(clientPlayer.isInJail() != playerData.isInJail()) {
				clientPlayer.setInJail(playerData.isInJail());
				fireModelEvent(ModelEventName.ISINJAIL);
			}
			if(clientPlayer.getMoney() != playerData.getMoney()) {
				oldMoney = clientPlayer.getMoney();
				clientPlayer.setMoney(playerData.getMoney());
				fireModelEvent(ModelEventName.MONEY);
			}
			if(!clientPlayer.getPosition().equals(playerData.getPosition())) {
				clientPlayer.setPosition(playerData.getPosition());

				fireModelEvent(ModelEventName.POSITION);
			}
			if(clientPlayer.isTurnEnd() != playerData.isTurnEnd()) {
				clientPlayer.setTurnEnd(playerData.isTurnEnd());
				fireModelEvent(ModelEventName.TURNSTATE);
			}
			if(clientPlayer.getThrewDice() != playerData.getThrewDice()) {
				clientPlayer.setThrewDice(playerData.getThrewDice());
			}
			if(clientPlayer.getTrading() != playerData.getTrading()) {
				clientPlayer.setTrading(playerData.getTrading());
			}
			updateTurnOption();
			updateBuyOption();
		}
	}

	/**
	 * the method will update the purchasable data and if the purchasable is or was owned by the player, or it is the
	 * current position of the player of the model it will fire either ModelEvent:
	 * <ul>
	 * <li>PROPERTY</li>
	 * <li>INTMORTAGE</li>
	 * <li>STAGE</li>
	 * <li>POSITION</li>
	 * </ul>
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

					if(i == clientPlayer.getId()) {
						fireModelEvent(ModelEventName.PROPERTY);
					}
				}

				if(!list.contains(purchasableData) && i == purchasableData.getOwner().getId()) {
					list.add(purchasableData);

					if(i == clientPlayer.getId()) {
						fireModelEvent(ModelEventName.PROPERTY);
					}
				}
			}
		} else if(purchasableData.getOwner().getId() == clientPlayer.getId()) {
			ArrayList<PurchasableData> clientPurchasable = purchasableHashMap.get(clientPlayer.getId());

			PurchasableData property = clientPurchasable.get(clientPurchasable.indexOf(purchasableData));

			if(property.isInMortgage() != purchasableData.isInMortgage()) {
				property.setInMortgage(purchasableData.isInMortgage());
				fireModelEvent(ModelEventName.INTMORTAGE);
			}
			if(property.getStage() != purchasableData.getStage()) {
				property.setStage(purchasableData.getStage());
				fireModelEvent(ModelEventName.STAGE);
			}
		}

		if(clientPlayer.getPosition().equals(purchasableData)) {
			clientPlayer.setPosition(purchasableData);
			updateBuyOption();
			fireModelEvent(ModelEventName.POSITION);
		}
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
	public CurrentMainPanelName getCurrentMainPanelName() {
		return currentMainPanelName;
	}

	/**
	 * @param currentMainPanelName the value determines the "name" of the current main panel
	 */
	public void setCurrentMainPanelName(CurrentMainPanelName currentMainPanelName) {
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
	 * @return the return value is the money of the client player
	 */
	public int getCurrentMoney() {
		return clientPlayer.getMoney();
	}

	/**
	 * @return the return value is the name of the field the client player ist standing on
	 */
	public String getFieldName() {
		return clientPlayer.getPosition().getName();
	}

	/**
	 * @return the return value is the first dice of the cleint player
	 */
	public int getFirstDice() {
		return clientPlayer.getFirstDice();
	}

	/**
	 * @return the return value is the second dice of the client player
	 */
	public int getSecondDice() {
		return clientPlayer.getSecondDice();
	}

	/**
	 * @return the return value is the previous amount of money from the client player
	 */
	public int getOldMoney() {
		return oldMoney;
	}

	/**
	 * @return the return value is null if the purchasable has no owner or is the name of the owner
	 */
	public String getPurchasableOwner() {
		if(clientPlayer.getPosition() instanceof PurchasableData) {
			if(((PurchasableData) clientPlayer.getPosition()).getOwner() != null) {
				return ((PurchasableData) clientPlayer.getPosition()).getOwner().getName();
			}
		}
		return null;
	}

	/**
	 * @return the return value is null if the purchasable has no owner or is the id of the owner
	 */
	public int getPurchasableOwnerId() {
		if(clientPlayer.getPosition() instanceof PurchasableData) {
			if(((PurchasableData) clientPlayer.getPosition()).getOwner() != null) {
				return ((PurchasableData) clientPlayer.getPosition()).getOwner().getId();
			}
		}
		return -1;
	}

	/**
	 * @return the return value is -1 if the client player is not standing on a purchasable field or its price
	 */
	public int getPurchasablePrice() {
		if(clientPlayer.getPosition() instanceof PurchasableData) {
			return ((PurchasableData) clientPlayer.getPosition()).getPRICE();
		} else {
			return -1;
		}
	}

	/**
	 * @return the return value is the stage of the street the client player is currently standing on or if there is no
	 *         stage -1
	 */
	public int getStreetStage() {
		if(clientPlayer.getPosition() instanceof StreetData) {
			return ((StreetData) clientPlayer.getPosition()).getStage();
		}
		return -1;
	}

	/**
	 * @return the return value is the price of a upgrade (house/hotel) of street or if it is not a street -1
	 */
	public int getStreetUpgrade() {
		if(clientPlayer.getPosition() instanceof StreetData) {
			return ((StreetData) clientPlayer.getPosition()).getUPGRADE();
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
		return clientPlayer.isInJail();
	}

	/**
	 * @return the return value is true if the player can throw a dice
	 */
	public boolean isMoveAction() {
		return turnOptionState == TurnOptionState.THROWDICE || turnOptionState == TurnOptionState.THROWDICEAGAIN;
	}

	/**
	 * @return the return value is true if the client player is standing on a purchasable field
	 */
	public boolean isPurchasable() {
		return clientPlayer.getPosition() instanceof PurchasableData;
	}

	/**
	 * @return the return value is true if the client player is standing on a street field
	 */
	public boolean isStreet() {
		return clientPlayer.getPosition() instanceof StreetData;
	}

	/**
	 * @param listener the value determines the listener to be removed
	 */
	public void removeModelEventListener(ModelEventName eventName, ModelEventListener listener) {
		this.listener.remove(eventName).add(listener);
	}

	/**
	 * The method changes the buyOptionState to PURCHASABLE if the player can buy the field,
	 * to BUYHOUSE if he can buy a house, to BUYHOTEL if he can buy a hotel or DEACTIVATED if he can't buy anything.
	 * Will fire a ModelEvent
	 */
	private void updateBuyOption() {
		boolean active = false;
		if(clientPlayer.getPosition() instanceof PurchasableData) {
			if(((PurchasableData) clientPlayer.getPosition()).getOwner() == null) {
				buyOptionState = BuyOptionState.PURCHASABLE;
				active = true;
			} else if(clientPlayer.getPosition() instanceof StreetData) {
				StreetData street = (StreetData) clientPlayer.getPosition();
				if(street.getOwner().getId() == clientPlayer.getId()) {
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
	 * The methos will change turnOptionState to THROWDICE if the player starts his turn, to THROWDICEAGAIN if he got a
	 * double, to ENDTURN if he can only end his turn or deactivated if he cant do anything
	 */
	private void updateTurnOption() {
		if(!clientPlayer.isTurnEnd()) {
			if(clientPlayer.getThrewDice() == 0) {
				turnOptionState = TurnOptionState.THROWDICE;
			} else if(clientPlayer.getFirstDice() == clientPlayer.getSecondDice()) {
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
	 * @return the return value is the client player
	 */
	public PlayerData getClientPlayer() {
		return clientPlayer;
	}

	/**
	 * @return the return value holds all purchaseables of the client player
	 */
	public ArrayList<PurchasableData> getClientPurchasable() {
		return purchasableHashMap.get(clientPlayer.getId());
	}

	/**
	 * @return the return value holds all boughts purchaseables for all player
	 */
	public HashMap<Integer, ArrayList<PurchasableData>> getPurchasableHashMap() {
		return purchasableHashMap;
	}

	/**
	 * @return the return value holds all player participating player
	 */
	public ArrayList<PlayerData> getPlayerArrayList() {
		return playerArrayList;
	}

	/**
	 * <ul>
	 * <li>THROWDICE: the player can throw the dice for the first time</li>
	 * <li>THROWDICEAGAIN: the player can throw the dice again</li>
	 * <li>ENDTURN: the player can only end the turn</li>
	 * <li>DEACTIVATED: the player can't do anything</li>
	 * </ul>
	 */
	public enum TurnOptionState {
		THROWDICE, THROWDICEAGAIN, ENDTURN, DEACTIVATED
	}

	/**
	 * <ul>
	 * <li>PURCHASABLE: option to buy purchasable field</li>
	 * <li>BUYHOUSE: option to buy a house</li>
	 * <li>BUYHOTEL: option to buy a hotel</li>
	 * <li>DEACTIVATED: no option at all</li>
	 * </ul>
	 */
	public enum BuyOptionState {
		PURCHASABLE, BUYHOUSE, BUYHOTEL, DEACTIVATED
	}

	/**
	 * Types of ModelEvents
	 */
	public enum ModelEventName {
		DICE, ISINJAIL, MONEY, POSITION, TURNSTATE, THREWDICE, INTMORTAGE, STAGE, PROPERTY, TURNOPTION, BUYOPTION,
		MAINPANEL, HAGGLE
	}

	/**
	 * "name" of the current main panel
	 */
	public enum CurrentMainPanelName {
		GAME, MORTGAGE, HAGGLE
	}
}
