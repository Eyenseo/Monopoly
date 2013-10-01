package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ThirdTest {
	public static void main(String[] args) {
		//Frame
		JFrame frame = createJFrame();
		//Panels
		JPanel panelL = createJPanel(new Color(234, 8, 0));
		JPanel panelC = createJPanel(new Color(0, 234, 4));
		JPanel panelR = createJPanel(new Color(0, 4, 234));
		//Labels
		JLabel labelL = createJLabel();
		JLabel labelC = createJLabel();
		JLabel labelR = createJLabel();
		//LEFT
		labelL.setText("Left");
		panelL.add(labelL);
		frame.getContentPane().add(panelL, BorderLayout.WEST);
		//CENTER
		labelC.setText("Hello World!");
		panelC.add(labelC);
		frame.getContentPane().add(panelC, BorderLayout.CENTER);
		//RIGHT
		labelR.setText("Right");
		panelR.add(labelR);
		frame.getContentPane().add(panelR, BorderLayout.EAST);
		//View
		frame.pack();
		frame.setVisible(true);
	}

	private static JLabel createJLabel() {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(new Color(0x400743));
		label.setForeground(new Color(0xFFF200));
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		return label;
	}

	private static JPanel createJPanel(Color color) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		return panel;
	}

	private static JFrame createJFrame() {
		JFrame frame = new JFrame("BorderLayout");
		frame.setLayout(new BorderLayout());
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
}

