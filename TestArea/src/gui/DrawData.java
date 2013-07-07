package gui;

import javax.swing.*;
import java.awt.*;

public class DrawData {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(frame.getRootPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(800, 400));

		frame.add(new PurchasableCard(new Data()));

		frame.pack();
		frame.setVisible(true);
	}
}

class PurchasableCard extends JPanel {
	private final Data     data;
	private final int      width;
	private final int      height;
	private final int      gap;
	private final int      gapX;
	private final int      gapY;
	private final Font     headerFont;
	private final Font     normFont;
	//Header
	private       int      nameBackgroundWidth;
	private       int      nameBackgroundHeight;
	private       int      nameX;
	private       int      nameY;
	//Price
	private       String   priceText;
	private       int      priceTextX;
	private       int      priceTextY;
	private       int      priceValueX;
	private       int      priceValueY;
	//Income
	private       String   incomeLeadingText;
	private       String[] incomeText;
	private       int[]    incomeTextX;
	private       int[]    incomeTextY;
	private       int[]    incomeValueX;
	private       int[]    incomeValueY;
	//Upgrade
	private       String   upgradeText;
	private       int      upgradeTextX;
	private       int      upgradeTextY;
	private       int      upgradeValueX;
	private       int      upgradeValueY;
	//Mortgage
	private       String   mortgageText;
	private       int      mortgageTextX;
	private       int      mortgageTextY;
	private       int      mortgageValueX;
	private       int      mortgageValueY;

	PurchasableCard(Data data) {
		this.data = data;
		width = 175;
		height = 225;
		gap = 6;
		gapX = 4;
		gapY = 2;
		headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 17);
		normFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		setText();
		calculateTextProperties();
	}

	private void setText() {
		//Price
		priceText = "Grundstueckwert";
		//Income
		incomeLeadingText = "Miete ";
		incomeText = new String[]{"Grundstueck", "Strasse", "1 Haus", "2 Haueser", "3 Haueser", "4 Haueser", "1 Hotel"};
		//Upgrade
		upgradeText = "Haus / Hotel Preis";
		//Mortgage
		mortgageText = "Hypothekenwert";
	}

	private void calculateTextProperties() {
		FontMetrics fontMetrics;
		int lineHeightAdjustment;

		//Header
		fontMetrics = getFontMetrics(headerFont);
		lineHeightAdjustment = fontMetrics.getHeight() / 2 - fontMetrics.getDescent();

		//Header name
		nameBackgroundWidth = width;
		nameBackgroundHeight = 30;
		nameX = (width - fontMetrics.stringWidth(data.name)) / 2;
		nameY = nameBackgroundHeight / 2 + lineHeightAdjustment;

		//Norm
		fontMetrics = getFontMetrics(normFont);
		lineHeightAdjustment = fontMetrics.getHeight();

		//Price
		priceTextX = gapX;
		priceTextY = gapY + gap + nameBackgroundHeight + fontMetrics.getHeight() / 2;
		priceValueX = -gapX + width - fontMetrics.stringWidth("" + data.PRICE);
		priceValueY = priceTextY;

		//Income
		incomeTextX = new int[incomeText.length];
		incomeTextY = new int[incomeText.length];
		incomeValueX = new int[incomeText.length];
		incomeValueY = new int[incomeText.length];
		for(int i = 0; i < incomeText.length; i++) {
			if(i == 0) {
				incomeTextX[i] = gapX;
				incomeTextY[i] = gapY + gap + lineHeightAdjustment + priceTextY;
			} else {
				incomeTextX[i] = gapX + fontMetrics.stringWidth(incomeLeadingText);
				incomeTextY[i] = gapY + lineHeightAdjustment + incomeTextY[i - 1];
			}
			incomeValueX[i] = -gapX + width - fontMetrics.stringWidth("" + data.INCOME[i]);
			incomeValueY[i] = incomeTextY[i];
		}

		//Upgrade
		upgradeTextX = gapX;
		upgradeTextY = gapY + gap + lineHeightAdjustment + incomeTextY[incomeTextY.length - 1];
		upgradeValueX = -gapX + width - fontMetrics.stringWidth("" + data.UPGRADE);
		upgradeValueY = upgradeTextY;

		//Mortgage
		mortgageTextX = gapX;
		mortgageTextY = gapY + gap + lineHeightAdjustment + upgradeTextY;
		mortgageValueX = -gapX + width - fontMetrics.stringWidth("" + data.MORTGAGE);
		mortgageValueY = mortgageTextY;
	}

	@Override protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		//This sets the Antialiasing for the drawing on - without the text looks really bad.
		((Graphics2D) g)
				.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		//Background
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, width, height);

		//Header - color
		g.setColor(new Color(data.COLOR[0], data.COLOR[1], data.COLOR[2]));
		g.fillRect(0, 0, nameBackgroundWidth, nameBackgroundHeight);
		//Header - text
		g.setFont(headerFont);
		g.setColor(new Color(0, 0, 0));
		g.drawString(data.name, nameX, nameY);

		g.setFont(normFont);

		//Price - text
		g.drawString(priceText, priceTextX, priceTextY);
		//Price - value
		g.drawString("" + data.PRICE, priceValueX, priceValueY);

		//Income
		for(int i = 0; i < incomeText.length; i++) {
			if(i == 0) {
				g.drawString(incomeLeadingText + incomeText[i], incomeTextX[i], incomeTextY[i]);
			} else {
				g.drawString(incomeText[i], incomeTextX[i], incomeTextY[i]);
			}
			g.drawString("" + data.INCOME[i], incomeValueX[i], incomeValueY[i]);
		}

		//Upgrade - text
		g.drawString(upgradeText, upgradeTextX, upgradeTextY);
		//Upgrade - value
		g.drawString("" + data.UPGRADE, upgradeValueX, upgradeValueY);

		//Mortgage - text
		g.drawString(mortgageText, mortgageTextX, mortgageTextY);
		//Mortgage - value
		g.drawString("" + data.MORTGAGE, mortgageValueX, mortgageValueY);
	}

	@Override public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}

class Data {
	private final String  className;
	private final int     fieldNumber;
	final         String  name;
	final         int[]   INCOME;
	final         int     MORTGAGE;
	final         int     PRICE;
	private final boolean inMortgage;
	private final int     stage;
	final         int[]   COLOR;
	final         int     UPGRADE;

	Data() {
		className = "test";
		fieldNumber = 3;
		name = "Schlossallee";
		INCOME = new int[]{1000, 2000, 4000, 12000, 28000, 34000, 40000};
		MORTGAGE = 4000;
		PRICE = 8000;
		inMortgage = false;
		stage = 0;
		COLOR = new int[]{243, 23, 90};
		UPGRADE = 4000;
	}
}
