package ui.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

//JAVADOC
public class DiceImage extends JPanel {
	Image[] activeDiceImage   = new Image[6];
	Image[] inactiveDiceImage = new Image[6];
	Image current;
	int currentIndex = 0;
	boolean active;

	//JAVADOC
	public DiceImage() {
		setPreferredSize(new Dimension(32, 32));
		try {
			for(int i = 0; i < activeDiceImage.length; i++) {

				activeDiceImage[i] = ImageIO.read(
						new File(getClass().getResource("/storage/images/dice/" + (i + 1) + ".png").toURI()));
				inactiveDiceImage[i] = ImageIO.read(
						new File(getClass().getResource("/storage/images/dice/" + (i + 1) + "g.png").toURI()));
			}
		} catch(IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch(URISyntaxException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		current = inactiveDiceImage[0];
		active = false;
	}

	//JAVADOC
	public void setImage(int dice, boolean active) {
		if(active) {
			current = activeDiceImage[dice - 1];
		} else {
			current = inactiveDiceImage[dice - 1];
		}
	}

	//JAVADOC
	@Override protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(current, 0, 0, null);
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
		setImage(currentIndex, active);
		repaint();
	}

	public void setActive(boolean active) {
		this.active = active;
		setImage(currentIndex, active);
		repaint();
	}
}
