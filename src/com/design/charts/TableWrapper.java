package com.design.charts;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.commons.lang3.text.WordUtils;

import com.design.persistence.Directions;
import com.design.persistence.News;
import com.design.persistence.Queries;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TableWrapper extends VerticalLayout {
	
	private String type;
	private Label label;
	private Table table;
	
	static EntityManager em = Persistence.createEntityManagerFactory("DesignProject").createEntityManager();

	public TableWrapper (String title, String type) {
		this.type = type;
		
		if (type.equals("directions")) {
			table = buildDirectionsTable();
		} else if (type.equals("news")) {
			table = buildNewsTable();
		} else if (type.equals("weather")) {
			table = buildWeatherTable();
		} else if (type.equals("math")) {
			table = buildWolframTable();
		} else if (type.equals("usage")) {
			table = buildUsageTable();
		} else {
			table = new Table();
		}
		
		label = new Label(title);
		label.addStyleName("table-title");
		label.setSizeUndefined();
		
		this.addComponent(label);
		this.addComponent(table);
		
		this.setComponentAlignment(label, Alignment.BOTTOM_CENTER);
		
		this.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
		
		this.setExpandRatio(label, (float) 0.07);
		this.setExpandRatio(table, (float) 0.93); 
		
		this.setSizeFull();
	}
	
	public Table buildUsageTable (){
		Table table = new Table ();
		table.addContainerProperty("#", Integer.class, 1);
		table.addContainerProperty("query", String.class, null);
		table.addContainerProperty("class", String.class, null);
		table.addContainerProperty("success", CheckBox.class, new CheckBox("", true));
		table.addContainerProperty("type", String.class, null);
		
		String qu = "SELECT x FROM Queries AS x";
		List <Queries> queries = em.createQuery(qu).getResultList();
		
		for (int i =0; i <queries.size(); i++){
			CheckBox box = new CheckBox();
			box.setValue(queries.get(i).getSuccessful());
			box.setEnabled(false);
			
			table.addItem(new Object [] {i + 1, queries.get(i).getQuery(), queries.get(i).getClass1(),
					box, queries.get(i).getType()}, queries.get(i).getId());
		}
		
		table.setImmediate(true);
		table.setVisibleColumns("#", "query", "success", "class");
		table.setColumnAlignments(Align.CENTER, Align.CENTER, Align.CENTER, Align.CENTER);
		
		table.setColumnHeaders("#", "Query", "Successful", "Class");
		table.sort(new Object [] {"#"}, new boolean [] {false});
		
        table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
        table.addStyleName(ValoTheme.TABLE_SMALL);  
        table.setHeight("95%");
        table.setWidth("97%");
        
        return table;

	}
	
	public Table buildDirectionsTable () {
		Table table = new Table();
		table.addContainerProperty("#", Integer.class, 1);
		table.addContainerProperty("query", String.class, null);
		table.addContainerProperty("success", CheckBox.class, new CheckBox("", true));
		table.addContainerProperty("from", String.class, null);
		table.addContainerProperty("to", String.class, null);
		table.addContainerProperty("type", String.class, null);
		
		String qu = "SELECT x FROM Directions AS x";
		List <Directions> queries = em.createQuery(qu).getResultList();
		
		for (int i = 0; i < queries.size(); i++) {
			CheckBox box = new CheckBox();
			box.setValue(queries.get(i).getQueries().getSuccessful());
			box.setEnabled(false);
			table.addItem(new Object [] {i + 1, queries.get(i).getQueries().getQuery(), box,
					WordUtils.capitalize(queries.get(i).getOrigin()), WordUtils.capitalize(queries.get(i).getDestination()),
					queries.get(i).getQueries().getType()},
					queries.get(i).getId());
			
		}
		
		
		table.setImmediate(true);
		table.setVisibleColumns("#", "query", "success", "from", "to");
		table.setColumnAlignments(Align.CENTER, Align.CENTER, Align.CENTER, Align.CENTER, Align.CENTER);
		
		table.setColumnHeaders("#", "Query", "Successful", "From", "To");
		table.sort(new Object [] {"#"}, new boolean [] {false});
		
        table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
        table.addStyleName(ValoTheme.TABLE_SMALL);  
        table.setHeight("95%");
        table.setWidth("97%");
  
        //table.addStyleName("table-padding");
        
		return table;
	}
	
	public Table buildWeatherTable () {
		Table table = new Table();
		table.addContainerProperty("#", Integer.class, 0);
		table.addContainerProperty("query", String.class, null);
		table.addContainerProperty("success", CheckBox.class, null);
		table.addContainerProperty("type", String.class, null);
		
		table.setColumnAlignments(Align.CENTER, Align.CENTER, Align.CENTER, Align.CENTER);
		table.setColumnHeaders("#", "Query", "Successful", "Type");
		
		table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		table.addStyleName(ValoTheme.TABLE_SMALL);
		table.setHeight("95%");
		table.setWidth("97%");
		table.setImmediate(true);
		
		String qu = "SELECT x FROM Queries AS x where x.class1='weather'";
		List <Queries> weather = em.createQuery(qu).getResultList();
		
		for (int i = 0; i < weather.size(); i++) {
			CheckBox box = new CheckBox();
			box.setValue(weather.get(i).getSuccessful());
			box.setEnabled(false);
			table.addItem(new Object [] {i + 1,  weather.get(i).getQuery(), box, weather.get(i).getType()}, weather.get(i).getId());
		}
		
		table.setVisibleColumns("#", "query", "success");
		table.sort(new Object [] {"type"}, new boolean [] {false});
		
		return table;
	}
	
	public Table buildWolframTable () {
		Table table = new Table();
		table.addContainerProperty("#", Integer.class, 0);
		table.addContainerProperty("query", String.class, null);
		table.addContainerProperty("success", CheckBox.class, null);
		table.addContainerProperty("type", String.class, null);
		
		table.setColumnAlignments(Align.CENTER, Align.CENTER, Align.CENTER, Align.CENTER);
		table.setColumnHeaders("#", "Query", "Successful", "Type");
		
		table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		table.addStyleName(ValoTheme.TABLE_SMALL);
		table.setHeight("95%");
		table.setWidth("97%");
		table.setImmediate(true);
		
		String qu = "SELECT x FROM Queries AS x where x.class1='math'";
		List <Queries> wolf = em.createQuery(qu).getResultList();
		
		for (int i = 0; i < wolf.size(); i++) {
			CheckBox box = new CheckBox();
			box.setValue(wolf.get(i).getSuccessful());
			box.setEnabled(false);
			
			table.addItem(new Object [] {i + 1,  wolf.get(i).getQuery(), box, wolf.get(i).getType()}, wolf.get(i).getId());
		}
		
		table.setVisibleColumns("#", "query", "success");
		table.sort(new Object [] {"#"}, new boolean [] {false});
		
		return table;
	}
	
	public Table buildNewsTable () {
		Table table = new Table();
		table.addContainerProperty("#", Integer.class, 0);
		table.addContainerProperty("query", String.class, null);
		table.addContainerProperty("success", CheckBox.class, new CheckBox("", true));
		table.addContainerProperty("publisher", String.class, null);
		table.addContainerProperty("type", String.class, null);
		
		table.setColumnAlignments(Align.CENTER, Align.CENTER, Align.CENTER, Align.CENTER, Align.CENTER);
		table.setColumnHeaders("#", "Query", "Successful", "Publisher", "Sort");
		
		table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		table.addStyleName(ValoTheme.TABLE_SMALL);
		table.setHeight("95%");
		table.setWidth("97%");
		table.setImmediate(true);
		
		
		
		String qu = "SELECT x FROM News AS x";
		List <News> news = em.createQuery(qu).getResultList();
		
		for (int i = 0; i < news.size(); i++) {
			CheckBox box = new CheckBox();
			box.setValue(news.get(i).getQueries().getSuccessful());
			box.setEnabled(false);
			table.addItem(new Object []{i + 1, news.get(i).getQueries().getQuery(), box, news.get(i).getPublisher(), news.get(i).getQueries().getType()}, news.get(i).getId());
			
			System.out.println(news.get(i).getQueries().getType());
			
			//System.out.println(i + ": " + news.get(i).getQueries().getQuery() + ", " + news.get(i).getQueries().getSuccessful() + ", "
					//+ news.get(i).getPublisher());
		}
		table.setVisibleColumns("#", "query", "success", "publisher");
		table.sort(new Object []{"#"}, new boolean [] {false});
		
		return table;
		
	}
	
	public Filterable switchToBoth () {
		Filterable filter = (Filterable) table.getContainerDataSource();
		filter.removeAllContainerFilters();
		return filter;
	}
	
	public void switchToSms () {
		switchToBoth().addContainerFilter(new Compare.Equal("type", "sms"));
	}
	
	public void switchToVoice () {
		switchToBoth().addContainerFilter(new Compare.Equal("type", "voice"));
	}
	
	public void receiveLocation (Directions queries) {
		System.out.println("hemmmklklklere");
		CheckBox box = new CheckBox();
		box.setValue(queries.getQueries().getSuccessful());
		box.setEnabled(false);
		table.addItem(new Object [] {table.size() + 1, queries.getQueries().getQuery(), box,
				WordUtils.capitalize(queries.getOrigin()), WordUtils.capitalize(queries.getDestination())},
				queries.getId());
		table.sort(new Object [] {"#"}, new boolean [] {false});
		
		
	}
	
	public void recieveStandardQuery (Queries qu) {
		CheckBox box = new CheckBox();
		box.setValue(qu.getSuccessful());
		box.setEnabled(false);
	
		table.addItem(new Object [] {table.size() + 1, qu.getQuery(), box, qu.getClass1()}, qu.getId());
		table.sort(new Object [] {"#"}, new boolean [] {false});
	}
	
	public void receiveWolfram (Queries qu) {
		CheckBox box = new CheckBox();
		box.setValue(qu.getSuccessful());
		box.setEnabled(false);
		table.addItem(new Object [] {table.size() + 1, qu.getQuery(), box},
				qu.getId());
		table.sort(new Object [] {"#"}, new boolean [] {false});
	}
	
}
