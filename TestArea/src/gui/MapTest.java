package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MapTest {

	public static void main(String[] args) {

		FieldCard card = new FieldCard("Schlossallee", new Color(8, 0, 255), 7000);
		card.updatePlayerPosition(2, 'J', new Color(255, 0, 199), true);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout());
		panel.setPreferredSize(new Dimension(200, 200));
		panel.add(card);

		frame.add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}

class FieldCard extends JPanel {
	public static final  int width  = 175;
	private static final int height = 220;
	private final int    cardWidth;
	private final int    cardHeight;
	private final int    gapX;
	private final int    gapY;
	private final Font   normFont;
	//Filed data
	private       Color  streetColor;
	private       Color  borderColor;
	private       Color  backgroundColor;
	//Header
	private final Font   headerFont;
	private       int    headerBackgroundWidth;
	private       int    headerBackgroundHeight;
	private       String headerName;
	private       int    headerNameX;
	private       int    headerNameY;
	//Body
	private       int    bodyX;
	private       int    bodyY;

	//Base
	private String price;
	private int    priceX;
	private int    priceY;

	//do playerHashMap
	private HashMap<Integer, PlayerFigure> playerHashMap;

	FieldCard(String headerName, Color streetColor, int price) {
		this.headerName = headerName;
		this.streetColor = streetColor;
		this.price = "" + price + " DM";

		cardWidth = width;
		cardHeight = height;

		headerBackgroundWidth = width;
		headerBackgroundHeight = 40;

		gapX = 6;
		gapY = 2;

		headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 17);
		normFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		borderColor = new Color(0, 0, 0);
		backgroundColor = new Color(255, 255, 255);

		playerHashMap = new HashMap<Integer, PlayerFigure>();

		//header calculation
		FontMetrics fontMetrics = getFontMetrics(headerFont);
		headerNameX = (headerBackgroundWidth - fontMetrics.stringWidth("" + headerName)) / 2;
		headerNameY = headerBackgroundHeight + fontMetrics.getHeight();

		bodyX = gapX;
		bodyY = headerNameY + fontMetrics.getHeight() + gapY;

		//base calculation
		fontMetrics = getFontMetrics(normFont);
		priceX = (width - fontMetrics.stringWidth(this.price)) / 2;
		priceY = height - fontMetrics.getHeight();
	}

	public void updatePlayerPosition(int id, char playerName, Color playerColor, boolean add) {
		if(add) {
			playerHashMap.put(id, new PlayerFigure(playerColor, playerName));
		} else {
			playerHashMap.remove(id);
		}
	}

	@Override public void paintComponent(Graphics g) {
		((Graphics2D) g)
				.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);

		g.setColor(streetColor);
		g.fillRect(0, 0, headerBackgroundWidth, headerBackgroundHeight);

		g.setColor(borderColor);
		g.drawRect(0, 0, width, height);

		g.setFont(headerFont);
		g.drawString(headerName, headerNameX, headerNameY);

		g.setFont(normFont);
		g.drawString(price, priceX, priceY);

		int x = bodyX;
		int y = bodyY;
		int player = 0;

		for(PlayerFigure figure : playerHashMap.values()) {
			figure.setBounds(100, y, PlayerFigure.width, PlayerFigure.height);
			figure.paintComponent(g);
			if(player > 1) {
				y = y + PlayerFigure.height + gapY;
				x = gapX;
				player = -1;
			} else {
				x = x + PlayerFigure.width + gapX;
			}
			player++;
		}
	}
}

class PlayerFigure extends JPanel {
	private Color color;
	private Color borderColor;
	private char  name;
	public static int width  = 25;
	public static int height = width;
	private Font font;
	private int  nameX;
	private int  nameY;

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
		g.fillOval(getBounds().x, getBounds().y, getBounds().x + width, getBounds().y + height);
		//Color
		g.setColor(color);
		g.fillOval(getBounds().x + 2, getBounds().y + 2, getBounds().x + width - 4, getBounds().y + height - 4);
		//Name
		g.setColor(borderColor);
		g.setFont(font);
		g.drawString("" + name, getBounds().x + nameX, getBounds().y + nameY);
	}
}
