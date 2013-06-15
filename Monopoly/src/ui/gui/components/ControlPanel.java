package ui.gui.components;

import core.ClientOperator;
import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import objects.value.action.BuyData;
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
	private JButton        turnOption;
	private JButton        buyOption;
	private JButton        haggle;
	private JButton        mortgage;
	private JButton        giveUp;
	private JButton        endApp;
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
		chatHistoryText = "BALALALLLALALAALLALALALA";

		add(buildButtonPanel(), BorderLayout.NORTH);
		add(buildChatPanel(), BorderLayout.CENTER);

		model.addModelEventListener(Model.ModelEventName.TURNOPTION, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				updateTurnOption();
			}
		});
		model.addModelEventListener(Model.ModelEventName.BUYOPTION, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				updateBuyOption();
			}
		});
		model.addModelEventListener(Model.ModelEventName.TURNSTATE, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				updateBuyOption();
				updateTurnOption();
			}
		});

		registerButtonListener();
	}

	/**
	 * The method creates following buttons:
	 * <ul>
	 * <li>turnOption</li>
	 * <li>buyOption</li>
	 * <li>haggle</li>
	 * <li>mortgage</li>
	 * <li>giveUp</li>
	 * <li>endApp</li>
	 * </ul>
	 *
	 * @return The return value is a Panel with all buttons of the ControlPanel
	 */
	private JPanel buildButtonPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		// Button for throw dice, end turn, begin next turn
		turnOption = new JButton();
		turnOption.setText(turnOptionText);
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		panel.add(turnOption, gridBagConstraints);

		// Button to buy a house, hotel, property
		buyOption = new JButton();
		buyOption.setText(buyOptionText);
		gridBagConstraints.insets = new Insets(0, 5, 5, 5);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		panel.add(buyOption, gridBagConstraints);

		// Button to haggle with a player
		haggle = new JButton();
		haggle.setText(haggleText);
		gridBagConstraints.insets = new Insets(10, 5, 5, 5);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		panel.add(haggle, gridBagConstraints);

		// Button to take a mortgage
		mortgage = new JButton();
		mortgage.setText(mortgageText);
		gridBagConstraints.insets = new Insets(5, 5, 5, 0);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		panel.add(mortgage, gridBagConstraints);

		// Button to give up
		giveUp = new JButton();
		giveUp.setText(giveUpText);
		gridBagConstraints.insets = new Insets(20, 5, 5, 5);
		gridBagConstraints.ipadx = 100;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		panel.add(giveUp, gridBagConstraints);

		// Button to end program
		endApp = new JButton();
		endApp.setText(endAppText);
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		panel.add(endApp, gridBagConstraints);

		return panel;
	}

	/**
	 * The method creates the chatHistory and chatMassage Panel divided by a SplitPane
	 *
	 * @return The return value is a panel with a chat history TextArea and a chat massage TextArea divided by a
	 *         SplitPane
	 */
	private JPanel buildChatPanel() {
		chatHistory = new JTextArea("BALALALLLALALAALLALALALA");
		chatHistory.setEditable(false);

		JScrollPane seeScrollPane = new JScrollPane(chatHistory);
		seeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		seeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		seeScrollPane.setPreferredSize(new Dimension(100, 150));

		chatMessage = new JTextArea("BALALALLLALALAALLALALALA");
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
				clientOperator.sendActionData(new TurnData(model.getClientPlayer().getId(), model.isMoveAction()));
			}
		});
		buyOption.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				clientOperator.sendActionData(new BuyData(model.getClientPlayer().getId(),
				                                          model.getBuyOptionState() == Model.BuyOptionState.BUYHOTEL ||
				                                          model.getBuyOptionState() == Model.BuyOptionState.BUYHOUSE));
			}
		});
		haggle.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				model.setCurrentMainPanelName(Model.CurrentMainPanelName.HAGGLE);
			}
		});
		mortgage.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				model.setCurrentMainPanelName(Model.CurrentMainPanelName.MORTGAGE);
			}
		});
		giveUp.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(), "Not yet implemented.");
			}
		});
		endApp.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(), "Not yet implemented.");
			}
		});
	}
}
