package ui.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * The PlayerFigure class is used to display a player figure on a FieldCard
 */
class PlayerFigure extends JPanel {
	public static final int width  = 50;
	public static final int height = width;
	private final Color color;
	private final Color borderColor;
	private final char  name;
	private final Font  font;
	private final int   nameX;
	private final int   nameY;

	/**
	 * @param color The value determines the color of the player that shall be used to display the player figure
	 * @param name  The value determines the first character of the player name
	 */
	PlayerFigure(Color color, char name) {
		this.color = color;
		this.name = name;

		borderColor = new Color(0, 0, 0);
		font = new Font(Font.SANS_SERIF, Font.BOLD, 17);

		FontMetrics fontMetrics = getFontMetrics(font);
		int lineHeightAdjustment = fontMetrics.getHeight() / 2 - fontMetrics.getDescent();
		nameX = (width - fontMetrics.stringWidth("" + name)) / 2;
		nameY = height / 2 + lineHeightAdjustment;
	}

	/**
	 * The method will paint hte player figure
	 *
	 * @param g The value determines the graphics object to be used
	 */
	@Override public void paintComponent(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//Border
		g.setColor(borderColor);
		g.fillOval(getBounds().x, getBounds().y, width, height);
		//Color
		g.setColor(color);
		g.fillOval(getBounds().x + 2, getBounds().y + 2, width - 4, height - 4);
		//Name
		g.setColor(borderColor);
		g.setFont(font);
		g.drawString("" + name, getBounds().x + nameX, getBounds().y + nameY);
	}
}
