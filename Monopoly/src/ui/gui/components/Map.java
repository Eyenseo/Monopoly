package ui.gui.components;

import objects.events.ModelEvent;
import objects.listeners.ModelEventListener;
import objects.value.PlayerData;
import objects.value.map.*;
import ui.gui.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Map extends JPanel {
	ArrayList<FieldCard> fieldCardArrayList;

	Map(LayoutManager layout, final Model model) {
		super(layout);
		FieldCard card = null;
		int[] colorData;
		Color color;
		fieldCardArrayList = new ArrayList<FieldCard>();

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
			fieldCardArrayList.add(card);
		}

		ModelEventListener playerUpdate = new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						for(int i = 0; i < fieldCardArrayList.size(); i++) {
							FieldCard fieldCard = fieldCardArrayList.get(i);
							fieldCard.removeAll();
							for(PlayerData playerData : model.getPlayerHashMap().values()) {
								if(playerData.getPosition().getFieldNumber() == i) {
									fieldCard.updatePlayerPosition(playerData.getId(), playerData.getName().charAt(0),
									                               playerData.getColor());
								}
							}
						}
						repaint();
					}
				});
			}
		};
		model.addModelEventListener(Model.ModelEventName.POSITION, playerUpdate);
		model.addModelEventListener(Model.ModelEventName.PLAYERREMOVED, playerUpdate);
	}
}
