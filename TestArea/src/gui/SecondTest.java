package gui;

import javax.swing.*;
import java.awt.*;

public class SecondTest {
	static SpringLayout layout = new SpringLayout();
	static Dimension    d      = new Dimension(130, 70);

	public static void main(String[] args) {
		JFrame frame = createJFrame();
		JPanel panelL = createJPanel(new Color(234, 8, 0));
		JPanel panelC = createJPanel(new Color(0, 234, 4));
		JPanel panelR = createJPanel(new Color(0, 4, 234));
		JLabel labelL = createJLabel();
		JLabel labelC = createJLabel();
		JLabel labelR = createJLabel();
		//LEFT
		layout.putConstraint(SpringLayout.NORTH, panelL, 5, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, panelL, -5, SpringLayout.SOUTH, frame);
		layout.putConstraint(SpringLayout.WEST, panelL, 5, SpringLayout.WEST, frame);
		labelL.setText("L");
		panelL.add(labelL);
		//CENTER
		layout.putConstraint(SpringLayout.NORTH, panelC, 5, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.EAST, panelC, -5, SpringLayout.WEST, panelR);
		layout.putConstraint(SpringLayout.SOUTH, panelC, -5, SpringLayout.SOUTH, frame);
		layout.putConstraint(SpringLayout.WEST, panelC, 5, SpringLayout.EAST, panelL);
		labelC.setText("Hello World!");
		panelC.add(labelC);
		//RIGHT
		layout.putConstraint(SpringLayout.NORTH, panelR, 5, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.EAST, panelR, -15, SpringLayout.EAST, frame);
		// 3x-5 [1] PanelBorder [2]LabelBorder [3] FrameBorder
		layout.putConstraint(SpringLayout.SOUTH, panelR, 15, SpringLayout.SOUTH, frame);
		labelR.setText("R");
		panelR.add(labelR);
		//FRAME
		frame.add(panelL);
		frame.add(panelC);
		frame.add(panelR);
		frame.pack();
		//		Dimension dimension = frame.getPreferredSize();
		//		System.out.println(dimension);
		//		frame.setPreferredSize(dimension);
		frame.setVisible(true);
		frame.pack();
	}

	private static JLabel createJLabel() {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(new Color(0x400743));
		label.setForeground(new Color(0xFFF200));
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		label.setPreferredSize(d);
		return label;
	}

	private static JPanel createJPanel(Color color) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		return panel;
	}

	private static JFrame createJFrame() {
		JFrame frame = new JFrame("SpringLayout");
		frame.setLayout(layout);
		frame.setLocation(30, 30);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(0, 192, 174));
		return frame;
	}
}
