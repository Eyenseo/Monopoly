package gui;

import javax.swing.*;
import java.awt.*;

public class ExampleThree {
	private final JFrame       frame      = new JFrame();
	private final JLayeredPane lpane      = new JLayeredPane();
	private final JPanel       panelBlue  = new JPanel();
	private final JPanel       panelGreen = new JPanel();

	//Example for Layered panel
	private ExampleThree() {
		frame.setPreferredSize(new Dimension(600, 400));
		frame.setLayout(new BorderLayout());
		frame.add(lpane, BorderLayout.CENTER);
		lpane.setBounds(0, 0, 600, 400);
		panelBlue.setBackground(Color.BLUE);
		panelBlue.setBounds(0, 0, 600, 400);
		panelBlue.setOpaque(true);
		panelGreen.setBackground(Color.GREEN);
		panelGreen.setBounds(200, 100, 100, 100);
		panelGreen.setOpaque(true);
		lpane.add(panelBlue, new Integer(0), 0);
		lpane.add(panelGreen, new Integer(1), 0);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		new ExampleThree();
	}
}
