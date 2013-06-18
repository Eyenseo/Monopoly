package gui;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: eyenseo
 * Date: 5/22/13
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class FifthTest extends JPanel {

	private JTextPane money;
	private JTextPane position;
	private JTextPane dice;

	public FifthTest() {
		super(new GridBagLayout());
		setBackground(new Color(0, 255, 255)); //DEBUG

		buildLayout();
	}

	private void buildLayout() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

		money = new JTextPane();
		money.setEditable(false);
		money.setPreferredSize(new Dimension(200, 20));
		money.setText("Left");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.weightx = 0.25;
		add(money, gridBagConstraints);

		position = new JTextPane();
		position.setEditable(false);
		position.setPreferredSize(new Dimension(400, 20));
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		position.setParagraphAttributes(center, true);
		position.setText("Center");
		gridBagConstraints.gridx = 1;
		gridBagConstraints.weightx = 0.5;
		add(position, gridBagConstraints);

		dice = new JTextPane();
		dice.setEditable(false);
		dice.setPreferredSize(new Dimension(200, 20));
		dice.setBackground(new Color(255, 0, 0));
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		dice.setParagraphAttributes(right, true);
		dice.setText("Right");
		gridBagConstraints.gridx = 2;
		gridBagConstraints.weightx = 0.25;
		add(dice, gridBagConstraints);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new FifthTest());
		frame.pack();
		frame.setVisible(true);
	}
}
