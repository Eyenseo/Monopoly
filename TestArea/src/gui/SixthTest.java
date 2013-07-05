package gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

/**
 * Created with IntelliJ IDEA. User: eyenseo Date: 6/26/13 Time: 12:12 PM To change this template use File | Settings |
 * File Templates.
 */
public class SixthTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(500, 300));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBackground(new Color(255, 31, 72));
		frame.add(panel);

		JTextPane pane = new JTextPane();
		try {
			pane.getDocument().insertString(0, "Hallo\noo\noo\n!", null);
		} catch(BadLocationException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		JPanel top = new JPanel(new GridLayout());
		//		top.setLayout(new BoxLayout(top,BoxLayout.LINE_AXIS));
		top.setBackground(new Color(106, 255, 105));

		JPanel middle = new JPanel(new GridLayout());
		//		middle.setLayout(new BoxLayout(middle,BoxLayout.LINE_AXIS));
		middle.setLayout(new FlowLayout());
		middle.setPreferredSize(new Dimension(20, 5));
		middle.setBackground(new Color(247, 255, 40));

		middle.add(pane);

		JPanel bottom = new JPanel(new GridLayout());
		//		bottom.setLayout(new BoxLayout(bottom,BoxLayout.LINE_AXIS));
		bottom.setBackground(new Color(63, 83, 255));

		//		panel.add(top);
		panel.add(Box.createVerticalGlue());
		panel.add(middle);
		panel.add(Box.createVerticalGlue());
		//		panel.add(bottom);

		frame.pack();
		frame.setVisible(true);
	}
}
