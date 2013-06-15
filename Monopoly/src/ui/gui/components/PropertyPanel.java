package ui.gui.components;

import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import objects.value.map.PurchasableData;
import objects.value.map.StationData;
import objects.value.map.StreetData;
import ui.gui.Model;
import ui.gui.layout_manager.PurchasableCardLayout;
import ui.gui.layout_manager.PurchasableCardLayoutConstraints;

import javax.swing.*;
import java.awt.*;

/**
 * The PropertyPanel displays the purchasable fields the user owns
 */
class PropertyPanel extends JPanel {
	private final Model  model;
	private final JPanel content;

	/**
	 * @param model The value determines the Model to get all information from
	 */
	public PropertyPanel(Model model) {
		super(new GridLayout());
		this.model = model;

		setPreferredSize(new Dimension(205, 200)); //TODO get Parameters from PurchasableCardPanel Class

		content = new JPanel();
		content.setLayout(new PurchasableCardLayout());
		JScrollPane scrollPane = new JScrollPane(content);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);

		ModelEventListener listener = new ModelEventListener() {
			/**
			 * The method will check if the there are new PurchasableCardPanels to be displayed or to be removed
			 * @param event the value determines the event source
			 */
			//TODO check method
			@Override public void actionPerformed(ModelEvent event) {

				PurchasableCardLayoutConstraints constraints = new PurchasableCardLayoutConstraints();
				boolean change;

				//add new components
				for(PurchasableData data : PropertyPanel.this.model.getClientPurchasable()) {
					change = true;
					for(Component panel : content.getComponents()) {
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
						content.add(new PurchasableCardPanel(data), constraints);
					}
				}

				//remove new components
				for(Component panel : content.getComponents()) {
					if(panel instanceof PurchasableCardPanel) {
						change = true;
						for(PurchasableData data : PropertyPanel.this.model.getClientPurchasable()) {
							if(((PurchasableCardPanel) panel).getData().equals(data)) {
								change = false;
							}
						}

						if(change) {
							content.remove(panel);
						}
					}
				}
				repaint();
			}
		};

		model.addModelEventListener(Model.ModelEventName.PROPERTY, listener);
		model.addModelEventListener(Model.ModelEventName.STAGE, listener);
		model.addModelEventListener(Model.ModelEventName.INMORTAGE, listener);
	}
}
