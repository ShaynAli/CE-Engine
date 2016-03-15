package com.design.charts;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;
import com.design.persistence.Directions;
import com.design.persistence.Queries;

public class QueriesTimeChart extends VerticalLayout {

	private Chart chart;
	private Configuration conf;
	private DataSeries sms;
	private DataSeries voice;
	
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();
	
	public QueriesTimeChart (String type) {
		ui(type);
	}
	
	private void ui (String type) {
		chart = new Chart(ChartType.LINE);
		conf = chart.getConfiguration();
		conf.setTitle("Queries Over Time");
		sms = new DataSeries();
		voice = new DataSeries();
		
		YAxis y = conf.getyAxis();
		y.setTitle("Number of Queries");
		y.getTitle().setAlign(VerticalAlign.MIDDLE);
		
		XAxis x = conf.getxAxis();
		x.setTitle("Time");
		x.getTitle().setAlign(VerticalAlign.MIDDLE);
		x.setType(AxisType.DATETIME);
		
		PlotOptionsLine plot = new PlotOptionsLine();
		conf.setPlotOptions(plot);
		conf.addSeries(voice);
		conf.addSeries(sms);
		
		conf.getLegend().setEnabled(false);
		
		sms.setName("SMS");
		voice.setName("Voice");
		
		chart.setSizeFull();
		this.addComponent(chart);
		
		loadData(type);

	}
	
	public void loadData(String type) {
		String query=" ";
		if (type.equals("usage")){
			query = "SELECT x FROM Queries AS x";
		}
		else {
			query = "SELECT x FROM Queries AS x where x.class1='" + type + "'";
		}
		
		List <Queries> qu = em.createQuery(query).getResultList();
		List <Queries> smsList = qu.stream().filter(p -> p.getType().equals("sms")).collect(Collectors.toList());
		List <Queries> voiceList = qu.stream().filter(p -> p.getType().equals("voice")).collect(Collectors.toList());
		
		for (int i = 0; i < smsList.size(); i++) {
			sms.add(new DataSeriesItem(smsList.get(i).getTime(), i + 1));
		}
		
		for (int i = 0; i < voiceList.size(); i++) {
			voice.add(new DataSeriesItem(voiceList.get(i).getTime(), i + 1));
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
				sms.add(new DataSeriesItem(dir.getQueries().getTime(), sms.size()+1));
			} else {
				voice.add(new DataSeriesItem(dir.getQueries().getTime(), voice.size() + 1));
			}
		} catch (Exception ex) {
			
		}
	}
	
	public void receiveStandardQuery (Queries qu) {
		try {
			if (qu.getType().equals("sms")) {
				sms.add(new DataSeriesItem(qu.getTime(), sms.size() + 1));
			} else {
				voice.add(new DataSeriesItem(qu.getTime(), voice.size() + 1));
			}
		} catch (Exception ex) {
			
		}
	}

	public void clear() {
		this.removeComponent(chart);
		chart = new Chart(ChartType.LINE);
		conf = chart.getConfiguration();
		conf.setTitle("Queries Over Time");
		sms = new DataSeries();
		voice = new DataSeries();
		
		YAxis y = conf.getyAxis();
		y.setTitle("Number of Queries");
		y.getTitle().setAlign(VerticalAlign.MIDDLE);
		
		XAxis x = conf.getxAxis();
		x.setTitle("Time");
		x.getTitle().setAlign(VerticalAlign.MIDDLE);
		x.setType(AxisType.DATETIME);
		
		PlotOptionsLine plot = new PlotOptionsLine();
		conf.setPlotOptions(plot);
		conf.addSeries(voice);
		conf.addSeries(sms);
		
		conf.getLegend().setEnabled(false);
		
		sms.setName("SMS");
		voice.setName("Voice");
		
		chart.setSizeFull();
		this.addComponent(chart);
		
	}
}
