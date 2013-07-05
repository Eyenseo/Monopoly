package ui.gui.components;

import core.ClientOperator;
import ui.gui.Model;

import javax.swing.*;
import java.awt.*;

/**
 * The game panel is the holds a ControlPanel, PropertyPanel and a MapPanel.
 */
public class GamePanel extends JPanel {
	private final ControlPanel  controlPanel;
	private final PropertyPanel propertyPanel;
	private final MapPanel      mapPanel;

	/**
	 * @param model          The value determines the Model the ControlPanel will get its information from
	 * @param clientOperator The value determines the ClientOperator, that will send ActionData to the server
	 */
	public GamePanel(Model model, ClientOperator clientOperator) {
		setLayout(new BorderLayout());

		controlPanel = new ControlPanel(model, clientOperator);
		propertyPanel = new PropertyPanel(model);
		mapPanel = new MapPanel();

		add(propertyPanel, BorderLayout.WEST);
		add(mapPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.EAST);
	}
}
