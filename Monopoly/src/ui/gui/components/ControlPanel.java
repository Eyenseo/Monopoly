package ui.gui.components;

import core.ClientOperator;
import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import objects.value.action.BuyData;
import objects.value.action.PlayerStatusData;
import objects.value.action.TurnData;
import ui.gui.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ControlPanel presents the buttons the user can interact with and also the chat the users can communicate over
 */
class ControlPanel extends JPanel {
	private ClientOperator clientOperator;
	private Model          model;
	//Components
	private DiceImage      firstDice;
	private DiceImage      secondDice;
	private JButton        turnOption;
	private JButton        buyOption;
	private JButton        haggle;
	private JButton        mortgage;
	private JButton        giveUp;
	private JTextArea      chatHistory;
	private JTextArea      chatMessage;
	//Text
	private String         turnOptionText;
	private String         buyOptionText;
	private String         haggleText;
	private String         mortgageText;
	private String         giveUpText;
	private String         endAppText;
	private String         chatHistoryText;

	/**
	 * @param model          The value determines the Model the ControlPanel will get its information from
	 * @param clientOperator The value determines the ClientOperator, that will send ActionData to the server
	 */
	public ControlPanel(Model model, ClientOperator clientOperator) {
		super(new BorderLayout());
		this.clientOperator = clientOperator;
		this.model = model;

		//Text
		haggleText = "Handeln";
		mortgageText = "Hypothek";
		giveUpText = "Aufgeben";
		endAppText = "Beenden";
		chatHistoryText = "Hier sollte ein Chat hin";

		add(buildButtonPanel(), BorderLayout.NORTH);
		add(buildChatPanel(), BorderLayout.CENTER);

		model.addModelEventListener(Model.ModelEventName.TURNOPTION, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						updateTurnOption();
						if(ControlPanel.this.model.getTurnOptionState() == Model.TurnOptionState.DEACTIVATED) {
							firstDice.setActive(false);
							secondDice.setActive(false);
						} else {
							firstDice.setActive(true);
							secondDice.setActive(true);
						}
					}
				});
			}
		});
		model.addModelEventListener(Model.ModelEventName.BUYOPTION, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						updateBuyOption();
					}
				});
			}
		});
		model.addModelEventListener(Model.ModelEventName.TURNSTATE, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						updateBuyOption();
						updateTurnOption();
					}
				});
			}
		});
		model.addModelEventListener(Model.ModelEventName.DICE, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						firstDice.setCurrentIndex(ControlPanel.this.model.getFirstDice());
						secondDice.setCurrentIndex(ControlPanel.this.model.getSecondDice());
					}
				});
			}
		});
		registerButtonListener();
	}

	/**
	 * The method creates following buttons: <ul> <li>turnOption</li> <li>buyOption</li> <li>haggle</li>
	 * <li>mortgage</li>
	 * <li>giveUp</li> <li>endApp</li> </ul>
	 *
	 * @return The return value is a Panel with all buttons of the ControlPanel
	 */
	private JPanel buildButtonPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		//Add first dice
		firstDice = new DiceImage();
		gridBagConstraints.insets = new Insets(5, 5, 0, 8);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		panel.add(firstDice, gridBagConstraints);

		//Add second dice
		secondDice = new DiceImage();
		gridBagConstraints.insets = new Insets(5, 0, 0, 5);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		panel.add(secondDice, gridBagConstraints);

		// Button for throw dice, end turn, begin next turn
		turnOption = new JButton();
		turnOption.setText(turnOptionText);
		gridBagConstraints.insets = new Insets(5, 5, 0, 5);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		panel.add(turnOption, gridBagConstraints);

		// Button to buy a house, hotel, property
		buyOption = new JButton();
		buyOption.setText(buyOptionText);
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		panel.add(buyOption, gridBagConstraints);

		// Button to haggle with a player
		haggle = new JButton();
		haggle.setText(haggleText);
		gridBagConstraints.insets = new Insets(20, 5, 5, 5);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 3;
		panel.add(haggle, gridBagConstraints);

		// Button to take a mortgage
		mortgage = new JButton();
		mortgage.setText(mortgageText);
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 3;
		panel.add(mortgage, gridBagConstraints);

		// Button to give up
		giveUp = new JButton();
		giveUp.setText(giveUpText);
		gridBagConstraints.insets = new Insets(20, 5, 5, 5);
		gridBagConstraints.ipadx = 100;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 3;
		panel.add(giveUp, gridBagConstraints);

		return panel;
	}

	/**
	 * The method creates the chatHistory and chatMessage Panel divided by a SplitPane
	 *
	 * @return The return value is a panel with a chat history TextArea and a chat message TextArea divided by a
	 * SplitPane
	 */
	private JPanel buildChatPanel() {
		chatHistory = new JTextArea(chatHistoryText);
		chatHistory.setWrapStyleWord(true);
		chatHistory.setLineWrap(true);
		chatHistory.setEditable(false);

		JScrollPane seeScrollPane = new JScrollPane(chatHistory);
		seeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		seeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		seeScrollPane.setPreferredSize(new Dimension(100, 150));

		chatMessage = new JTextArea("Hier sollte ihre Nachtricht eingegeben werden");
		chatMessage.setWrapStyleWord(true);
		chatMessage.setLineWrap(true);
		JScrollPane writeScrollPane = new JScrollPane(chatMessage);
		writeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		writeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		writeScrollPane.setPreferredSize(new Dimension(100, 50));

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.7);
		splitPane.setTopComponent(seeScrollPane);
		splitPane.setBottomComponent(writeScrollPane);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(splitPane, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * The method will set the turn button text and state accordingly to the turnOptionState set in the model
	 */
	private void updateTurnOption() {
		if(model.getTurnOptionState() == Model.TurnOptionState.DEACTIVATED) {
			turnOption.setEnabled(false);
		} else {
			turnOption.setEnabled(true);
		}

		switch(model.getTurnOptionState()) {
			case THROWDICE:
				turnOptionText = "Wuerfeln";
				break;
			case THROWDICEAGAIN:
				turnOptionText = "Erneut Wuerfeln";
				break;
			case ENDTURN:
				turnOptionText = "Zug beenden";
				break;
			case DEACTIVATED:
				turnOptionText = "Wuerfeln";
				break;
		}

		turnOption.setText(turnOptionText);
	}

	/**
	 * The method will set the buy button text and state accordingly to the buyOptionState set in the model
	 */
	private void updateBuyOption() {
		if(model.getBuyOptionState() == Model.BuyOptionState.DEACTIVATED) {
			buyOption.setEnabled(false);
		} else {
			buyOption.setEnabled(true);
		}

		switch(model.getBuyOptionState()) {
			case PURCHASABLE:
				buyOptionText = "Feld kaufen (" + model.getPurchasablePrice() + ")";
				break;
			case BUYHOUSE:
				buyOptionText = "Haus kaufen (" + model.getStreetUpgrade() + ")";
				break;
			case BUYHOTEL:
				buyOptionText = "Hotel kaufen (" + model.getStreetUpgrade() + ")";
				break;
			case DEACTIVATED:
				buyOptionText = "Nichts zu kaufen";
				break;
		}

		buyOption.setText(buyOptionText);
	}

	/**
	 * The method will register the needed EventListeners at the buttons
	 */
	private void registerButtonListener() {
		turnOption.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						clientOperator.sendActionData(new TurnData(model.getUser().getId(), model.isMoveAction()));
					}
				});
			}
		});
		buyOption.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						clientOperator.sendActionData(new BuyData(model.getUser().getId(), model.getBuyOptionState() ==
						                                                                   Model.BuyOptionState
								                                                                   .BUYHOTEL || model
										                                                                                .getBuyOptionState() ==
						                                                                   Model.BuyOptionState
								                                                                   .BUYHOUSE));
					}
				});
			}
		});
		haggle.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCurrentMainPanelName(Model.MainPanelName.HAGGLE);
					}
				});
			}
		});
		mortgage.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCurrentMainPanelName(Model.MainPanelName.MORTGAGE);
					}
				});
			}
		});
		giveUp.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						clientOperator.sendActionData(
								new PlayerStatusData(model.getUser().getId(), PlayerStatusData.PlayerStatus.GIVEUP));
					}
				});
			}
		});
	}
}
