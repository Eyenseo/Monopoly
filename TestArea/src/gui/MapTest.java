package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class MapTest {

	public static void main(String[] args) {

		//JPanel panel = new JPanel(new GridLayout());
		JPanel panel = new JPanel(new MapLayout());
		FieldCard card;

		card = new FieldCard("humdi", new Color(12, 128, 102), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(12, 128, 102), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(12, 128, 102), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(128, 30, 24), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(128, 30, 24), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(128, 30, 24), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(28, 128, 14), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(28, 128, 14), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(28, 128, 14), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(108, 6, 128), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(108, 6, 128), 1254);
		panel.add(card);
		card = new FieldCard("humdi", new Color(108, 6, 128), 1254);
		panel.add(card);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}

class FieldCard extends JPanel {
	public static int widthCard  = 175;
	public static int heightCard = 220;
	public int rotation;

	int width;
	int height;

	private final int                            gapX;
	private final int                            gapY;
	//Fonts
	private final Font                           normFont;
	private final Font                           headerFont;
	//Color
	private       Color                          streetColor;
	private       Color                          borderColor;
	private       Color                          backgroundColor;
	//Image
	private       BufferedImage                  image;
	//Header
	private       int                            headerBackgroundWidth;
	private       int                            headerBackgroundHeight;
	private       String                         headerName;
	private       int                            headerNameX;
	private       int                            headerNameY;
	//Body
	private       int                            bodyY;
	//Base
	private       String                         price;
	private       int                            priceX;
	private       int                            priceY;
	//do playerHashMap
	private       HashMap<Integer, PlayerFigure> playerHashMap;

	FieldCard(String headerName, Color streetColor, int price) {
		this.headerName = headerName;
		this.streetColor = streetColor;
		this.price = "" + price + " DM";

		headerBackgroundWidth = widthCard;
		headerBackgroundHeight = 40;

		width = widthCard;
		height = heightCard;

		gapX = 6;
		gapY = 2;

		headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 17);
		normFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		borderColor = new Color(0, 0, 0);
		backgroundColor = new Color(192, 206, 155);

		playerHashMap = new HashMap<Integer, PlayerFigure>();

		changePurchasableTextPlacement();
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

		headerBackgroundWidth = widthCard;
		headerBackgroundHeight = 40;

		width = widthCard;
		height = heightCard;

		gapX = 6;
		gapY = 2;

		headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 17);
		normFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		borderColor = new Color(0, 0, 0);
		backgroundColor = new Color(192, 206, 155);

		playerHashMap = new HashMap<Integer, PlayerFigure>();

		changePurchasableTextPlacement();
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

		headerBackgroundWidth = widthCard;
		headerBackgroundHeight = 40;

		width = widthCard;
		height = heightCard;

		gapX = 6;
		gapY = 2;

		headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 17);
		normFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

		borderColor = new Color(0, 0, 0);
		backgroundColor = new Color(192, 206, 155);

		playerHashMap = new HashMap<Integer, PlayerFigure>();

		//header calculation
		changeTextPlacement();
	}

	public void updatePlayerPosition(int id, char playerName, Color playerColor, boolean add) {
		if(add) {
			playerHashMap.put(id, new PlayerFigure(playerColor, playerName));
		} else {
			playerHashMap.remove(id);
		}
	}

	@Override public void paintComponent(Graphics g1D) {
		Graphics2D g = (Graphics2D) g1D;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		AffineTransform backUp = g.getTransform();

		int widthBuffer;

		switch(rotation) {
			case 90:
				widthBuffer = width;
				width = height;
				height = widthBuffer;
				g.translate(width, 0);
				break;
			case 180:
				g.translate(width, height);
				break;
			case 270:
				widthBuffer = width;
				width = height;
				height = widthBuffer;
				g.translate(0, height);
		}

		g.rotate(rotation * (Math.PI / 180));

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
			g.drawImage(image, (width - image.getWidth(null)) / 2, (height + lastElementY - image.getHeight(null)) / 2,
			            null);
		}

		if(price != null) {
			g.setFont(normFont);
			g.drawString(price, priceX, priceY);
		}

		int x = 0;
		int y = bodyY;
		y = 75;
		x = (width - ((PlayerFigure.width * 3) + (gapX * 2))) / 2;
		int player = 0;

		for(PlayerFigure figure : playerHashMap.values()) {
			figure.setBounds(x, y, PlayerFigure.width, PlayerFigure.height);
			figure.paintComponent(g);
			if(player > 1) {
				y = y + PlayerFigure.height + gapY;
				x = (width - ((PlayerFigure.width * 3) + (gapX * 2))) / 2;
				player = -1;
			} else {
				x = x + PlayerFigure.width + gapX;
			}
			player++;
		}

		g.setTransform(backUp);
	}

	public void changeTextPlacement() {
		//header calculation
		FontMetrics fontMetrics = getFontMetrics(headerFont);
		headerNameX = (width - fontMetrics.stringWidth("" + headerName)) / 2;
		headerNameY = fontMetrics.getHeight();

		bodyY = headerNameY + fontMetrics.getHeight() + gapY;
	}

	public void changePurchasableTextPlacement() {
		FontMetrics fontMetrics = getFontMetrics(headerFont);
		changeTextPlacement();
		fontMetrics = getFontMetrics(normFont);
		priceX = (width - fontMetrics.stringWidth(this.price)) / 2;
		priceY = height - fontMetrics.getHeight();
	}

	public void setRotation(Rotation rotation) {
		switch(rotation) {
			case BOTTOM:
				this.rotation = 0;
				break;
			case LEFT:
				this.rotation = 90;
				break;
			case TOP:
				this.rotation = 180;
				break;
			case RIGHT:
				this.rotation = 270;
				break;
		}
	}

	@Override public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		if(this.rotation == 0 || this.rotation == 180) {
			changeFieldSize(width, height);
		} else {
			changeFieldSize(height, width);
		}
	}

	public void changeFieldSize(int width, int height) {
		this.width = width;
		this.height = height;

		this.headerBackgroundWidth = width;
		changePurchasableTextPlacement();
	}

	public enum Rotation {
		BOTTOM, LEFT, TOP, RIGHT
	}
}

class PlayerFigure extends JPanel {
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
