package com.example.designgui;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.design.persistence.Directions;
import com.design.persistence.News;
import com.design.persistence.Queries;

public class Broadcaster implements Serializable {

		static ExecutorService executorService = Executors.newSingleThreadExecutor();

	    public interface BroadcastListener {
	        void receiveBroadcast(String message);
	        void receiveBroadcast(Queries qu);
	        void receiveBroadcast(Directions dir);
	        void receiveBroadcast(News news);
	    }

	    private static LinkedList<BroadcastListener> listeners = new LinkedList<BroadcastListener>();

	    public static synchronized void register(BroadcastListener listener) {
	    	listeners.add(listener);
	    }

	    public static synchronized void unregister(BroadcastListener listener) {
	        listeners.remove(listener);
	    }

	    public static synchronized void broadcast(final String message) {
	        for (final BroadcastListener listener: listeners)
	            executorService.execute(new Runnable() {
	                @Override
	                public void run() {
	                    listener.receiveBroadcast(message);
	                }
	            });
	    }
	    
	    public static synchronized void broadcast(final Queries qu) {
	        for (final BroadcastListener listener: listeners)
	            executorService.execute(new Runnable() {
	                @Override
	                public void run() {
	                    listener.receiveBroadcast(qu);
	                }
	            });
	    }
	    
	    public static synchronized void broadcast(final Directions dir) {
	        for (final BroadcastListener listener: listeners)
	            executorService.execute(new Runnable() {
	                @Override
	                public void run() {
	                    listener.receiveBroadcast(dir);
	                }
	            });
	    }
	    
	    public static synchronized void broadcast(final News news) {
	        for (final BroadcastListener listener: listeners)
	            executorService.execute(new Runnable() {
	                @Override
	                public void run() {
	                    listener.receiveBroadcast(news);
	                }
	            });
	    }
	
}
