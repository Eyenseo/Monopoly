package core;

import objects.Player;
import objects.card.Jailbait;
import objects.map.FieldCircularList;
import objects.map.purchasable.PurchasableCircularList;
import objects.map.purchasable.Street;
import objects.value.action.*;

import java.util.ArrayList;
import java.util.HashMap;

//JAVADOC
//TODO find a better / less costly way to have "something like" a Thread
public class ActionThread extends Thread {
	private HashMap<Integer, Player>                        playerHashMap;
	private ActionData                                      actionData;
	private ServerOperator                                  serverOperator;
	private FieldCircularList                               jail;
	private Thread                                          turnThread;
	private HashMap<Integer, HashMap<String, ActionThread>> actions;

	/**
	 * <b><i>Taken of the official JavaDoc</i></b> Allocates a new {@code Thread} object. This constructor has the same
	 * effect as {@linkplain #ActionThread(ThreadGroup, Runnable, String, HashMap, ActionData, ServerOperator,
	 * FieldCircularList, Thread, HashMap) Thread} {@code (null, null, gname)}, where {@code gname} is a newly generated
	 * name. Automatically generated names are of the form {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
	 */
	//JAVADOC
	public ActionThread(HashMap<Integer, Player> playerHashMap, ActionData actionData, ServerOperator serverOperator,
	                    FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		this.playerHashMap = playerHashMap;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	/**
	 * <b><i>Taken of the official JavaDoc</i></b> Allocates a new {@code Thread} object. This constructor has the same
	 * effect as {@linkplain #ActionThread(ThreadGroup, Runnable, String, HashMap, ActionData, ServerOperator,
	 * FieldCircularList, Thread, HashMap) Thread} {@code (null, target, gname)}, where {@code gname} is a newly generated
	 * name. Automatically generated names are of the form {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
	 *
	 * @param target the object whose {@code run} method is invoked when this thread is started. If {@code null}, this
	 *               classes {@code run} method does nothing.
	 */
	//JAVADOC
	public ActionThread(Runnable target, HashMap<Integer, Player> playerHashMap, ActionData actionData,
	                    ServerOperator serverOperator, FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		super(target);
		this.playerHashMap = playerHashMap;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	/**
	 * <b><i>Taken of the official JavaDoc</i></b> Allocates a new {@code Thread} object. This constructor has the same
	 * effect as {@linkplain #ActionThread(ThreadGroup, Runnable, String, HashMap, ActionData, ServerOperator,
	 * FieldCircularList, Thread, HashMap) Thread} {@code (group, target, gname)} , where {@code gname} is a newly
	 * generated name. Automatically generated names are of the form {@code "Thread-"+}<i>n</i>, where <i>n</i> is an
	 * integer.
	 *
	 * @param group  the thread group. If {@code null} and there is a security manager, the group is determined by
	 *               {@linkplain SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}. If there is not a
	 *               security manager or {@code SecurityManager.getThreadGroup()} returns {@code null},
	 *               the group is set to
	 *               the current thread's thread group.
	 * @param target the object whose {@code run} method is invoked when this thread is started. If {@code null}, this
	 *               thread's run method is invoked.
	 * @throws SecurityException if the current thread cannot create a thread in the specified thread group
	 */
	//JAVADOC
	public ActionThread(ThreadGroup group, Runnable target, HashMap<Integer, Player> playerHashMap,
	                    ActionData actionData, ServerOperator serverOperator, FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		super(group, target);
		this.playerHashMap = playerHashMap;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	/**
	 * <b><i>Taken of the official JavaDoc</i></b> Allocates a new {@code Thread} object. This constructor has the same
	 * effect as {@linkplain #ActionThread(ThreadGroup, Runnable, String, HashMap, ActionData, ServerOperator,
	 * FieldCircularList, Thread, HashMap) Thread} {@code (null, null, name)}.
	 *
	 * @param name the name of the new thread
	 */
	//JAVADOC
	public ActionThread(String name, HashMap<Integer, Player> playerHashMap, ActionData actionData,
	                    ServerOperator serverOperator, FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		super(name);
		this.playerHashMap = playerHashMap;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	/**
	 * <b><i>Taken of the official JavaDoc</i></b> Allocates a new {@code Thread} object. This constructor has the same
	 * effect as {@linkplain #ActionThread(ThreadGroup, Runnable, String, HashMap, ActionData, ServerOperator,
	 * FieldCircularList, Thread, HashMap) Thread} {@code (group, null, name)}.
	 *
	 * @param group the thread group. If {@code null} and there is a security manager, the group is determined by
	 *              {@linkplain SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}. If there is not a
	 *              security manager or {@code SecurityManager.getThreadGroup()} returns {@code null}, the group is set to
	 *              the current thread's thread group.
	 * @param name  the name of the new thread
	 * @throws SecurityException if the current thread cannot create a thread in the specified thread group
	 */
	//JAVADOC
	public ActionThread(ThreadGroup group, String name, HashMap<Integer, Player> playerHashMap, ActionData actionData,
	                    ServerOperator serverOperator, FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		super(group, name);
		this.playerHashMap = playerHashMap;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	/**
	 * <b><i>Taken of the official JavaDoc</i></b> Allocates a new {@code Thread} object. This constructor has the same
	 * effect as {@linkplain #ActionThread(ThreadGroup, Runnable, String, HashMap, ActionData, ServerOperator,
	 * FieldCircularList, Thread, HashMap) Thread} {@code (null, target, name)}.
	 *
	 * @param target the object whose {@code run} method is invoked when this thread is started. If {@code null}, this
	 *               thread's run method is invoked.
	 * @param name   the name of the new thread
	 */
	//JAVADOC
	public ActionThread(Runnable target, String name, HashMap<Integer, Player> playerHashMap, ActionData actionData,
	                    ServerOperator serverOperator, FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		super(target, name);
		this.playerHashMap = playerHashMap;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	/**
	 * <b><i>Taken of the official JavaDoc</i></b> Allocates a new {@code Thread} object so that it has {@code target} as
	 * its run object, has the specified {@code name} as its name, and belongs to the thread group referred to by {@code
	 * group}.
	 * <p/>
	 * <p>If there is a security manager, its {@link SecurityManager#checkAccess(ThreadGroup) checkAccess} method is
	 * invoked with the ThreadGroup as its argument.
	 * <p/>
	 * <p>In addition, its {@code checkPermission} method is invoked with the {@code RuntimePermission
	 * ("enableContextClassLoaderOverride")} permission when invoked directly or indirectly by the constructor of a
	 * subclass which overrides the {@code getContextClassLoader} or {@code setContextClassLoader} methods.
	 * <p/>
	 * <p>The priority of the newly created thread is set equal to the priority of the thread creating it, that is, the
	 * currently running thread. The method {@linkplain #setPriority setPriority} may be used to change the priority to a
	 * new value.
	 * <p/>
	 * <p>The newly created thread is initially marked as being a daemon thread if and only if the thread creating it is
	 * currently marked as a daemon thread. The method {@linkplain #setDaemon setDaemon} may be used to change whether or
	 * not a thread is a daemon.
	 *
	 * @param group  the thread group. If {@code null} and there is a security manager, the group is determined by
	 *               {@linkplain SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}. If there is not a
	 *               security manager or {@code SecurityManager.getThreadGroup()} returns {@code null},
	 *               the group is set to
	 *               the current thread's thread group.
	 * @param target the object whose {@code run} method is invoked when this thread is started. If {@code null}, this
	 *               thread's run method is invoked.
	 * @param name   the name of the new thread
	 * @throws SecurityException if the current thread cannot create a thread in the specified thread group or cannot
	 *                           override the context class loader methods.
	 */
	//JAVADOC
	public ActionThread(ThreadGroup group, Runnable target, String name, HashMap<Integer, Player> playerHashMap,
	                    ActionData actionData, ServerOperator serverOperator, FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		super(group, target, name);
		this.playerHashMap = playerHashMap;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	/**
	 * <b><i>Taken of the official JavaDoc</i></b> Allocates a new {@code Thread} object so that it has {@code target} as
	 * its run object, has the specified {@code name} as its name, and belongs to the thread group referred to by {@code
	 * group}, and has the specified <i>stack size</i>.
	 * <p/>
	 * <p>This constructor is identical to {@link #ActionThread(ThreadGroup, Runnable, String, HashMap, ActionData,
	 * ServerOperator, FieldCircularList, Thread, HashMap)}with the exception of the fact that it allows the thread stack
	 * size to be specified.  The stack size is the approximate number of bytes of address space that the virtual machine
	 * is to allocate for this thread's stack.  <b>The effect of the {@code stackSize} parameter, if any, is highly
	 * platform dependent.</b>
	 * <p/>
	 * <p>On some platforms, specifying a higher value for the {@code stackSize} parameter may allow a thread to achieve
	 * greater recursion depth before throwing a {@link StackOverflowError}. Similarly, specifying a lower value may allow
	 * a greater number of threads to exist concurrently without throwing an {@link OutOfMemoryError} (or other internal
	 * error).  The details of the relationship between the value of the <tt>stackSize</tt> parameter and the maximum
	 * recursion depth and concurrency level are platform-dependent.  <b>On some platforms, the value of the {@code
	 * stackSize} parameter may have no effect whatsoever.</b>
	 * <p/>
	 * <p>The virtual machine is free to treat the {@code stackSize} parameter as a suggestion.  If the specified value is
	 * unreasonably low for the platform, the virtual machine may instead use some platform-specific minimum value; if the
	 * specified value is unreasonably high, the virtual machine may instead use some platform-specific maximum. Likewise,
	 * the virtual machine is free to round the specified value up or down as it sees fit (or to ignore it completely).
	 * <p/>
	 * <p>Specifying a value of zero for the {@code stackSize} parameter will cause this constructor to behave exactly
	 * like
	 * the {@code Thread(ThreadGroup, Runnable, String)} constructor.
	 * <p/>
	 * <p><i>Due to the platform-dependent nature of the behavior of this constructor, extreme care should be exercised in
	 * its use. The thread stack size necessary to perform a given computation will likely vary from one JRE
	 * implementation
	 * to another.  In light of this variation, careful tuning of the stack size parameter may be required, and the tuning
	 * may need to be repeated for each JRE implementation on which an application is to run.</i>
	 * <p/>
	 * <p>Implementation note: Java platform implementers are encouraged to document their implementation's behavior with
	 * respect to the {@code stackSize} parameter.
	 *
	 * @param group     the thread group. If {@code null} and there is a security manager, the group is determined by
	 *                  {@linkplain SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}. If there is not a
	 *                  security manager or {@code SecurityManager.getThreadGroup()} returns {@code null},
	 *                  the group is set
	 *                  to the current thread's thread group.
	 * @param target    the object whose {@code run} method is invoked when this thread is started. If {@code null}, this
	 *                  thread's run method is invoked.
	 * @param name      the name of the new thread
	 * @param stackSize the desired stack size for the new thread, or zero to indicate that this parameter is to be
	 *                  ignored.
	 * @throws SecurityException if the current thread cannot create a thread in the specified thread group
	 * @since 1.4
	 */
	//JAVADOC
	public ActionThread(ThreadGroup group, Runnable target, String name, long stackSize,
	                    HashMap<Integer, Player> playerHashMap, ActionData actionData, ServerOperator serverOperator,
	                    FieldCircularList jail, Thread turnThread,
	                    HashMap<Integer, HashMap<String, ActionThread>> actions) {
		super(group, target, name, stackSize);
		this.playerHashMap = playerHashMap;
		this.actionData = actionData;
		this.serverOperator = serverOperator;
		this.jail = jail;
		this.turnThread = turnThread;
		this.actions = actions;
	}

	@Override public void run() {
		Player user = playerHashMap.get(actionData.getUserId());

		if(actionData instanceof TurnData) {
			turnAction(user, (TurnData) actionData);
		} else if(actionData instanceof BuyData) {
			buyAction(user, (BuyData) actionData);
		} else if(actionData instanceof HaggleData) {
			haggleAction(user, (HaggleData) actionData);
		} else if(actionData instanceof MortgageData) {
			mortgageAction(user, (MortgageData) actionData);
		} else if(actionData instanceof PlayerStatusData) {
			playerStatusAction(user, (PlayerStatusData) actionData);
		}
		actions.get(actionData.getUserId()).remove(actionData.getId());
	}

	//JAVADOC
	private void turnAction(Player user, TurnData data) {
		synchronized(turnThread) {
			if(data.isTurnAction()) {
				if(user.getThrewDice() < 3) {
					user.move();
				} else {
					user.setPosition(jail);
					user.setInJail(true);
					user.setTurnEnd(true);
					turnThread.notify();
				}
			} else {
				user.setTurnEnd(true);
				turnThread.notify();
			}
		}
	}

	//JAVADOC
	private void buyAction(Player user, BuyData actionData) {
		if(user.getPosition() instanceof PurchasableCircularList) { //TODO check if this is enough
			if(actionData.isUpgrade()) {
				((Street) user.getPosition()).nextStage();
			} else {
				user.buyPurchasable((PurchasableCircularList) user.getPosition());
			}
		}
	}

	//JAVADOC
	private void haggleAction(Player user, HaggleData actionData) {
		Player seller;

		System.out.println("User Id: " + actionData.getUserId() + " with Seller Id: " + actionData.getSellerId());

		switch(actionData.getHaggleState()) {
			case ESTABLISH:
				seller = playerHashMap.get(actionData.getSellerId());
				if(user.getTrading() == -1 && seller.getTrading() == -1) {
					user.setTrading(seller.getPlayerId());
					seller.setTrading(user.getPlayerId());
					actionData.setHaggleState(HaggleData.HaggleState.ESTABLISHED);
				} else {
					actionData.setHaggleState(HaggleData.HaggleState.DECLINE);
				}
				serverOperator.sendHaggleData(actionData);
				break;

			// case ESTABLISHED: //Will never happen - this state will just appear on the client
			// break;

			case REQUEST:
				serverOperator.sendHaggleData(actionData);
				break;

			case OFFER:
				serverOperator.sendHaggleData(actionData);
				break;

			case ACCEPT:
				seller = playerHashMap.get(actionData.getSellerId());
				// trade money
				int playerPay = actionData.getSellerMoney() - actionData.getUserMoney();
				if(playerPay != 0) {
					if(playerPay < 0) {
						playerPay *= -1;
						seller.pay(playerPay);
						user.addMoney(playerPay);
					} else {
						seller.addMoney(playerPay);
						user.pay(playerPay);
					}
				}
				// trade purchasables
				for(int id : actionData.getSellerFieldIds()) {
					for(PurchasableCircularList purchasable : user.getProperties()) {
						if(purchasable.getFieldNumber() == id) {
							purchasable.setOwner(seller);
							break;
						}
					}
				}
				for(int id : actionData.getUserFieldIds()) {
					for(PurchasableCircularList purchasable : seller.getProperties()) {
						if(purchasable.getFieldNumber() == id) {
							purchasable.setOwner(user);
							break;
						}
					}
				}
				serverOperator.sendHaggleData(actionData);
				user.setTrading(-1);
				seller.setTrading(-1);
				break;

			case DECLINE:
				seller = playerHashMap.get(actionData.getSellerId());

				user.setTrading(-1);
				seller.setTrading(-1);

				serverOperator.sendHaggleData(actionData);
				break;
		}
	}

	//JAVADOC
	private void mortgageAction(Player user, MortgageData actionData) {
		ArrayList<PurchasableCircularList> purchasableCircularListArrayList = user.getProperties();
		for(PurchasableCircularList purchasable : purchasableCircularListArrayList) {
			if(purchasable.getFieldNumber() == actionData.getFieldId()) {
				purchasable.setInMortgage(!purchasable.isInMortgage());
				break;
			}
		}
	}

	//JAVADOC
	private void playerStatusAction(Player user, PlayerStatusData actionData) {
		switch(actionData.getStatus()) {
			case GIVEUP:
				removePlayer(user, actionData);
				break;
			case FINANCIAL:
				for(Thread thread : actions.get(actionData.getUserId()).values()) {
					synchronized(thread) {
						if(thread.getState() == Thread.State.WAITING) {
							thread.notify(); //notify all waiting threads
						}
					}
				}
				break;
		}
	}

	//JAVADOC
	private void removePlayer(Player user, PlayerStatusData actionData) {
		//Cancel all trades that the player had - upon receiving player give up the client will remove the player
		// anyway
		for(ActionThread thread : actions.get(actionData.getUserId()).values()) {
			synchronized(thread) {
				if(thread.getActionData() instanceof HaggleData) {
					HaggleData haggleData = (HaggleData) thread.getActionData();
					haggleData.setHaggleState(HaggleData.HaggleState.DECLINE);
					serverOperator.sendHaggleData(haggleData);
				}
			}
		}
		//Set player to giveUp - a player event will be fired an the clients will be informed
		user.setGiveUp(true);
		//Remove from Server
		serverOperator.removeDestination(actionData.getUserId());
		//Set purchasables to be buyable again
		for(PurchasableCircularList property : user.getProperties()) {
			property.setOwner(null);
		}
		//Put all cards back on the Cardstack
		for(Jailbait card : user.getJailbait()) {
			card.putBack();
		}
		//remove Player
		playerHashMap.remove(user.getPlayerId());
		//remove action HashMap
		actions.remove(user.getPlayerId());
	}

	//JAVADOC
	public ActionData getActionData() {
		return actionData;
	}
}
