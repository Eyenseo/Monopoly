package ui.gui.components;

import objects.value.map.FacilityData;
import objects.value.map.PurchasableData;
import objects.value.map.StreetData;

import javax.swing.*;
import java.awt.*;

/**
 * The PurchasableCardPanel displays the data of PurchasableData and its subclasses
 */
public class PurchasableCardPanel extends JPanel {
	private final PurchasableData data;
	public static final  int width  = 175;   //TODO check if this can be replaced with overriding WIDTH
	private static final int height = 220; //TODO check if this can be replaced with overriding HEIGHT
	private final int      cardWidth;
	private final int      cardHeight;
	private final int      gap;
	private final int      gapX;
	private final int      gapY;
	private final Font     headerFont;
	private final Font     normFont;
	private final Font     normInfoFont;
	private final Font     normActiveFont;
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
	private       int      incomeLeadingTextX;
	private       int      incomeLeadingTextY;
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
	//Facility info
	private       String[] facilityInfoText;
	private       int[]    facilityInfoTextX;
	private       int[]    facilityInfoTextY;
	//Mortgage
	private       String   mortgageText;
	private       int      mortgageTextX;
	private       int      mortgageTextY;
	private       int      mortgageValueX;
	private       int      mortgageValueY;

	/**
	 * @param data The value determines the data to be displayed
	 */
	public PurchasableCardPanel(PurchasableData data) {
		super(new GridLayout());
		this.data = data;
		cardWidth = width;
		cardHeight = height;
		gap = 6;
		gapX = 6;
		gapY = 2;
		headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 17);
		normFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		normInfoFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		// Font.BOLD | Font.ITALIC <-- bold and italic via bit or-connection
		normActiveFont = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 12);

		setText();
		calculateTextProperties();
	}

	/**
	 * The method sets text strings needed to display the data
	 */
	private void setText() {
		//Price
		priceText = "Grundstueckswert";

		//Income
		incomeLeadingText = "Miete ";
		if(data instanceof StreetData) {
			incomeText =
					new String[]{"Grundstueck", "Strasse", "1 Haus", "2 Haueser", "3 Haueser", "4 Haueser", "1 Hotel"};
		} else if(data instanceof FacilityData) {
			incomeText = new String[]{"1 Werk", "2 Werke"};
		} else {
			incomeText = new String[]{"1 Bahnhof", "2 Bahnhoefe", "3 Bahnhoefe", "4 Bahnhoefe"};
		}

		//Upgrade
		upgradeText = "Haus / Hotel Preis";

		//Facility info
		facilityInfoText = new String[]{"Die Miete ist der Preis mal", "der Anzahl der Augen,", "die der Mitspieler ",
		                                "gewurfelt hat."};
		//Mortgage
		mortgageText = "Hypothekenwert";
	}

	/**
	 * The method will calculate the positions of each text element on the card
	 */
	//TODO probably replace most variables with static values
	private void calculateTextProperties() {
		FontMetrics fontMetrics;
		int lineHeightAdjustment;
		int lastLine;

		//Header
		fontMetrics = getFontMetrics(headerFont);
		lineHeightAdjustment = fontMetrics.getHeight() / 2 - fontMetrics.getDescent();

		//Name
		nameBackgroundWidth = cardWidth;
		nameBackgroundHeight = 30;
		nameX = (cardWidth - fontMetrics.stringWidth(data.getName())) / 2;
		nameY = nameBackgroundHeight / 2 + lineHeightAdjustment;
		lastLine = nameY;

		//Norm
		fontMetrics = getFontMetrics(normFont);
		lineHeightAdjustment = fontMetrics.getHeight();

		//Price
		priceTextX = gapX;
		priceTextY = gapY + gap + lineHeightAdjustment + lastLine;
		priceValueX = -gapX + cardWidth - fontMetrics.stringWidth("" + data.getPRICE());
		priceValueY = priceTextY;
		lastLine = priceValueY;

		//Income
		incomeLeadingTextX = gapX;
		incomeLeadingTextY = gapY + lastLine + lineHeightAdjustment;
		incomeTextX = new int[incomeText.length];
		incomeTextY = new int[incomeText.length];
		incomeValueX = new int[incomeText.length];
		incomeValueY = new int[incomeText.length];

		for(int i = 0; i < incomeText.length; i++) {
			incomeTextX[i] = gapX + fontMetrics.stringWidth(incomeLeadingText);
			incomeTextY[i] = gapY + lineHeightAdjustment + lastLine;

			incomeValueX[i] = -gapX + cardWidth - fontMetrics.stringWidth("" + data.getINCOME()[i]);
			incomeValueY[i] = incomeTextY[i];

			lastLine = incomeTextY[i];
		}

		if(data instanceof StreetData) {
			//Upgrade
			upgradeTextX = gapX;
			upgradeTextY = gapY + gap + lineHeightAdjustment + lastLine;
			upgradeValueX = -gapX + cardWidth - fontMetrics.stringWidth("" + ((StreetData) data).getUPGRADE());
			upgradeValueY = upgradeTextY;
			lastLine = upgradeTextY;
		} else if(data instanceof FacilityData) {
			//Facility info
			facilityInfoTextX = new int[facilityInfoText.length];
			facilityInfoTextY = new int[facilityInfoText.length];
			facilityInfoTextY[0] = gap;

			for(int i = 0; i < facilityInfoText.length; i++) {
				facilityInfoTextX[i] += gapX;
				facilityInfoTextY[i] += gapY + lineHeightAdjustment + lastLine;
				lastLine = facilityInfoTextY[i];
			}
		}

		//Mortgage
		mortgageTextX = gapX;
		mortgageTextY = gapY + gap + lineHeightAdjustment + lastLine;
		mortgageValueX = -gapX + cardWidth - fontMetrics.stringWidth("" + data.getMORTGAGE());
		mortgageValueY = mortgageTextY;
	}

	/**
	 * The method will draw the data on the surface of the panel
	 *
	 * @param g the value determines the graphic to draw with
	 */
	@Override protected void paintComponent(Graphics g) {
		//		super.paintComponent(g);

		//This sets the Antialiasing for the drawing - without it the text looks really bad.
		((Graphics2D) g)
				.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		//Background
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, cardWidth, cardHeight);

		//Header - color
		if(data instanceof StreetData) {
			g.setColor(new Color(((StreetData) data).getCOLOR()[0], ((StreetData) data).getCOLOR()[1],
			                     ((StreetData) data).getCOLOR()[2]));
		} else {
			g.setColor(new Color(196, 196, 196));
		}
		g.fillRect(0, 0, nameBackgroundWidth, nameBackgroundHeight);

		//Header - text
		g.setFont(headerFont);
		g.setColor(new Color(0, 0, 0));
		g.drawString(data.getName(), nameX, nameY);

		g.setFont(normFont);

		//Price - text
		g.drawString(priceText, priceTextX, priceTextY);
		//Price - value
		g.drawString("" + data.getPRICE(), priceValueX, priceValueY);

		//Income
		for(int i = 0; i < incomeText.length; i++) {
			if(i == 0) {
				g.drawString(incomeLeadingText, incomeLeadingTextX, incomeLeadingTextY);
			}

			if(data.getStage() == i) {
				if(data.isInMortgage()) {
					g.setFont(normInfoFont);
				} else {
					g.setFont(normActiveFont);
				}
			} else {
				g.setFont(normFont);
			}

			g.drawString(incomeText[i], incomeTextX[i], incomeTextY[i]);

			g.drawString("" + data.getINCOME()[i], incomeValueX[i], incomeValueY[i]);
		}
		g.setFont(normFont);

		if(data instanceof StreetData) {
			//Upgrade - text
			g.drawString(upgradeText, upgradeTextX, upgradeTextY);
			//Upgrade - value
			g.drawString("" + ((StreetData) data).getUPGRADE(), upgradeValueX, upgradeValueY);
		} else if(data instanceof FacilityData) {
			//Facility info
			for(int i = 0; i < facilityInfoText.length; i++) {
				g.drawString(facilityInfoText[i], facilityInfoTextX[i], facilityInfoTextY[i]);
			}
		}

		if(!data.isInMortgage()) {
			g.setColor(new Color(0, 0, 0));
		} else {
			g.setColor(new Color(218, 0, 0));
			g.setFont(normActiveFont);
		}
		//Mortgage - text
		g.drawString(mortgageText, mortgageTextX, mortgageTextY);
		//Mortgage - value
		g.drawString("" + data.getMORTGAGE(), mortgageValueX, mortgageValueY);

		//Border
		g.setColor(new Color(0, 0, 0));
		g.drawRect(0, 0, cardWidth - 1, cardHeight - 1);
	}

	/**
	 * @return the return value is the preferred size of the PurchasableCardPanel
	 */
	@Override public Dimension getPreferredSize() {
		return new Dimension(cardWidth, cardHeight);
	}

	/**
	 * @return the return value is the width of the PurchasableCardPanel
	 */
	@Override public int getWidth() {
		return cardWidth;
	}

	/**
	 * @return the return value is the height of the PurchasableCardPanel
	 */
	@Override public int getHeight() {
		return cardHeight;
	}

	/**
	 * @return The return value is the data the PurchasableCardPanel uses to draw itself
	 */

	public PurchasableData getData() {
		return data;
	}
}
