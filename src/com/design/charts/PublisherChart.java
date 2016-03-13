package com.design.charts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.design.persistence.News;

public class PublisherChart {

	private Chart chart;
	private Configuration conf;
	private ListSeries voice;
	private ListSeries sms;
	private XAxis x;
	
	private Map <String, Integer> map;
	List <Number> data;
	
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();
	
	public PublisherChart () {
		ui();
	}
	
	private void ui () {
		chart = new Chart(ChartType.COLUMN);
		conf = chart.getConfiguration();
		
		conf.setTitle("Number of Queries by Publisher");
		
		YAxis y = new YAxis();
		y.setMin(0);
		y.setTitle("Number of Queries");
		conf.addyAxis(y);
	    conf.setLegend(buildLegend());
	    
	    x = new XAxis();
	    
	    map = new HashMap <String, Integer>();
	    data = new ArrayList<Number>();
	    
	    loadData();
	}
	
	private Legend buildLegend () {
		 Legend legend = new Legend();
	     legend.setLayout(LayoutDirection.VERTICAL);
	     legend.setBackgroundColor(new SolidColor("#FFFFFF"));
	     legend.setAlign(HorizontalAlign.LEFT);
	     legend.setVerticalAlign(VerticalAlign.TOP);
	     legend.setX(100);
	     legend.setY(70);
	     legend.setFloating(true);
	     legend.setShadow(true);
	     return legend;
	}
	
	public void loadData () {
		String qu = "SELECT x FROM News as X";
		List <News> news = em.createQuery(qu).getResultList();
		
		for (int i = 0; i< news.size(); i++) {
			if (news.get(i).getQueries().getType().equals("sms")) {
				if (!Arrays.asList(x.getCategories()).contains(news.get(i).getPublisher())) {
					map.put(news.get(i).getPublisher(), x.getCategories().length);
					x.addCategory(news.get(i).getPublisher());
					data.add(1);
				} else {
					data.set(map.get(news.get(i).getPublisher()), data.get(map.get(news.get(i).getPublisher())) + 1);
					data.get()
				}
				
				
				
			} else {
				
			}
		}
	}
	
}
