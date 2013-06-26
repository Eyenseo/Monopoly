package ui.gui.components;

import core.ClientOperator;
import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import objects.value.PlayerData;
import objects.value.action.HaggleData;
import objects.value.map.PurchasableData;
import objects.value.map.StationData;
import objects.value.map.StreetData;
import ui.gui.Model;
import ui.gui.layout_manager.PurchasableCardLayout;
import ui.gui.layout_manager.PurchasableCardLayoutConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The HagglePanel provides the user with the options to trade with other players
 */
public class HagglePanel extends JPanel {
	private final Model             model;
	private final ClientOperator    clientOperator;
	private       JPanel            sellerContent;
	private       JPanel            userContent;
	private       JSplitPane        hagglePane;
	private       JPanel            headerPanel;
	private       JPanel            basePanel;
	private       JButton           accept;
	private       JTextField        tradeSellerMoney;
	private       JTextField        tradeUserMoney;
	private       JComboBox<String> playerList;

	/**
	 * @param model          The value determines the Model the ControlPanel will get its information from
	 * @param clientOperator The value determines the ClientOperator, that will send ActionData to the server
	 */
	public HagglePanel(Model model, ClientOperator clientOperator) {
		super(new BorderLayout());
		this.model = model;
		this.clientOperator = clientOperator;
		createHeader();
		createHagglePane();
		createBase();
		createPlayerContent();

		add(headerPanel, BorderLayout.NORTH);
		add(hagglePane, BorderLayout.CENTER);
		add(basePanel, BorderLayout.SOUTH);

		model.addModelEventListener(Model.ModelEventName.HAGGLE, haggleListener());
	}

	/**
	 * @return The return value is a listener that will update the HagglePanel accordingly to the HaggleData of the
	 *         model.
	 */
	private ModelEventListener haggleListener() {
		return new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				HaggleData haggleData = model.getHaggleData();
				if(haggleData != null) {
					switch(haggleData.getHaggleState()) {
						// case ESTABLISH: //Will never happen - this state will just appear on the server
						case ESTABLISHED:
							if(haggleData.getUserId() == model.getUser().getId()) {
								playerList.setEnabled(false);
								accept.setEnabled(true);
								tradeUserMoney.setEditable(true);

								setSeller(haggleData.getSellerId());
							} else {
								int sellerId = haggleData.getSellerId();
								if(sellerId >= model.getUser().getId()) {
									sellerId--;
								}
								playerList.setSelectedIndex(sellerId);
								playerList.setEnabled(false);

								accept.setText("Angebot");
							}
							break;

						case REQUEST:
							if(haggleData.getSellerId() == model.getUser().getId()) {
								accept.setEnabled(true);

								setSeller(haggleData.getUserId());
								markPlayerPurchasable(haggleData.getUserFieldIds());
								tradeUserMoney.setEditable(true);

								tradeSellerMoney.setText("" + haggleData.getUserMoney());
								model.setCurrentMainPanelName(Model.CurrentMainPanelName.HAGGLE);
							}
							break;

						case OFFER:
							if(haggleData.getUserId() == model.getUser().getId()) {
								accept.setEnabled(true);

								markPlayerPurchasable(haggleData.getSellerFieldIds());
								tradeSellerMoney.setText("" + haggleData.getSellerMoney());

								accept.setText("Akzeptieren");
							}
							break;

						case ACCEPT:
						case DECLINE:
							accept.setEnabled(false);
							accept.setText("Anfrage");
							playerList.setEnabled(true);
							tradeUserMoney.setText("0");
							tradeUserMoney.setEditable(false);
							tradeSellerMoney.setText("0");
							sellerContent.removeAll();
							unmarkPlayerPurchasable();
							repaint();
							break;
					}
				}
			}
		};
	}

	/**
	 * The method will remove the borders from the property from the user purchasables.
	 */
	private void unmarkPlayerPurchasable() {
		for(Component panel : userContent.getComponents()) {
			if(panel instanceof PurchasableCardPanel) {
				((PurchasableCardPanel) panel).setBorder(null);
			}
		}
	}

	/**
	 * The method will add a red border to the PurchasableCardPanels that are included in the trade
	 *
	 * @param purchasableDataList The value determines the list of purchasable ids to check against the
	 *                            PurchasableCardPanel against to set their borders
	 */
	private void markPlayerPurchasable(ArrayList<Integer> purchasableDataList) {
		for(int id : purchasableDataList) {
			for(Component panel : userContent.getComponents()) {
				if(panel instanceof PurchasableCardPanel) {
					if(((PurchasableCardPanel) panel).getData().getFieldNumber() == id) {
						((PurchasableCardPanel) panel)
								.setBorder(BorderFactory.createLineBorder(new Color(218, 0, 0), 3));
					}
				}
			}
		}
	}

	/**
	 * The method will create a Panel with a drop down menu where the user can select a player to trade with and a
	 * indication which side is his
	 */
	private void createHeader() {
		ArrayList<PlayerData> playerDataArrayList = model.getPlayerArrayList();
		String[] playerNames = new String[playerDataArrayList.size() - 1];
		for(int i = 0, j = 0; i < playerDataArrayList.size(); i++) {
			if(i != model.getUser().getId()) {
				playerNames[j] = playerDataArrayList.get(i).getName();
				j++;
			}
		}

		playerList = new JComboBox<String>(playerNames);
		playerList.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				HaggleData haggleData = model.getHaggleData();
				if(haggleData == null || haggleData.getHaggleState() == HaggleData.HaggleState.ACCEPT ||
				   haggleData.getHaggleState() == HaggleData.HaggleState.DECLINE) {
					JComboBox playerList = (JComboBox) e.getSource();
					int index = playerList.getSelectedIndex();
					if(index >= model.getUser().getId()) {
						index++;
					}
					model.setHaggleData(new HaggleData(model.getUser().getId(), index));
					clientOperator.sendActionData(model.getHaggleData());
					playerList.setEnabled(false);
				}
			}
		});

		JTextField userIdentifier = new JTextField("Sie");
		userIdentifier.setEditable(false);
		userIdentifier.setBorder(null);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playerList, userIdentifier);
		splitPane.setResizeWeight(0.5);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(0);

		headerPanel = new JPanel(new GridLayout());
		headerPanel.add(splitPane);
	}

	/**
	 * The method is a work around without the split view doesn't splits in the center
	 */
	@Override protected void paintComponent(Graphics g) {
		//TODO find a better fix for decenter on first opening
		hagglePane.setDividerLocation(0.5);

		hagglePane.validate();
		userContent.setSize(new Dimension(hagglePane.getRightComponent().getWidth(), userContent.getHeight()));
		hagglePane.validate();

		super.paintComponent(g);
	}

	/**
	 * The method will show the purchasable field that the seller owns
	 *
	 * @param sellerId The value determines the id of the player the user is trading with
	 */
	private void setSeller(int sellerId) {
		sellerContent.removeAll();
		PurchasableCardLayoutConstraints constraints = new PurchasableCardLayoutConstraints();

		for(PurchasableData data : model.getPurchasableHashMap().get(sellerId)) {
			PurchasableCardPanel panel = new PurchasableCardPanel(data);

			panel.addMouseListener(new MouseAdapter() {
				/**
				 * The method will add/remove the clicked panel to the trade and set/remove the border of the
				 * PurchasableCardPanel
				 * @param e The value determines the event source
				 */
				@Override public void mouseClicked(MouseEvent e) {
					HaggleData haggleData = model.getHaggleData();
					HaggleData.HaggleState state = haggleData.getHaggleState();
					if(state == HaggleData.HaggleState.ESTABLISHED || state == HaggleData.HaggleState.REQUEST) {
						PurchasableCardPanel panel = (PurchasableCardPanel) e.getSource();
						if(panel.getBorder() == null) {
							panel.setBorder(BorderFactory.createLineBorder(new Color(0, 156, 0), 3));
							if(haggleData.getUserId() == model.getUser().getId() &&
							   state == HaggleData.HaggleState.ESTABLISHED) {
								haggleData.getUserFieldIds().add(panel.getData().getFieldNumber());
							} else if(haggleData.getSellerId() == model.getUser().getId()) {

								haggleData.getSellerFieldIds().add(panel.getData().getFieldNumber());
							}
						} else {
							panel.setBorder(null);
							if(haggleData.getUserId() == model.getUser().getId() &&
							   state == HaggleData.HaggleState.ESTABLISHED) {
								haggleData.getUserFieldIds().remove((Integer) panel.getData().getFieldNumber());
							} else if(haggleData.getSellerId() == model.getUser().getId()) {

								haggleData.getSellerFieldIds().remove((Integer) panel.getData().getFieldNumber());
							}
						}
					}
				}
			});

			if(data instanceof StreetData) {
				constraints.type = PurchasableCardLayoutConstraints.PurchasableCardLayoutConstraintType.STREET;
			} else if(data instanceof StationData) {
				constraints.type = PurchasableCardLayoutConstraints.PurchasableCardLayoutConstraintType.STATION;
			} else {
				constraints.type = PurchasableCardLayoutConstraints.PurchasableCardLayoutConstraintType.FACILITY;
			}
			constraints.position = data.getFieldNumber();
			sellerContent.add(panel, constraints);
		}
		repaint();
	}

	/**
	 * The method will show the purchasable fields that the user owns
	 */
	private void createPlayerContent() {
		ModelEventListener listener = new ModelEventListener() {
			/**
			 * The method will add any new purchasabels to the panel that the user buys and removes all the the user
			 * doesn't own anymore
			 * @param event the value determines the event source
			 */
			@Override public void actionPerformed(ModelEvent event) {
				PurchasableCardLayoutConstraints constraints = new PurchasableCardLayoutConstraints();
				boolean change;

				//add new components
				for(PurchasableData data : model.getClientPurchasable()) {
					change = true;
					for(Component panel : userContent.getComponents()) {
						if(panel instanceof PurchasableCardPanel) {
							if(((PurchasableCardPanel) panel).getData().equals(data)) {
								change = false;
							}
						}
					}

					if(change) {
						if(data instanceof StreetData) {
							constraints.type =
									PurchasableCardLayoutConstraints.PurchasableCardLayoutConstraintType.STREET;
						} else if(data instanceof StationData) {
							constraints.type =
									PurchasableCardLayoutConstraints.PurchasableCardLayoutConstraintType.STATION;
						} else {
							constraints.type =
									PurchasableCardLayoutConstraints.PurchasableCardLayoutConstraintType.FACILITY;
						}
						constraints.position = data.getFieldNumber();

						userContent.add(new PurchasableCardPanel(data), constraints);
					}
				}

				//remove new components
				for(Component panel : userContent.getComponents()) {
					if(panel instanceof PurchasableCardPanel) {
						change = true;
						for(PurchasableData data : model.getClientPurchasable()) {
							if(((PurchasableCardPanel) panel).getData().equals(data)) {
								change = false;
							}
						}

						if(change) {
							userContent.remove(panel);
						}
					}
				}
			}
		};

		model.addModelEventListener(Model.ModelEventName.PROPERTY, listener);
		model.addModelEventListener(Model.ModelEventName.STAGE, listener);
		model.addModelEventListener(Model.ModelEventName.INMORTAGE, listener);
	}

	/**
	 * The method will create the HagglePane which holds the sellerContentPanel and the userContentPanel
	 */
	private void createHagglePane() {
		hagglePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		hagglePane.setDividerSize(0);
		hagglePane.setDividerLocation(0.5);
		hagglePane.setResizeWeight(0.5);
		hagglePane.setEnabled(false);

		hagglePane.setLeftComponent(createSellerContentPanel());
		hagglePane.setRightComponent(createUserContentPanel());
	}

	/**
	 * The method will create a ScrollPane that holds the sellerContentPanel
	 *
	 * @return The return value holds the sellerContentPanel
	 */
	private JScrollPane createSellerContentPanel() {
		sellerContent = new JPanel();
		sellerContent.setLayout(new PurchasableCardLayout());

		JScrollPane scrollPane = new JScrollPane(sellerContent);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		return scrollPane;
	}

	/**
	 * The method will create a ScrollPane that holds the userContentPanel
	 *
	 * @return The return value holds the userContentPanel
	 */
	private JScrollPane createUserContentPanel() {
		userContent = new JPanel();
		userContent.setLayout(new PurchasableCardLayout());

		JScrollPane scrollPane = new JScrollPane(userContent);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		return scrollPane;
	}

	/**
	 * The method will create the basePanel with a textArea that displays the money to be payed by the player, a
	 * TextArea where the user can type how much money he wants, a button to precede to the next stage of the trade
	 * and a button to cancel the trade
	 */
	private void createBase() {
		basePanel = new JPanel();
		basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.PAGE_AXIS));

		JPanel layerWrapper = new JPanel(new GridLayout(2, 1));

		//TextArea
		JTextField sellerText = new JTextField("Sie zahlen:");
		sellerText.setEditable(false);
		sellerText.setBorder(null);

		tradeSellerMoney = new JTextField("0");
		tradeSellerMoney.setEditable(false);
		tradeSellerMoney.setBorder(null);

		JTextField userText = new JTextField("Sie bekommen:");
		userText.setEditable(false);
		userText.setBorder(null);

		tradeUserMoney = new JTextField();
		tradeUserMoney.setDocument(new IntegerDocument());
		tradeUserMoney.addFocusListener(new FocusListener() {
			@Override public void focusGained(FocusEvent e) {
				if(tradeUserMoney.getText().equals("0")) {
					tradeUserMoney.setText("");
				}
			}

			@Override public void focusLost(FocusEvent e) {
				if(tradeUserMoney.getText().equals("")) {
					tradeUserMoney.setText("0");
				}
			}
		});
		tradeUserMoney.setText("0");
		tradeUserMoney.setEditable(false);

		// place text fields in one line
		JPanel moneyTextWrapper = new JPanel(new GridLayout(1, 4));
		moneyTextWrapper.add(sellerText);
		moneyTextWrapper.add(tradeSellerMoney);
		moneyTextWrapper.add(userText);
		moneyTextWrapper.add(tradeUserMoney);

		//Buttons
		JButton back = new JButton("Abbrechen");
		back.addActionListener(new ActionListener() {
			/**
			 *     The method will send the cancellation of the trade to the server
			 * @param e The value determines the event source
			 */
			@Override public void actionPerformed(ActionEvent e) {
				if(model.getHaggleData() != null) {
					model.getHaggleData().setHaggleState(HaggleData.HaggleState.DECLINE);
					clientOperator.sendActionData(model.getHaggleData());
				}
				model.setCurrentMainPanelName(Model.CurrentMainPanelName.GAME);
			}
		});

		accept = new JButton("Anfrage");
		accept.setEnabled(false);
		accept.addActionListener(new ActionListener() {
			/**
			 * The method will set the HaggleData state to the next stage and sends the data to the server
			 * @param e The value determines the event source
			 */
			@Override public void actionPerformed(ActionEvent e) {
				HaggleData haggleData = model.getHaggleData();
				switch(haggleData.getHaggleState()) {
					// case ESTABLISH:   //Will never happen - this state will just appear on the server
					case ESTABLISHED:
						haggleData.setHaggleState(HaggleData.HaggleState.REQUEST);
						haggleData.setUserMoney(Integer.parseInt(tradeUserMoney.getText()));
						break;
					case REQUEST:
						haggleData.setHaggleState(HaggleData.HaggleState.OFFER);
						haggleData.setSellerMoney(Integer.parseInt(tradeUserMoney.getText()));
						break;
					case OFFER:
						haggleData.setHaggleState(HaggleData.HaggleState.ACCEPT);
						break;
					// case ACCEPT:    //Will never happen - this state will just appear on the server
					// case DECLINE:   //Will never happen - this state will just appear on the server
				}
				tradeUserMoney.setEditable(false);
				accept.setEnabled(false);
				clientOperator.sendActionData(model.getHaggleData());
			}
		});

		//place buttons next to each other with space
		JPanel buttonWrapper = new JPanel();
		buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.LINE_AXIS));
		buttonWrapper.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		buttonWrapper.add(accept);
		buttonWrapper.add(Box.createHorizontalStrut(10)); //Space between buttons
		buttonWrapper.add(back);

		//place buttonWrapper at the right side
		JPanel buttonWrapperWrapper = new JPanel();
		buttonWrapperWrapper.setLayout(new BoxLayout(buttonWrapperWrapper, BoxLayout.PAGE_AXIS));
		buttonWrapperWrapper.add(buttonWrapper);

		layerWrapper.add(moneyTextWrapper);
		layerWrapper.add(buttonWrapperWrapper);

		basePanel.add(layerWrapper);
	}
}
