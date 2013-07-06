package ui.gui.components;

import javax.swing.*;
import java.awt.*;

public class PlayerFigure extends JPanel {
	public static int width  = 50;
	public static int height = width;
	private Color color;
	private Color borderColor;
	private char  name;
	private Font  font;
	private int   nameX;
	private int   nameY;

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
