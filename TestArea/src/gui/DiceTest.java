package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Marlena
 * Date: 08.06.13
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public class DiceTest {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout());
		panel.setPreferredSize(new Dimension(50, 50));
		panel.add(new DiceImage());

		window.add(panel);

		window.pack();
		window.setVisible(true);
	}
}

class DiceImage extends JPanel {
	Image[] activeDiceImage   = new Image[6];
	Image[] inactiveDiceImage = new Image[6];
	Image current;
	int currentIndex = 0;

	public DiceImage() {
		try {
			for(int i = 0; i < activeDiceImage.length; i++) {

				activeDiceImage[i] =
						ImageIO.read(new File(getClass().getResource("resource/" + (i + 1) + ".png").toURI()));
				inactiveDiceImage[i] =
						ImageIO.read(new File(getClass().getResource("resource/" + (i + 1) + "g.png").toURI()));
			}
		} catch(IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch(URISyntaxException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		current = inactiveDiceImage[0];

		addMouseListener(new MouseAdapter() {
			@Override public void mouseClicked(MouseEvent e) {
				setImage(++currentIndex, true);
				repaint();
			}
		});
	}

	public void setImage(int dice, boolean active) {
		if(active) {
			current = activeDiceImage[dice - 1];
		} else {
			current = inactiveDiceImage[dice - 1];
		}
	}

	@Override protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(current, 0, 0, null);
	}
}