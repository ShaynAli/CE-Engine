package com.design.charts;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.design.persistence.Queries;
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
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.VerticalLayout;

import javafx.scene.paint.Color;

public class ConfidenceChart extends VerticalLayout {
	
	private Chart chart;
	private Configuration conf;
	private DataSeries voice;
	private DataSeries sms;
	
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();
	
	public ConfidenceChart (String type) {
		ui(type);
	}
	
	private void ui(String type) {
		chart = new Chart(ChartType.SCATTER);
		conf = chart.getConfiguration();
		conf.setTitle("Query Confidence");
		sms = new DataSeries();
		voice = new DataSeries();
		
		YAxis yAxis = conf.getyAxis();
		yAxis.setTitle(new AxisTitle("Confidence (%)"));
		yAxis.getTitle().setAlign(VerticalAlign.MIDDLE);
		yAxis.setMax(100);
		
		
		XAxis x = conf.getxAxis();
		x.setTitle(new AxisTitle("Query #"));
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
		
		
		loadData(type);
	}
	
	public void loadData (String type) {
		String query = "SELECT x FROM Queries AS x where x.class1='" + type + "'";
		Query qu = em.createQuery(query);
		List <Queries> results = qu.getResultList();
		results = results.stream().filter(p -> p.getClass1().equals(type)).collect(Collectors.toList());
		
		List <Queries> smsList = results.stream().filter(p -> p.getType().equals("sms")).collect(Collectors.toList());
		List <Queries> voiceList = results.stream().filter(p -> p.getType().equals("voice")).collect(Collectors.toList());
		
		for (int i = 0; i < smsList.size(); i++) {
			double conf = 0;
			
			if (smsList.get(i).getConfidence() != null) {
				conf = smsList.get(i).getConfidence();
			}
			
			sms.add(new DataSeriesItem(i + 1, conf*100));
		}
		
		for (int i = 0; i < voiceList.size(); i++) {
			double conf = 0;
			
			if (smsList.get(i).getConfidence() != null) {
				conf = smsList.get(i).getConfidence();
			}
			
			voice.add(new DataSeriesItem(i + 1, conf*100));
		}
	}
	
	public void switchToBoth() {
		sms.setVisible(true);
		voice.setVisible(true);
	}
	
	public void switchToSMS () {
		sms.setVisible(true);
		voice.setVisible(false);
	}
	
	public void switchToVoice () {
		sms.setVisible(false);
		voice.setVisible(true);
	}

	public void receiveStandardQuery(Queries qu) {
		try {
			if (qu.getType().equals("sms")) {
				sms.add(new DataSeriesItem(sms.size() + 1, qu.getConfidence()*100));
			} else {
				voice.add(new DataSeriesItem(voice.size() + 1, qu.getConfidence()*100));
			}
		} catch (Exception ex) {
			
		}
		
	}
	
}
