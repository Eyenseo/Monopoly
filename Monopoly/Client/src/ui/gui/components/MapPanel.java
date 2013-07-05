package ui.gui.components;

import javax.swing.*;
import java.awt.*;

//JAVADOC
class MapPanel extends JPanel {
	private final JTextArea main;

	//JAVADOC
	public MapPanel() {
		super(new GridLayout());

		main = new JTextArea("BALALALLLALALAALLALALALA");
		main.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(main);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(scrollPane);
	}
}
