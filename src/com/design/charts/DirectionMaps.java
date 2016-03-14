package com.design.charts;

import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;


public class DirectionMaps extends VerticalLayout {
	
	GoogleMap map;
	
	public DirectionMaps () {
		ui();
	}
	
	private void ui() {
		map = new GoogleMap("AIzaSyBbcq6id_NE2X_M-Fr7vXqCV6DLLYcDZ78", null, "english");
		map.setSizeFull();
		
		map.addMarker(null, new LatLon(42.983, -81.2497), false, null);
		map.setCenter(new LatLon(42.9837, -81.2497));
	
		
		this.addComponent(map);
		this.setComponentAlignment(map, Alignment.MIDDLE_CENTER);
	}
	
}
