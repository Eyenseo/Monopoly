package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: eyenseo
 * Date: 5/19/13
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class GridBagTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		JPanel red = new JPanel();
		red.setBackground(new Color(255, 0, 0));
		red.setPreferredSize(new Dimension(50, 100));
		gridBagConstraints.weightx = 0.25;
		gridBagConstraints.gridx = 0;
		panel.add(red, gridBagConstraints);

		JPanel green = new JPanel();
		green.setBackground(new Color(0, 255, 0));
		green.setPreferredSize(new Dimension(200, 100));
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.gridx = 1;
		panel.add(green, gridBagConstraints);

		JPanel blue = new JPanel();
		blue.setBackground(new Color(0, 0, 255));
		blue.setPreferredSize(new Dimension(200, 100));
		gridBagConstraints.weightx = 0.25;
		gridBagConstraints.gridx = 2;
		panel.add(blue, gridBagConstraints);

		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
