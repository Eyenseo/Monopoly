package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ExampleOne extends JPanel {
	public static final  int    MAX_COUNTER      = 20;
	private static final int    SIDE             = 600;
	private static final int    TIMER_DELAY      = 400;
	private static final int    MAX_RAND         = 220;
	private static final long   serialVersionUID = 173392568530000155L;
	private              int    counter          = 0;
	private              JLabel label            = new JLabel(String.valueOf(counter));
	private              Random gen              = new Random();
	private              int[]  randNumb         = new int[3];

	//Example for animation
	public ExampleOne() {
		setPreferredSize(new Dimension(SIDE, SIDE));
		setLayout(null);
		add(label);
		new Timer(TIMER_DELAY, new TimerListener()).start();
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
	}

	private static void createAndShowUI() {
		JFrame frame = new JFrame("Test3");
		frame.getContentPane().add(new ExampleOne());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void myMove() {
		for(int i = 0; i < randNumb.length; i++) {
			randNumb[i] = gen.nextInt(MAX_RAND);
		}
		label.setText(String.valueOf(counter));
		label.setSize(label.getPreferredSize());
		label.setLocation((SIDE * randNumb[0]) / MAX_RAND, (SIDE * randNumb[1]) / MAX_RAND);
		label.setForeground(new Color(randNumb[0], randNumb[1], randNumb[2]));
		repaint();
	}

	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(counter < MAX_COUNTER) {
				myMove();
				counter++;
			} else {
				((Timer) e.getSource()).stop();
			}
		}
	}
}
