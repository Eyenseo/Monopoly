package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class MapTest {

	public static void main(String[] args) {

		FieldCard card = new FieldCard("Schlossallee", new Color(8, 0, 255), 7000);
		card.updatePlayerPosition(0, 'H', new Color(255, 0, 199), true);
		card.updatePlayerPosition(1, 'A', new Color(50, 255, 206), true);
		card.updatePlayerPosition(2, 'L', new Color(252, 255, 66), true);
		card.updatePlayerPosition(3, 'L', new Color(106, 255, 105), true);
		card.updatePlayerPosition(4, 'O', new Color(255, 31, 72), true);
		card.updatePlayerPosition(5, '!', new Color(24, 16, 255), true);

		FieldCard card2 = new FieldCard("Zusatzsteuer", "/storage/images/werk.png", 7000);
		card2.updatePlayerPosition(0, 'M', new Color(255, 0, 199), true);
		card2.updatePlayerPosition(1, 'O', new Color(50, 255, 206), true);
		card2.updatePlayerPosition(2, 'I', new Color(19, 255, 23), true);
		card2.updatePlayerPosition(3, 'N', new Color(217, 108, 199), true);

		FieldCard card3 = new FieldCard("Gemeinschaftsfeld", "/storage/images/ereignesfeld.png");
		card3.updatePlayerPosition(0, 'H', new Color(247, 255, 40), true);
		card3.updatePlayerPosition(1, 'I', new Color(63, 83, 255), true);

		JPanel panel = new JPanel(new GridLayout());
		panel.setPreferredSize(new Dimension(600, 300));
		panel.add(card);
		panel.add(card2);
		panel.add(card3);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}

class FieldCard extends JPanel {
	public static final int width  = 175;
	public static final int height = 220;
	private final int           gapX;
	private final int           gapY;
	//Fonts
	private final Font          normFont;
	private final Font          headerFont;
	//Color
	private       Color         streetColor;
	private       Color         borderColor;
	private       Color         backgroundColor;
	//Image
	private       BufferedImage image;

	//Header
	private int    headerBackgroundWidth;
	private int    headerBackgroundHeight;
	private String headerName;
	private int    headerNameX;
	private int    headerNameY;
	//Body
	private int    bodyX;
	private int    bodyY;

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
		headerNameX = (width - fontMetrics.stringWidth("" + headerName)) / 2;
		headerNameY = fontMetrics.getHeight();

		bodyX = gapX;
		bodyY = headerNameY + fontMetrics.getHeight() + gapY;

		//base calculation
		fontMetrics = getFontMetrics(normFont);
		priceX = (width - fontMetrics.stringWidth(this.price)) / 2;
		priceY = height - fontMetrics.getHeight();
	}

	FieldCard(String headerName, String image, int price) {
		this.headerName = headerName;
		try {
			this.image = ImageIO.read(new File(getClass().getResource(image).toURI()));
		} catch(IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch(URISyntaxException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		this.price = "" + price + " DM";

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
		headerNameX = (width - fontMetrics.stringWidth("" + headerName)) / 2;
		headerNameY = fontMetrics.getHeight();

		bodyX = gapX;
		bodyY = headerNameY + fontMetrics.getHeight() + gapY;

		//base calculation
		fontMetrics = getFontMetrics(normFont);
		priceX = (width - fontMetrics.stringWidth(this.price)) / 2;
		priceY = height - fontMetrics.getHeight();
	}

	FieldCard(String headerName, String image) {
		this.headerName = headerName;
		try {
			this.image = ImageIO.read(new File(getClass().getResource(image).toURI()));
		} catch(IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch(URISyntaxException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		price = null;

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
		headerNameX = (width - fontMetrics.stringWidth("" + headerName)) / 2;
		headerNameY = fontMetrics.getHeight();

		bodyX = gapX;
		bodyY = headerNameY + fontMetrics.getHeight() + gapY;
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
		int lastElementY = 0;

		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);

		//if it is a Street with a Color
		if(streetColor != null) {
			g.setColor(streetColor);
			g.fillRect(0, 0, headerBackgroundWidth, headerBackgroundHeight);
			lastElementY = headerBackgroundHeight;
		} else {
			lastElementY = 9;
		}

		g.setColor(borderColor);
		g.drawRect(0, 0, width, height);

		g.setFont(headerFont);
		g.drawString(headerName, headerNameX, lastElementY + headerNameY);

		lastElementY += headerNameY + gapY;

		if(image != null) {
			g.drawImage(image, (width - image.getWidth(null)) / 2, 50, null);
		}

		if(price != null) {
			g.setFont(normFont);
			g.drawString(price, priceX, priceY);
		}

		int x = bodyX;
		int y = bodyY;
		y = 75;
		int player = 0;

		for(PlayerFigure figure : playerHashMap.values()) {
			figure.setBounds(x, y, PlayerFigure.width, PlayerFigure.height);
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
	public static int width  = 50;
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
