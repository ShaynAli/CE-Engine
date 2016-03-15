package com.design.charts;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.design.persistence.Directions;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;


public class DirectionMaps extends VerticalLayout {
	
	GoogleMap map;
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();
	
	public DirectionMaps () {
		ui();
	}
	
	private void ui() {
		map = new GoogleMap("AIzaSyBbcq6id_NE2X_M-Fr7vXqCV6DLLYcDZ78", null, "english");
		map.setSizeFull();
		
		map.setCenter(new LatLon(42.9837, -81.2497));
		map.setZoom(6);
	
		
		this.addComponent(map);
		this.setComponentAlignment(map, Alignment.MIDDLE_CENTER);
		
		loadData();
	}
	
	private void loadData () {
		String qu = "SELECT x FROM Directions AS x";
		List <Directions> dir = em.createQuery(qu).getResultList();
		for (int i = 0; i < dir.size(); i++) {
			if (dir.get(0).getQueries().getSuccessful()) {
				if (dir.get(i).getOrigLat() != null) {
					map.addMarker(null, new LatLon(dir.get(i).getOrigLat(), dir.get(i).getOrigLon()), false, null);
				}
				if (dir.get(i).getDestLat() != null) {
					map.addMarker(null, new LatLon(dir.get(i).getDestLat(), dir.get(i).getDestLon()), false, null);
				}
			}
		}
	}
	
	public void receiveLocation (Directions dir) {
		if (dir.getOrigLat() != null) {
			map.addMarker(null, new LatLon(dir.getOrigLat(), dir.getOrigLon()), false, null);
		}
		if (dir.getDestLat() != null) {
			map.addMarker(null, new LatLon(dir.getDestLat(), dir.getDestLon()), false, null);
		}
	}

	public void clear() {
		this.removeComponent(map);
		map = new GoogleMap("AIzaSyBbcq6id_NE2X_M-Fr7vXqCV6DLLYcDZ78", null, "english");
		map.setSizeFull();
		
		map.setCenter(new LatLon(42.9837, -81.2497));
		map.setZoom(6);
	
		
		this.addComponent(map);
		this.setComponentAlignment(map, Alignment.MIDDLE_CENTER);
		
	}
	
}
