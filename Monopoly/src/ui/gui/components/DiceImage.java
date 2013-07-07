package ui.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The DiceImage is a class to display the numbers from 1 to 6 as a image that represents a dice.
 */
public class DiceImage extends JPanel {
	Image[] activeDiceImage   = new Image[6];
	Image[] inactiveDiceImage = new Image[6];
	Image current;
	int currentIndex = 0;
	boolean active;

	/**
	 * The constructor loads all images
	 */
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
			e.printStackTrace();
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}
		current = inactiveDiceImage[0];
		active = false;
	}

	/**
	 * Only numbers in between 0 and 7 are allowed.
	 *
	 * @param dice   The value determines the number to be displayed
	 * @param active The value determines if the image should be displayed gray or white
	 */
	// TODO use the set enabled method to set the image gray?
	public void setImage(int dice, boolean active) {
		if(dice > 0 && dice < 7) {
			if(active) {
				current = activeDiceImage[dice - 1];
			} else {
				current = inactiveDiceImage[dice - 1];
			}
		} else {
			throw new IllegalArgumentException("Only Integer between 0 and 7 are allowed");
		}
	}

	/**
	 * The method paints the selected image.
	 *
	 * @param g The value determines the graphics object that should be used
	 */
	@Override protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(current, 0, 0, null);
	}

	/**
	 * The method sets the current index of the number to be displayed, fom 0 to 5(0=1,5=6)
	 *
	 * @param currentIndex The value determines the index of the number to be displayed
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
		setImage(currentIndex, active);
		repaint();
	}

	/**
	 * @param active The value determines in which state the number should be displayed false = gray, true = white
	 */
	public void setActive(boolean active) {
		this.active = active;
		setImage(currentIndex, active);
		repaint();
	}
}
