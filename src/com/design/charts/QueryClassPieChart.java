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

public class QueryClassPieChart extends VerticalLayout {

	Chart chart;
	Configuration conf;
	DataSeries series;
	
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();
	
	public QueryClassPieChart () {
		ui();
	}
	public void ui() {
		
		
		chart = new Chart(ChartType.PIE);

		conf = chart.getConfiguration();

		conf.setTitle("Queries by Class");

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
		series.add(new DataSeriesItem("Math", 25));
		series.add(new DataSeriesItem("Weather", 25));
		series.add(new DataSeriesItem("News", 25));
		series.add(new DataSeriesItem("Directions", 25));
		conf.setSeries(series);
		
		
		chart.setSizeFull();
		this.addComponent(chart);
		
		
		
		loadData();
		
	}
	public void loadData () {
		
		String query = "SELECT x from Queries AS x";
		
		List <Queries> queries = em.createQuery(query).getResultList();
		int math = queries.stream().filter(p -> p.getClass1().equals("math")).collect(Collectors.toList()).size();
		int weather = queries.stream().filter(p -> p.getClass1().equals("weather")).collect(Collectors.toList()).size();
		int directions = queries.stream().filter(p -> p.getClass1().equals("directions")).collect(Collectors.toList()).size();
		int news = queries.stream().filter(p -> p.getClass1().equals("news")).collect(Collectors.toList()).size();

		
		series.get("Math").setY(math);
		series.get("Weather").setY(weather);
		series.get("News").setY(news);
		series.get("Directions").setY(directions);
		
	}
}
