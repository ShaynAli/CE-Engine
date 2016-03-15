package com.design.charts;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.design.persistence.Weather;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

public class WeatherMaps extends VerticalLayout {

	GoogleMap map;
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();
	
	public WeatherMaps () {
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
		String qu = "SELECT x FROM Weather AS x";
		
		List <Weather> weather = em.createQuery(qu).getResultList();
		
		for (int i = 0; i < weather.size(); i++) {
			if (weather.get(i).getLatitude() != null) {
				map.addMarker(null, new LatLon(weather.get(i).getLatitude(), weather.get(i).getLongitude()), false, null);
			}
		}
		
	}

	public void receiveWeather(Weather weather) {
		if (weather.getLatitude() != null) {
			map.addMarker(null, new LatLon(weather.getLatitude(), weather.getLongitude()), false, null);
		}
	}
	
}
