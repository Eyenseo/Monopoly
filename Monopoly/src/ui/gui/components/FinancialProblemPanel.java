package ui.gui.components;

import core.ClientOperator;
import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import objects.value.action.HaggleData;
import objects.value.action.PlayerStatusData;
import ui.gui.Model;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The FinancialProblemPanel presents the panel that will be schown if a player is in need of money, he can either
 * haggle set mortgage or give up
 */
public class FinancialProblemPanel extends JPanel {
	private final Model          model;
	private final ClientOperator clientOperator;
	private final String         headerText;
	private       JTextPane      header;
	private       JButton        haggle;
	private       JButton        mortgage;
	private       JButton        resolve;

	/**
	 * @param model          The value determines the Model to use to obtain information
	 * @param clientOperator The value determines the clientOperator
	 */
	public FinancialProblemPanel(Model model, ClientOperator clientOperator) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.model = model;
		this.clientOperator = clientOperator;
		headerText = "Sie sind kurz davor bankrott zu sein!\n" +
		             "Sollten Sie nicht in der Lage sein sich das noetiege Geld zu beschaffen, " +
		             "scheiden sie aus dem Spiel aus.\n" +
		             "Geld zu erreichen:\n";

		createHeaderPane();
		createHaggleButton();
		createMortgageButton();
		createResolveButton();

		add(Box.createVerticalGlue());
		buildLayout();
		add(Box.createVerticalGlue());

		model.addModelEventListener(Model.ModelEventName.FINANCIAL, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						updateHeaderText();
						updateResolveText();
					}
				});
			}
		});
		model.addModelEventListener(Model.ModelEventName.HAGGLE, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						updateHaggleButton();
					}
				});
			}
		});
	}

	/**
	 * The method will disable the haggel button if the financial problems where caused by a haggle
	 */
	//TODO allow multiply trades per player
	private void updateHaggleButton() {
		if(model.getHaggleData() != null && model.getHaggleData().getHaggleState() != HaggleData.HaggleState.ACCEPT &&
		   model.getHaggleData().getHaggleState() != HaggleData.HaggleState.DECLINE) {
			haggle.setEnabled(false);
		} else {
			haggle.setEnabled(true);
		}
	}

	/**
	 * The method will change the text to ok if the financial problems are resolved
	 */
	private void updateResolveText() {
		if(model.getUser().getNeededMoney() != 0) {
			resolve.setText("Aufgeben");
		} else {
			resolve.setText("Ok");
		}
	}

	/**
	 * The method will update the displayed text, more specific the amount of money that the user has to obtain at
	 * least do
	 * not lose the game
	 */
	private void updateHeaderText() {
		try {
			StyledDocument doc = header.getStyledDocument();

			Style style = header.addStyle("Style", null);
			StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
			StyleConstants.setForeground(style, new Color(0, 0, 0));

			doc.remove(0, doc.getLength());
			doc.insertString(0, headerText, style);

			StyleConstants.setForeground(style, new Color(255, 0, 0));

			doc.insertString(doc.getLength(), "" + model.getUser().getNeededMoney(), style);
		} catch(BadLocationException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	/**
	 * The method will create all components of the FinancialProblemPanel
	 */
	private void buildLayout() {
		JPanel wrapper = new JPanel(new GridLayout());
		wrapper.add(header);
		add(wrapper);

		JPanel buttons = new JPanel(new BorderLayout());
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));

		JPanel topRow = new JPanel();
		topRow.setLayout(new BoxLayout(topRow, BoxLayout.LINE_AXIS));
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.LINE_AXIS));

		topRow.add(haggle);
		topRow.add(Box.createRigidArea(new Dimension(35, 0)));
		topRow.add(mortgage);
		buttons.add(topRow);
		buttons.add(Box.createRigidArea(new Dimension(0, 25)));

		bottomRow.add(resolve);
		buttons.add(bottomRow);

		add(buttons);
	}

	/**
	 * The method will create the HeaderPane
	 */
	private void createHeaderPane() {
		//TODO create a costume JPanel/JTextPane that adjusts its height to minimal so there isn't a **** gap and is
		// still able to lay out text
		StyledDocument document = new DefaultStyledDocument();
		Style defaultStyle = document.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(defaultStyle, StyleConstants.ALIGN_CENTER);

		try {
			document.insertString(0, headerText, null);
		} catch(BadLocationException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		header = new JTextPane(document);
		header.setEditable(false);
		header.setBackground(new Color(236, 236, 236));
	}

	/**
	 * The method will create the haggle button
	 */
	private void createHaggleButton() {
		haggle = new JButton("Handeln");
		haggle.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCurrentMainPanelName(Model.MainPanelName.HAGGLE);
					}
				});
			}
		});
	}

	/**
	 * The method will create the mortage button
	 */
	private void createMortgageButton() {
		mortgage = new JButton("Hypothek");
		mortgage.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCurrentMainPanelName(Model.MainPanelName.MORTGAGE);
					}
				});
			}
		});
	}

	/**
	 * The method will create the give up / resolve button
	 */
	private void createResolveButton() {
		resolve = new JButton("Aufgeben");
		resolve.setAlignmentX(CENTER_ALIGNMENT);
		resolve.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						if(model.getUser().getNeededMoney() == 0) {
							model.setCurrentMainPanelName(Model.MainPanelName.GAME);
							clientOperator.sendActionData(new PlayerStatusData(model.getUser().getId(),
							                                                   PlayerStatusData.PlayerStatus.FINANCIAL));
						} else {
							clientOperator.sendActionData(new PlayerStatusData(model.getUser().getId(),
							                                                   PlayerStatusData.PlayerStatus.GIVEUP));
						}
					}
				});
			}
		});
	}
}
