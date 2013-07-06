package ui.gui.components;

import ui.gui.Model;

import javax.swing.*;
import java.awt.*;

//JAVADOC
class MapPanel extends JPanel {
	private final Map map;

	//JAVADOC
	public MapPanel(Model model) {
		//		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		setLayout(new GridLayout());

		map = new Map(new MapLayout(), model);

		add(map);
	}
}
