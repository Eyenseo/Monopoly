package gui;

import javax.swing.*;
import java.awt.*;

public class PanelExtendTest extends JPanel {
	public PanelExtendTest() {
		super(new GridBagLayout());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		JPanel red = new JPanel();
		red.setBackground(new Color(255, 0, 0));
		red.setPreferredSize(new Dimension(50, 100));
		gridBagConstraints.weightx = 0.25;
		gridBagConstraints.gridx = 0;
		add(red, gridBagConstraints);

		JPanel green = new JPanel();
		green.setBackground(new Color(0, 255, 0));
		green.setPreferredSize(new Dimension(200, 100));
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.gridx = 1;
		add(green, gridBagConstraints);

		JPanel blue = new JPanel();
		blue.setBackground(new Color(0, 0, 255));
		blue.setPreferredSize(new Dimension(200, 100));
		gridBagConstraints.weightx = 0.25;
		gridBagConstraints.gridx = 2;
		add(blue, gridBagConstraints);
	}
}

class Run {
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		frame.getContentPane().add(new PanelExtendTest());
		frame.pack();
		frame.setVisible(true);
	}
}
