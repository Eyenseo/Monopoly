package gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class FinancialProblemTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(800, 600));
		frame.add(new FinancialProblemPanel());
		frame.pack();
		frame.setVisible(true);
	}
}

class FinancialProblemPanel extends JPanel {
	private final String    headerText;
	private final JTextPane header;

	FinancialProblemPanel() {
		headerText = "Sie sind kurz davor bankrott zu sein!\n" +
		             "Sollten Sie nicht in der Lage sein sich das nötiege Geld zu beschaffen, " +
		             "scheiden sie aus dem Spiel aus.\n" +
		             "Geld zu erreichen:\n" +
		             "123456789";
		header = createHeaderPane();
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 15, 0);
		add(createHeaderPane(), constraints);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));

		JPanel topRow = new JPanel();
		topRow.setLayout(new BoxLayout(topRow, BoxLayout.LINE_AXIS));
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.LINE_AXIS));

		topRow.add(createHaggleButton());
		topRow.add(Box.createRigidArea(new Dimension(35, 0)));
		topRow.add(createMortgageButton());
		buttons.add(topRow, constraints);
		buttons.add(Box.createRigidArea(new Dimension(0, 25)));

		bottomRow.add(createGiveUpButton());
		buttons.add(bottomRow, constraints);

		constraints.gridy = 1;

		constraints.insets = new Insets(0, 0, 0, 0);
		add(buttons, constraints);
	}

	private JTextPane createHeaderPane() {
		StyledDocument document = new DefaultStyledDocument();
		Style defaultStyle = document.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(defaultStyle, StyleConstants.ALIGN_CENTER);
		try {
			document.insertString(0, headerText, null);
		} catch(BadLocationException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		JTextPane pane = new JTextPane(document);
		return pane;
	}

	private JButton createHaggleButton() {
		return new JButton("Handeln");
	}

	private JButton createMortgageButton() {
		return new JButton("Hypothek");
	}

	private JButton createGiveUpButton() {
		JButton button = new JButton("Aufgeben/ok");
		button.setAlignmentX(CENTER_ALIGNMENT);
		return button;
	}
}
