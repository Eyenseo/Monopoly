package gui;

import javax.swing.*;
import java.awt.*;

public class FourthTest {
	public static void main(String[] args) {
		JFrame frame = createJFrame();
		frame.setMinimumSize(new Dimension(800, 400));

		JPanel panelT = statusPanel();
		JPanel panelL = propertyPanel();
		JPanel panelC = mainPanel();
		JPanel panelR = controlPanel();

		frame.getContentPane().add(panelT, BorderLayout.NORTH);
		frame.getContentPane().add(panelL, BorderLayout.WEST);
		frame.getContentPane().add(panelC, BorderLayout.CENTER);
		frame.getContentPane().add(panelR, BorderLayout.EAST);

		frame.pack();
		frame.setVisible(true);
	}

	private static JPanel controlPanel() {
		JPanel main = new JPanel();
		main.setBackground(new Color(0, 4, 234));
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

		JPanel buttonPanel = buttonPanel();

		main.add(buttonPanel);
		main.add(chatPanel());
		return main;
	}

	private static JPanel statusPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 4));
		panel.setBackground(new Color(28, 220, 232));

		JTextField money = new JTextField("Geld");
		money.setHorizontalAlignment(JTextField.LEFT);

		JTextField field = new JTextField("Aktuelles Feld");
		field.setHorizontalAlignment(JTextField.CENTER);

		JTextField owner = new JTextField("Besitzer");
		owner.setHorizontalAlignment(JTextField.CENTER);

		JTextField turn = new JTextField("Runde");
		turn.setHorizontalAlignment(JTextField.RIGHT);

		panel.add(money);
		panel.add(field);
		panel.add(owner);
		panel.add(turn);

		return panel;
	}

	private static JPanel buttonPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(232, 226, 29));

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		// Button for throw dice, end turn, begin next turn
		JButton turnOptions = new JButton("Wuerfeln ...");
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		panel.add(turnOptions, gridBagConstraints);

		// Button to buy a house, hotel, property
		JButton buyOptions = new JButton("Kaufe ...");
		gridBagConstraints.insets = new Insets(0, 5, 5, 5);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		panel.add(buyOptions, gridBagConstraints);

		// Button to haggle with a player
		JButton haggle = new JButton("Handeln");
		gridBagConstraints.insets = new Insets(10, 5, 5, 0);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		panel.add(haggle, gridBagConstraints);

		// Button to take a mortgage
		JButton takeMortgage = new JButton("Hypotek aufnehmen");
		gridBagConstraints.insets = new Insets(5, 5, 5, 0);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 1;
		panel.add(takeMortgage, gridBagConstraints);

		// Button to redeem a mortgage
		JButton redeemMortgage = new JButton("Hypotek tilgen");
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		panel.add(redeemMortgage, gridBagConstraints);

		// Button to give up
		JButton giveUp = new JButton("Aufgeben");
		gridBagConstraints.insets = new Insets(20, 5, 5, 5);
		gridBagConstraints.ipadx = 100;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		panel.add(giveUp, gridBagConstraints);

		// Button to end program
		JButton endApp = new JButton("Beenden");
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		panel.add(endApp, gridBagConstraints);

		JFrame packer = new JFrame();
		packer.add(panel);
		packer.pack();
		panel.setMinimumSize(packer.getSize());
		panel.setPreferredSize(packer.getSize());
		panel.setMaximumSize(packer.getSize());

		return panel;
	}

	private static JPanel chatPanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBackground(new Color(234, 69, 227));

		JTextArea see = new JTextArea("BALALALLLALALAALLALALALA");
		see.setEditable(false);

		JScrollPane seeScrollPane = new JScrollPane(see);
		seeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		seeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JTextArea write = new JTextArea("BALALALLLALALAALLALALALA");

		JScrollPane writeScrollPane = new JScrollPane(write);
		writeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		writeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, seeScrollPane, writeScrollPane);

		panel.add(splitPane);
		return panel;
	}

	private static JPanel mainPanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBackground(new Color(0, 234, 4));

		JTextArea textArea = new JTextArea("BALALALLLALALAALLALALALA");

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		panel.add(scrollPane);
		return panel;
	}

	private static JPanel propertyPanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBackground(new Color(234, 8, 0));

		JTextArea textArea = new JTextArea("BALALALLLALALAALLALALALA");

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		panel.add(scrollPane);
		return panel;
	}

	private static JFrame createJFrame() {
		JFrame frame = new JFrame("Monopoly");
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
}
