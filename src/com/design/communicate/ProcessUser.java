/**
 * Processes and handles a user for the servlet
 */
package com.design.communicate;

import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.design.persistence.Directions;
import com.design.persistence.News;
import com.design.persistence.Queries;
import com.design.persistence.Users;
import com.design.persistence.Weather;
import com.example.designgui.Broadcaster;
import net.sf.sprockets.google.Place;
import net.sf.sprockets.google.Places;
import net.sf.sprockets.google.Places.Params;
import java.net.URL;

public class ProcessUser {
	
	public ProcessUser () {
		
	}
	
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();

	public static Users userExists (String from) {
		Users user = new Users();
		user.setPhone(from);
		user.setFirstUse(new Date());
		
		String str = "SELECT x FROM Users AS x WHERE x.phone='" + from + "'";
		List <Users> users = em.createQuery(str).getResultList();
		
		
		if (users == null || users.size() == 0) {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			return user;
		} else {
			return users.get(0);
		}
	}
	
	public static void persistWeather (Queries query, String output) {
		Weather weather = new Weather();
		
		
		if (query.getSuccessful()) {
			if (query.getType().equals("sms")) {
				System.out.println("sending");
				Communicate.sendText(output, query.getPhone().getPhone());
				System.out.println("sent");
				
				String key = "&key=AIzaSyAjXKpbYwL4CFbXVtNbLKKE9cOrlrsI05Q";
				String qu = "https://maps.googleapis.com/maps/api/geocode/json?address=";
				
				try {
					String quer = qu + query.getQuery().toLowerCase().replace("weather", "").replace(" in ", "").replace(" ", "+");
					URL url = new URL(quer);
					JsonReader json = Json.createReader(url.openStream());
					JsonObject obj = json.readObject().getJsonArray("results").getJsonObject(0).getJsonObject("geometry").getJsonObject("location");
					weather.setLatitude(obj.getJsonNumber("lat").doubleValue());
					weather.setLongitude(obj.getJsonNumber("lng").doubleValue());
				} catch (Exception ex) {
					
				}
			}
		} else {
			if (query.getType().equals("sms")) {
				Communicate.sendText("Unable to process your weather query.", query.getPhone().getPhone());
			}
		}
		
		query.setId(persistQuery(query));
		
		weather.setId(query.getId());
		weather.setQueries(query);

		Broadcaster.broadcast(weather);
		
		em.getTransaction().begin();
		em.persist(weather);
		em.getTransaction().commit();
	}
	
	public static void persistDirection (String [] data, Queries query, int distance, int time) {
		if (query.getType().equals("sms")) {
			persistDirection(data, query, distance, time, null);
		} else {
			
		}
		
	}
	
	public static void persistDirection (String [] data, Queries query, int distance, int time, String directions) {
		if (directions == null) {
			Communicate.sendText("Unable to parse your directions query.", query.getPhone().getPhone());
		} else {
			Communicate.sendText(directions, query.getPhone().getPhone());
		}
		
		Directions dirc = new Directions();
		dirc.setDestination(data[1]);
		dirc.setOrigin(data[0]);
		dirc.setQueries(query);
		dirc.setTime((double) time);
		dirc.setDistance((double) distance);
		
		String key = "&key=AIzaSyAjXKpbYwL4CFbXVtNbLKKE9cOrlrsI05Q";
		String qu = "https://maps.googleapis.com/maps/api/geocode/json?address=";
		
		if (dirc.getOrigin() != null) {
			try {
				String quer = qu + dirc.getOrigin().replace(" ", "+") + key;
				URL url = new URL(quer);
				JsonReader json = Json.createReader(url.openStream());
				JsonObject obj = json.readObject().getJsonArray("results").getJsonObject(0).getJsonObject("geometry").getJsonObject("location");
				dirc.setOrigLat(obj.getJsonNumber("lat").doubleValue());
				dirc.setOrigLon(obj.getJsonNumber("lng").doubleValue());
			} catch (Exception ex) {
				
			}
		}
		
		if (dirc.getDestination() != null) {
			try {
				String quer = qu + dirc.getDestination().replace(" ", "+") + key;
				URL url = new URL(quer);
				JsonReader json = Json.createReader(url.openStream());
				JsonObject obj = json.readObject().getJsonArray("results").getJsonObject(0).getJsonObject("geometry").getJsonObject("location");
				dirc.setDestLat(obj.getJsonNumber("lat").doubleValue());
				dirc.setDestLon(obj.getJsonNumber("lng").doubleValue());
			} catch (Exception ex) {
				
			}
		}
			
		int id = persistQuery(query);
		dirc.setId(id);
		dirc.getQueries().setId(id);
		
		Broadcaster.broadcast(dirc);
		
		em.getTransaction().begin();
		em.persist(dirc);
		em.getTransaction().commit();
	}
	
	
	
	public static void persistNews (Queries query, String publisher, String message) {
		if (query.getType().equals("sms")) {
			if (message == null) {
				Communicate.sendText("Unable to parse your news query.", query.getPhone().getPhone());
			} else {
				Communicate.sendText(message, query.getPhone().getPhone());
			}
		} else {
			
		}
		
		News news = new News();
		news.setPublisher(publisher);
		news.setQueries(query);
		
		int id = persistQuery(query);
		news.setId(id);
		news.getQueries().setId(id);
		
		Broadcaster.broadcast(news);
		
		
		
		em.getTransaction().begin();
		em.persist(news);
		em.getTransaction().commit();
	}
	
	public static void persistWolfram (Queries query) {
		Communicate.sendText("Unable to process your query. Please try rephrasing.", query.getPhone().getPhone());
		persistQuery(query);
	}
	
	public static void persistWolfram (Queries query, String result) {
		if (query.getType().equals("sms")) {
			Communicate.sendText(result, query.getPhone().getPhone());
		}
		
		query.setId(persistQuery(query));
		
		Broadcaster.broadcast(query);
	}
	
	public static int persistQuery (Queries query) {
		String str = "SELECT x FROM Queries AS x";
		List <Queries> qu = em.createQuery(str).getResultList();
		
		System.out.println("Queries: " + qu.size());
		
		int id = 1;
		
		if (qu != null && qu.size() > 0) {
			id = qu.size() + 1;
		}		
		
		query.setId(id);
		
		System.out.println("Phone #:" + query.getPhone().getPhone());
		
		em.getTransaction().begin();
		em.persist(query);
		em.getTransaction().commit();
		
		return id;
	}
	

}
