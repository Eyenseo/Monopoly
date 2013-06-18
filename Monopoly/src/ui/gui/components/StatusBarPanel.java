package ui.gui.components;

import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import ui.gui.Model;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * The StatusBarPanel displays the Money, the Position and the dieces of the user
 */
public class StatusBarPanel extends JPanel {
	private final Model     model;
	private final JTextPane moneyPane;
	private final JTextPane positionPane;

	/**
	 * @param model The value determines the model that the StatusBarPanel gets its information from
	 */
	public StatusBarPanel(Model model) {
		super(new GridBagLayout());
		this.model = model;

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		moneyPane = new JTextPane();
		moneyPane.setEditable(false);
		moneyPane.setPreferredSize(new Dimension(200, 20)); //TODO make a relative calculation!
		gridBagConstraints.gridx = 0;
		gridBagConstraints.weightx = 0.25;
		add(moneyPane, gridBagConstraints);

		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		positionPane = new JTextPane();
		positionPane.setEditable(false);
		positionPane.setPreferredSize(new Dimension(400, 20));
		positionPane.setParagraphAttributes(center, true);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.weightx = 0.5;
		add(positionPane, gridBagConstraints);

		registerModelListener();
	}

	/**
	 * The method will register all needed event listeners
	 */
	private void registerModelListener() {
		ModelEventListener moneyUpdate = new ModelEventListener() {
			/**
			 * The method will set the current moneyPane to the TextPanes text, if the user lost moneyPane the amount
			 * will be
			 * displayed in red and if he made moneyPane it will be displayed in green behind the current amount
			 * @param event the value determines the event source
			 */
			@Override public void actionPerformed(ModelEvent event) {
				int currentMoney = model.getCurrentMoney();
				int oldMoney = model.getOldMoney();

				try {
					StyledDocument doc = getMoneyPane().getStyledDocument();
					doc.remove(0, doc.getLength());
					Style style = getMoneyPane().addStyle("Style", null);
					StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
					StyleConstants.setForeground(style, new Color(0, 0, 0));

					if(currentMoney == oldMoney) {
						doc.insertString(doc.getLength(), "Geld: " + currentMoney, style);
					} else {
						doc.insertString(doc.getLength(), "Geld: " + currentMoney + " (", style);

						if(currentMoney < oldMoney) {
							StyleConstants.setForeground(style, new Color(255, 0, 0));
							doc.insertString(doc.getLength(), "-" + (oldMoney - currentMoney), style);
						} else if(currentMoney > oldMoney) {
							StyleConstants.setForeground(style, new Color(0, 198, 0));
							doc.insertString(doc.getLength(), "+" + (currentMoney - oldMoney), style);
						}
						StyleConstants.setForeground(style, new Color(0, 0, 0));
						doc.insertString(doc.getLength(), ")", style);
					}
				} catch(BadLocationException e1) {
					e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		};

		ModelEventListener positionUpdate = new ModelEventListener() {
			/**
			 * The method will set the current positionPane of the player with additional information if it's a
			 * purchasable. If the player is in Jail the Jail will have a red color.
			 * @param event the value determines the event source
			 */
			@Override public void actionPerformed(ModelEvent event) {
				try {
					StyledDocument doc = getPositionPane().getStyledDocument();
					doc.remove(0, doc.getLength());

					Style style = getPositionPane().addStyle("Style", null);
					StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
					StyleConstants.setForeground(style, new Color(0, 0, 0));

					doc.insertString(doc.getLength(), "Position: ", style);

					//Set the Field name red if in jail - the Field should be the Jail
					if(model.isInJail()) {
						StyleConstants.setForeground(style, new Color(255, 0, 0));
						doc.insertString(doc.getLength(), model.getFieldName(), style);
						StyleConstants.setForeground(style, new Color(0, 0, 0));
					} else {
						doc.insertString(doc.getLength(), model.getFieldName(), style);
					}

					//If there is a Owner add additional information
					if(model.isPurchasable()) {
						doc.insertString(doc.getLength(), "\tBesitzer: ", style);

						//Add the owner name
						if(model.getPurchasableOwner() != null) {
							if(model.getClientPlayer().getId() == model.getPurchasableOwnerId()) {
								doc.insertString(doc.getLength(), "Sie", style);
							} else {
								doc.insertString(doc.getLength(), model.getPurchasableOwner(), style);
							}
						} else {
							doc.insertString(doc.getLength(), "-", style);
						}

						//Add the amount of Houses of a Street
						if(model.isStreet()) {
							int stage = model.getStreetStage();

							if(6 == stage) {
								doc.insertString(doc.getLength(), "\tHotel: 1", style);
							} else if(stage > 1) {
								doc.insertString(doc.getLength(), "\tHaeuser: " + (stage - 1), style);
							} else {
								doc.insertString(doc.getLength(), "\tHaeuser: -", style);
							}
						}
					}
				} catch(BadLocationException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		};

		model.addModelEventListener(Model.ModelEventName.MONEY, moneyUpdate);

		model.addModelEventListener(Model.ModelEventName.POSITION, positionUpdate);

		model.addModelEventListener(Model.ModelEventName.PROPERTY, positionUpdate);

		model.addModelEventListener(Model.ModelEventName.ISINJAIL, positionUpdate);
	}

	/**
	 * @return The return value is the Pane of the money
	 */
	private JTextPane getMoneyPane() {
		return moneyPane;
	}

	/**
	 * @return The return value is the Pane of the position
	 */
	private JTextPane getPositionPane() {
		return positionPane;
	}
}
