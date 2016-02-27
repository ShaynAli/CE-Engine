package com.design.dashboard;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;

public class DirectionsPieChart {

	public DirectionsPieChart () {
		ui();
	}
	
	public void ui() {
		Chart chart = new Chart(ChartType.PIE);

		Configuration conf = chart.getConfiguration();

		conf.setTitle("Revenue by industry");
		conf.setSubTitle("2015");

		Tooltip tooltip = conf.getTooltip();
		tooltip.setPointFormat("<b>{point.percentage:.1f}%</b>");

		PlotOptionsPie plotOptions = new PlotOptionsPie();
		plotOptions.setAllowPointSelect(true);
		plotOptions.setCursor(Cursor.POINTER);
		plotOptions.setShowInLegend(true);
		DataLabels dataLabels = plotOptions.getDataLabels();
		dataLabels.setEnabled(true);
		dataLabels.setFormat("{point.name}: {point.y:.1f} M€");
		conf.setPlotOptions(plotOptions);

		DataSeries series = new DataSeries("Revenue");
		series.add(new DataSeriesItem("Aerospace", 90.0));
		series.add(new DataSeriesItem("Medical", 53.6));
		series.add(new DataSeriesItem("Agriculture", 25.6));
		series.add(new DataSeriesItem("Automotive", 17.0));
		series.add(new DataSeriesItem("Consumers", 12.4));
		series.add(new DataSeriesItem("Subsidies", 1.4));
		conf.setSeries(series);
	}
	
}
