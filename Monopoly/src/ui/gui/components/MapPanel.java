package ui.gui.components;

import ui.gui.Model;
import ui.gui.layout_manager.MapLayout;

import javax.swing.*;

/**
 * The MapPanel is a wrapper for the Map
 */
class MapPanel extends JPanel {
	private final Map map;

	/**
	 * @param model The value determines the Model to obtain information from
	 */
	public MapPanel(Model model) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//		setLayout(new GridLayout());

		map = new Map(new MapLayout(), model);

		add(map);
	}
}
