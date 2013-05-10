package gui;

import javax.swing.*;
import java.awt.*;

public class FirstTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Hello World!");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Hello World!");
		label.setSize(100, 100);
		label.setOpaque(true);
		label.setBackground(new Color(0x400743));
		label.setForeground(new Color(0xFF4724));
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.setBackground(new Color(101, 234, 212));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(label);
		frame.setLocation(30, 30);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 100);
		frame.add(panel);
		frame.setVisible(true);
	}
}
