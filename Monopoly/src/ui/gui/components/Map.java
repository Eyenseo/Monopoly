package ui.gui.components;

import objects.value.map.FacilityData;
import objects.value.map.FieldData;
import objects.value.map.StationData;
import objects.value.map.StreetData;
import ui.gui.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Map extends JPanel {
	Map(LayoutManager layout, Model model) {
		super(layout);
		FieldCard card;
		int[] colorData;
		Color color;

		for(FieldData data : model.getFieldDataArrayList()) {
			if(data instanceof StreetData) {
				StreetData sData = (StreetData) data;
				colorData = sData.getCOLOR();
				color = new Color(colorData[0], colorData[1], colorData[2]);

				card = new FieldCard(sData.getName(), color, sData.getPRICE());
			} else if(data instanceof FacilityData) {
				FacilityData fData = (FacilityData) data;

				card = new FieldCard(fData.getName(), "/storage/images/werk.png", fData.getPRICE());
			} else if(data instanceof StationData) {
				StationData stData = (StationData) data;

				card = new FieldCard(stData.getName(), "/storage/images/bahnhof.png", stData.getPRICE());
			} else {
				card = new FieldCard("PLACEHOLDER", new Color(0, 0, 0), 555);
			}

			add(card);
		}
	}

	@Override protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform backUpTransform = g2.getTransform();
		AffineTransform transform = g2.getTransform();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension preferredSize = getLayout().preferredLayoutSize(this);
		double scaleX = (double) getWidth() / preferredSize.width;
		double scaleY = (double) getHeight() / preferredSize.height;
		double scale = 1;
		if(scaleX > scaleY) {
			scale = scaleY;
		} else {
			scale = scaleX;
		}
		transform.setToScale(scale, scale);
		g2.setTransform(transform);
		super.paintComponent(g);    //To change body of overridden methods use File | Settings | File Templates.
		g2.setTransform(backUpTransform);
	}

	//HashMap<playerData, FieldCard>
}
