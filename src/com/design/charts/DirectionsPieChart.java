package com.design.charts;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.design.persistence.Queries;
import com.design.persistence.Users;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DirectionsPieChart extends VerticalLayout{

	private Chart chart;
	private Configuration conf;
	private DataSeries series;
	
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();
	
	public DirectionsPieChart (String type) {
		ui(type);
	}
	
	public void ui(String type) {
		
		
		chart = new Chart(ChartType.PIE);

		conf = chart.getConfiguration();

		conf.setTitle("Queries by Format");

		Tooltip tooltip = conf.getTooltip();
		tooltip.setPointFormat("<b>{point.percentage:.1f}%</b>");

		PlotOptionsPie plotOptions = new PlotOptionsPie();
		plotOptions.setAllowPointSelect(true);
		plotOptions.setCursor(Cursor.POINTER);
		DataLabels dataLabels = plotOptions.getDataLabels();
		dataLabels.setEnabled(true);
		dataLabels.setFormat("{point.name}");
		conf.setPlotOptions(plotOptions);

		series = new DataSeries("Revenue");
		series.add(new DataSeriesItem("SMS", 50));
		series.add(new DataSeriesItem("Voice", 50));
		conf.setSeries(series);
		
		
		chart.setSizeFull();
		this.addComponent(chart);
		
		
		
		loadData(type);
		
	}
	
	
	public void loadData (String type) {
		
		String query;
		
		if (type.equals("usage")){
			query = "SELECT x from Queries AS x";
		} else{
			query = "SELECT x from Queries AS x where x.class1='" + type + "'";
		}
		
		List <Queries> queries = em.createQuery(query).getResultList();
		List <Queries> smsList = queries.stream().filter(p -> p.getType().equals("sms")).collect(Collectors.toList());

		
		int sms = smsList.size();
		
		int voice = queries.size() - sms;
		
		if (sms != series.get("SMS").getY().intValue()){
			series.get("SMS").setY(sms);
		}
		
		if (voice != series.get("Voice").getY().intValue()) {
			series.get("Voice").setY(voice);
			System.out.println("here");
		}
	}
	
	
	
}
