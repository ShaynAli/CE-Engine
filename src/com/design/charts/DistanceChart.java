package com.design.charts;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.design.persistence.Directions;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisTitle;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsScatter;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.ui.VerticalLayout;

public class DistanceChart extends VerticalLayout {

	private Chart chart;
	private Configuration conf;
	private DataSeries sms;
	private DataSeries voice;
	
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();
	
	public DistanceChart () {
		ui();
	}
	
	private void ui () {
		sms = new DataSeries();
		voice = new DataSeries();
		
		chart = new Chart(ChartType.SCATTER);
		conf = chart.getConfiguration();
		conf.setTitle("Distance Travelled");
		
		YAxis y = conf.getyAxis();
		y.setTitle("Distance Travelled (km)");
		y.getTitle().setAlign(VerticalAlign.MIDDLE);
		
		XAxis x = conf.getxAxis();
		x.setTitle("Query #");
		x.getTitle().setAlign(VerticalAlign.MIDDLE);
		
		PlotOptionsScatter options = new PlotOptionsScatter();
		conf.setPlotOptions(options);
		conf.addSeries(sms);
		conf.addSeries(voice);
		
		conf.getLegend().setEnabled(false);
		

		sms.setName("SMS");
		voice.setName("Voice");
		
		chart.setSizeFull();
		this.addComponent(chart);
		
		loadData();
	}
	
	public void loadData () {
		String query = "SELECT x FROM Directions AS x";
		List <Directions> dir = em.createQuery(query).getResultList();
		
		List <Directions> smsList = dir.stream().filter(p -> p.getQueries().getType().equals("sms")).collect(Collectors.toList());
		List <Directions> voiceList = dir.stream().filter(p -> p.getQueries().getType().equals("voice")).collect(Collectors.toList());
		
		for (int i = 0; i < smsList.size(); i++) {
			sms.add(new DataSeriesItem(i + 1, smsList.get(i).getDistance()));
			System.out.println(i + 1 + " " +smsList.get(i).getDistance() );
		}
		
		for (int i = 0; i < voiceList.size(); i++) {
			voice.add(new DataSeriesItem(i + 1, voiceList.get(i).getDistance()));
		}
	}
	
	public void switchToBoth () {
		sms.setVisible(true);
		voice.setVisible(true);
	}
	
	public void switchToSms () {
		sms.setVisible(true);
		voice.setVisible(false);
	}
	
	public void switchToVoice () {
		sms.setVisible(false);
		voice.setVisible(true);
	}
	
	public void receiveLocation (Directions dir) {
		try {
			if (dir.getQueries().getType().equals("sms")) {
				sms.add(new DataSeriesItem(sms.size() + 1, dir.getDistance()));
			} else {
				voice.add(new DataSeriesItem(voice.size() + 1, dir.getDistance()));
			}
		} catch (Exception ex) {
			
		}
	}
	
}
