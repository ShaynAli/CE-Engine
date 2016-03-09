package com.design.charts;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.commons.lang3.text.WordUtils;

import com.design.persistence.Directions;
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
	
}
