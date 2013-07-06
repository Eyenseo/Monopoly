package ui.gui.components;

import objects.value.map.*;
import ui.gui.Model;

import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {
	Map(LayoutManager layout, Model model) {
		super(layout);
		FieldCard card = null;
		int[] colorData;
		Color color;

		for(FieldData fieldData : model.getFieldDataArrayList()) {
			if(fieldData instanceof StreetData) {
				StreetData data = (StreetData) fieldData;
				colorData = data.getCOLOR();
				color = new Color(colorData[0], colorData[1], colorData[2]);

				card = new FieldCard(data.getName(), color, data.getPRICE());
			} else if(fieldData instanceof FacilityData) {
				FacilityData data = (FacilityData) fieldData;

				card = new FieldCard(data.getName(), "/storage/images/werk.png", data.getPRICE());
			} else if(fieldData instanceof StationData) {
				StationData data = (StationData) fieldData;

				card = new FieldCard(data.getName(), "/storage/images/bahnhof.png", data.getPRICE());
			} else if(fieldData instanceof GoData) {
				card = new FieldCard(fieldData.getName(), "/storage/images/los.png");
			} else if(fieldData instanceof TaxData) {
				card = new FieldCard(fieldData.getName(), "/storage/images/steuern.png");
			} else if(fieldData instanceof JailData) {
				card = new FieldCard(fieldData.getName(), "/storage/images/gefaengnis.png");
			} else if(fieldData instanceof ParkingData) {
				card = new FieldCard(fieldData.getName(), "/storage/images/freiParken.png");
			} else if(fieldData instanceof GoToJailData) {
				card = new FieldCard(fieldData.getName(), "/storage/images/geheInsGefaengnis.png");
			} else if(fieldData instanceof ChanceData) {
				card = new FieldCard(fieldData.getName(), "/storage/images/ereignisfeld.png");
			} else if(fieldData instanceof CommunityData) {
				card = new FieldCard(fieldData.getName(), "/storage/images/gemeinschaftsfeld.png");
			}

			add(card);
		}
	}
	//HashMap<playerData, FieldCard>
}
