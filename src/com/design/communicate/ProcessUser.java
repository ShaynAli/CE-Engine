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

import com.design.data.Maps;
import com.design.data.Wolfram;
import com.design.persistence.Directions;
import com.design.persistence.News;
import com.design.persistence.Queries;
import com.design.persistence.Users;
import com.design.persistence.Weather;
import com.example.designgui.Broadcaster;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

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
				Broadcaster.broadcast(output, query);
				Communicate.sendText(output, query.getPhone().getPhone());
				Broadcaster.broadcast("twilio", query);
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
			Broadcaster.broadcast(directions, query);
			Communicate.sendText(directions, query.getPhone().getPhone());
			Broadcaster.broadcast("twilio", query);
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
				Broadcaster.broadcast(message, query);
				Communicate.sendText(message, query.getPhone().getPhone());
				Broadcaster.broadcast("twilio", query);
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
			Broadcaster.broadcast(result, query);
			Communicate.sendText(result, query.getPhone().getPhone());
			Broadcaster.broadcast("twilio", query);
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
	
	
	public String processVoiceQuery (String body, String from) {

		    	NaturalLanguageClassifier service = new NaturalLanguageClassifier();
		    	service.setUsernameAndPassword("6e7a6f54-5d89-4454-8f59-1ba52696f989", "TvAzv6xg9Up8");

		    	Classification classification = service.classify("c7fa4ax22-nlc-10514", body);
		    	List <ClassifiedClass> confidence = classification.getClasses(); // List of classes
		    	
		    	double largest = classification.getTopConfidence(); // Get largest
		    	boolean success = true;
		    	int secondLargestI; // Used to keep track of index of 2nd highest confidence if difference is < 30% between highest & 2nd highest

		    	// Assigns initial value to 2nd largest
		    	if (classification.getTopClass().equals(confidence.get(0).getName())) {
		    		secondLargestI = 1;
		    	} else {
		    		secondLargestI = 0;
		    	}
		    	
		    	// Loop through confidence classes
		    	for (int i = 0; i < confidence.size(); i++) {
		    		
		    		if (!confidence.get(i).getName().equals(classification.getTopClass())) {
		    			if (largest - 0.30 < confidence.get(i).getConfidence()) {
		        			success = false;
		        		}
		        		
		        		if (confidence.get(i).getConfidence() > confidence.get(secondLargestI).getConfidence()) {
		        			secondLargestI = i;
		        		}
		    		}
		    		
		    	}
		  
		    	Queries query = new Queries();
				query.setConfidence(classification.getTopConfidence());
				query.setDirections(null);
				query.setNews(null);
				query.setPhone(userExists(from));

				query.setQuery(body);
				query.setTime(new Date());
				query.setType("voice");
				
				String result;
		    	
		    	if (success) { // If successful map to correct data source
		    		System.out.println(classification.getTopClass());
		    		if (classification.getTopClass().equals("directions")) {
		    			query.setClass1("directions");
		    			Broadcaster.broadcast("class", query);
		    			result = Maps.googleMapss(query);
		    		} else if (classification.getTopClass().equals("math")) {
		    			query.setClass1("math");
		    			Broadcaster.broadcast("class", query);
		    			//result = Wolfram.wolframAlpha(query);
		    		} else if (classification.getTopClass().equals("weather")) {
		    			query.setClass1("weather");
		    			Broadcaster.broadcast("class", query);
		    			//result = com.design.data.Weather.weather(query);
		    		} else if (classification.getTopClass().equals("news")) {
		    			query.setClass1("news");
		    			Broadcaster.broadcast("class", query);
		    			//result = com.design.data.News.getNews(query);
		    		}
		    		else
		    		{
		    			query.setClass1("math");
		    			Broadcaster.broadcast("class", query);
		    			Wolfram.wolframAlpha(query);
		    			
		    		}
		    	} else { // Otherwise unsuccessful
		    		query.setClass1("");
		    		query.setSuccessful(false);
		    		
		    	}
		    return null;

	}
	

}
