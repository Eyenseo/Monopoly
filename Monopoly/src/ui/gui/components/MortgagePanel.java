package ui.gui.components;

import core.ClientOperator;
import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import objects.value.action.MortgageData;
import objects.value.map.PurchasableData;
import objects.value.map.StationData;
import objects.value.map.StreetData;
import ui.gui.Model;
import ui.gui.layout_manager.PurchasableCardLayout;
import ui.gui.layout_manager.PurchasableCardLayoutConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The MortgagePanel provides th user with the option to take and redeem mortgage of his purchasables
 */
public class MortgagePanel extends JPanel {
	private final ClientOperator clientOperator;
	private final Model          model;
	private final JPanel         purchasable;

	/**
	 * @param model          The value determines the Model the ControlPanel will get its information from
	 * @param clientOperator The value determines the ClientOperator, that will send ActionData to the server
	 */
	public MortgagePanel(Model model, ClientOperator clientOperator) {
		this.model = model;
		this.clientOperator = clientOperator;
		setLayout(new BorderLayout());
		purchasable = new JPanel();
		purchasable.setLayout(new PurchasableCardLayout());
		JScrollPane scrollPane = new JScrollPane(purchasable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JButton back = new JButton("Zurueck");
		back.setAlignmentY(JButton.BOTTOM_ALIGNMENT);
		back.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		back.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				MortgagePanel.this.model.setCurrentMainPanelName(Model.CurrentMainPanelName.GAME);
			}
		});
		model.addModelEventListener(Model.ModelEventName.PROPERTY, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				checkPurchasableData();
			}
		});
		model.addModelEventListener(Model.ModelEventName.INTMORTAGE, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				updatePurchasableCards();
			}
		});
		panel.add(back);
		add(scrollPane, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}

	/**
	 * The method will check if the there are new PurchasableCardPanels to be displayed or to be removed
	 */
	//TODO check method - especially for loops
	private void checkPurchasableData() {
		boolean change;
		//add new components
		for(PurchasableData data : model.getClientPurchasable()) {
			change = true;
			for(Component panel : purchasable.getComponents()) {
				if(panel instanceof PurchasableCardPanel) {
					if(((PurchasableCardPanel) panel).getData().equals(data)) {
						change = false;
					}
				}
			}
			if(change) {
				PurchasableCardLayoutConstraints constraints = new PurchasableCardLayoutConstraints();
				PurchasableCardPanel panel = new PurchasableCardPanel(data);
				panel.addMouseListener(new MouseAdapter() {
					/**
					 * The method will send MortgageData (ActionData) to the server with the id of the clicked
					 * PurchasableCardPanel
					 * @param e The value determines the event source
					 */
					@Override public void mouseClicked(MouseEvent e) {
						PurchasableCardPanel panel = (PurchasableCardPanel) e.getSource();
						MortgageData data =
								new MortgageData(model.getClientPlayer().getId(), panel.getData().getFieldNumber());
						clientOperator.sendActionData(data);
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
				purchasable.add(panel, constraints);
			}
		}
		//remove new components
		for(Component panel : purchasable.getComponents()) {
			if(panel instanceof PurchasableCardPanel) {
				change = true;
				for(PurchasableData data : model.getClientPurchasable()) {
					if(((PurchasableCardPanel) panel).getData().equals(data)) {
						change = false;
					}
				}
				if(change) {
					purchasable.remove(panel);
				}
			}
		}
		updatePurchasableCards();
	}

	/**
	 * The method changes the border of the PurchasableCardPanel according to their mortgage state
	 */
	private void updatePurchasableCards() {
		for(Component component : purchasable.getComponents()) {
			if(component instanceof PurchasableCardPanel) {
				if(((PurchasableCardPanel) component).getData().isInMortgage()) {
					((PurchasableCardPanel) component)
							.setBorder(BorderFactory.createLineBorder(new Color(218, 0, 0), 3));
				} else {
					((PurchasableCardPanel) component)
							.setBorder(BorderFactory.createLineBorder(new Color(0, 156, 0), 3));
				}
			}
		}
	}
}
