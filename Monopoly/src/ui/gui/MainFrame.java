package ui.gui;

import core.ClientOperator;
import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import ui.gui.components.GamePanel;
import ui.gui.components.HagglePanel;
import ui.gui.components.MortgagePanel;
import ui.gui.components.StatusBarPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The MainFrame is the main Window of the program.
 */
public class MainFrame extends JFrame {
	private final Model          model;
	//Components - Statusbar
	private final StatusBarPanel statusBarPanel;
	//Components - MainPanels
	private final JPanel         mainPanel;
	private final GamePanel      gamePanel;
	private final MortgagePanel  mortgagePanel;
	private final HagglePanel    hagglePanel;

	/**
	 * @param model          The value determines the Model the ControlPanel will get its information from
	 * @param clientOperator The value determines the ClientOperator, that will send ActionData to the server
	 */
	public MainFrame(Model model, ClientOperator clientOperator) {
		this.model = model;

		//Components - Statusbar
		statusBarPanel = new StatusBarPanel(model);

		//Components - MainPanels
		mainPanel = new JPanel(new BorderLayout());
		gamePanel = new GamePanel(model, clientOperator);
		mortgagePanel = new MortgagePanel(model, clientOperator);
		hagglePanel = new HagglePanel(model, clientOperator);

		setTitle("Monopoly - " + model.getUser().getName());
		setLocationRelativeTo(getRootPane());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new GridLayout());
		getContentPane().add(mainPanel);

		mainPanel.add(statusBarPanel, BorderLayout.NORTH);
		mainPanel.add(gamePanel, BorderLayout.CENTER);

		model.addModelEventListener(Model.ModelEventName.MAINPANEL, new ModelEventListener() {
			/**
			 * The method will change the panel to display int eh frame
			 * @param event the value determines the event source
			 */
			@Override public void actionPerformed(ModelEvent event) {
				mainPanel.removeAll();
				mainPanel.add(statusBarPanel, BorderLayout.NORTH);

				switch(MainFrame.this.model.getCurrentMainPanelName()) {
					case GAME:
						mainPanel.add(gamePanel, BorderLayout.CENTER);
						break;
					case MORTGAGE:
						mainPanel.add(mortgagePanel, BorderLayout.CENTER);
						break;
					case HAGGLE:
						mainPanel.add(hagglePanel, BorderLayout.CENTER);
				}
				validate();
				repaint();
			}
		});

		model.addModelEventListener(Model.ModelEventName.CARD, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				int lastCardData = MainFrame.this.model.getCardDataArrayList().size() - 1;

				if(lastCardData >= 0) {
					new CustomDialog(MainFrame.this.model,
					                 MainFrame.this.model.getCardDataArrayList().get(lastCardData));
				}
			}
		});

		pack();
		setVisible(true);
	}
}
