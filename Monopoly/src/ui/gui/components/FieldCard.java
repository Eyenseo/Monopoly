package ui.gui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class FieldCard extends JPanel {
	public static final  int  widthCard  = 175;
	public static final  int  heightCard = 220;
	private static final int  gapX       = 6;
	private static final int  gapY       = 2;
	//Fonts
	private static final Font normFont   = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private static final Font headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 17);

	public int rotation;

	int width;
	int height;

	//Color
	private static final Color borderColor     = new Color(0, 0, 0);
	private static final Color backgroundColor = new Color(192, 206, 155);
	private Color         streetColor;
	//Image
	private BufferedImage image;
	//Header
	private static final int headerBackgroundHeight = 40;
	private String headerName;
	private int    headerNameX;
	private int    headerNameY;
	//Base
	private String price;
	private int    priceX;
	private int    priceY;

	double scale;

	//do playerHashMap
	private HashMap<Integer, PlayerFigure> playerHashMap;

	FieldCard(String headerName, Color streetColor, int price) {
		this.headerName = headerName;
		this.streetColor = streetColor;
		this.price = "" + price + " DM";
		scale = 1;
		width = widthCard;
		height = heightCard;

		playerHashMap = new HashMap<Integer, PlayerFigure>();

		changePurchasableTextPlacement();
	}

	FieldCard(String headerName, String image, int price) {
		this.headerName = headerName;
		try {
			this.image = ImageIO.read(new File(getClass().getResource(image).toURI()));
		} catch(IOException e) {
			e.printStackTrace();
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}
		this.price = "" + price + " DM";

		width = widthCard;
		height = heightCard;

		playerHashMap = new HashMap<Integer, PlayerFigure>();

		changePurchasableTextPlacement();
	}

	FieldCard(String headerName, String image) {
		this.headerName = headerName;
		try {
			this.image = ImageIO.read(new File(getClass().getResource(image).toURI()));
		} catch(IOException e) {
			e.printStackTrace();
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}
		price = null;

		width = widthCard;
		height = heightCard;

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

	@Override public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		AffineTransform backUp = g.getTransform();

		g.scale(scale, scale);

		switch(rotation) {
			case 90:
				g.translate(height, 0);
				break;
			case 180:
				g.translate(width, height);
				break;
			case 270:
				g.translate(0, width);
		}

		g.rotate(rotation * (Math.PI / 180));

		int lastElementY;

		g.setColor(borderColor);
		g.fillRect(0, 0, width, height);

		g.setColor(backgroundColor);
		g.fillRect(1, 1, width - 2, height - 2);

		//if it is a Street with a Color
		if(streetColor != null) {
			g.setColor(streetColor);
			g.fillRect(1, 1, width - 2, headerBackgroundHeight);
			lastElementY = headerBackgroundHeight;
		} else {
			lastElementY = 9;
		}

		g.setColor(borderColor);

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

		int x = (width - ((PlayerFigure.width * 3) + (gapX * 2))) / 2;
		int y = 75;
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

		if(width == height) {
			this.width = heightCard;
		} else {
			this.width = widthCard;
		}
		changePurchasableTextPlacement();
	}

	public void changePurchasableTextPlacement() {
		changeTextPlacement();

		FontMetrics fontMetrics = getFontMetrics(normFont);

		priceX = (width - fontMetrics.stringWidth(price)) / 2;
		priceY = height - fontMetrics.getHeight();
	}

	public void changeTextPlacement() {
		//header calculation
		FontMetrics fontMetrics = getFontMetrics(headerFont);
		headerNameX = (width - fontMetrics.stringWidth("" + headerName)) / 2;
		headerNameY = fontMetrics.getHeight();
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public enum Rotation {
		BOTTOM, LEFT, TOP, RIGHT
	}
}
