package com.design.dashboard;

import java.lang.reflect.Method;
import java.util.Iterator;

import com.design.charts.ConfidenceChart;
import com.design.charts.DirectionMaps;
import com.design.charts.DirectionsPieChart;
import com.design.charts.DistanceChart;
import com.design.charts.QueriesTimeChart;
import com.design.charts.QueryClassPieChart;
import com.design.charts.TableWrapper;
import com.design.charts.WeatherMaps;
import com.design.persistence.Directions;
import com.design.persistence.News;
import com.design.persistence.Queries;
import com.design.persistence.Weather;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class DashView extends Panel {
	
	public static final String EDIT_ID = "dashboard-edit";
    public static final String TITLE_ID = "dashboard-title";
	
	private CssLayout dashboardPanels;
	private final VerticalLayout root;
	private Label titleLabel;
	
	private DistanceChart distchart = null;
	private ConfidenceChart confchart = null;
	private QueryClassPieChart piechart = null;
	private QueriesTimeChart timechart = null;
	private TableWrapper wrap = null;
	private DirectionMaps dirmap;
	private WeatherMaps wemap;
	
	private String title;
	private String type;
	private String queryTableTitle;
	
	public DashView (String title, String type, String quTitle) {
		this.title = title;
		this.type = type;
		this.queryTableTitle = quTitle;
		
		root = new VerticalLayout();
		ui();
	}
	
	public void ui () {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();

        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);
        
        root.addComponent(buildHeader());
        
        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);
	}
	
	private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        titleLabel = new Label(title);
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);


        return header;
    }
	
	 private Component buildContent() {
	        dashboardPanels = new CssLayout();
	        dashboardPanels.addStyleName("dashboard-panels");
	        Responsive.makeResponsive(dashboardPanels);
	        
	        if (type.equals("directions")) {
	        	dashboardPanels.addComponent(buildDirectionMap(0));
	        	dashboardPanels.addComponent(buildQueriesTimeChart(1));
	        	dashboardPanels.addComponent(buildDistanceChart(2));
	        	dashboardPanels.addComponent(buildQueryTable(3));	        	
	        } else if (type.equals("news")) {
	        	//dashboardPanels.addComponent(c); publisher  ---> LAST CHART TO FINISH
	        	dashboardPanels.addComponent(buildPieChart(0));
	        	dashboardPanels.addComponent(buildConfidenceChart(1)); 
	        	dashboardPanels.addComponent(buildQueriesTimeChart(2));
	        	dashboardPanels.addComponent(buildQueryTable(3));
	        } else if (type.equals("math")) {
	        	dashboardPanels.addComponent(buildPieChart(0));
	        	dashboardPanels.addComponent(buildConfidenceChart(1));
	        	dashboardPanels.addComponent(buildQueriesTimeChart(2));
	        	dashboardPanels.addComponent(buildQueryTable(3));
	        } else if (type.equals("weather")) {
	        	dashboardPanels.addComponent(buildWeatherMap(0));
	        	dashboardPanels.addComponent(buildConfidenceChart(1));
	        	//dashboardPanels.addComponent(build); temp ...time permitting
	        	dashboardPanels.addComponent(buildQueriesTimeChart(2));
	        	dashboardPanels.addComponent(buildQueryTable(3));
	        } else if (type.equals("usage")){
	        	dashboardPanels.addComponent(buildPieChart(0));
	        	dashboardPanels.addComponent(buildQueryClassPieChart(1));
	        	dashboardPanels.addComponent(buildQueriesTimeChart(2));
	        	dashboardPanels.addComponent(buildQueryTable(3));
	        }

	        return dashboardPanels;
	 }
	 
	 private Component buildDirectionMap (int i) {
		 dirmap = new DirectionMaps();
		 dirmap.setSizeFull();
		 return createContentWrapper(dirmap);
	 }
	 
	 private Component buildWeatherMap (int i) {
		 wemap = new WeatherMaps();
		 wemap.setSizeFull();
		 return createContentWrapper(wemap);
	 }
	 
	 private Component buildDistanceChart (int i) {
		 distchart = new DistanceChart();
		 distchart.setSizeFull();
		 return createContentWrapper(distchart);
	 }
	 private Component buildQueryClassPieChart (int i) {
		 piechart = new QueryClassPieChart();
		 piechart.setSizeFull();
		 return createContentWrapper(piechart);
	 }
	 private Component buildPieChart(int i) {
	        DirectionsPieChart chart = new DirectionsPieChart(type);
	        chart.setSizeFull();
	        return createContentWrapper(chart);
	 }
	 
	 private Component buildConfidenceChart (int i) {
		 	confchart = new ConfidenceChart(type);
		 	confchart.setSizeFull();
		 	return createContentWrapper(confchart);
	 }
	 
	 private Component buildQueriesTimeChart (int i) {
		 timechart = new QueriesTimeChart(type);
		 timechart.setSizeFull();
		 return createContentWrapper(timechart);
	 }
	 
	 private Component buildQueryTable (int i) {	
		 	wrap = new TableWrapper(queryTableTitle, type);
			Component wrapper =  createContentWrapper(wrap);
			wrapper.addStyleName("top10-revenue");
			return wrapper;
	 }
	 
	 
	 private Component createContentWrapper(final Component content) {
	        final CssLayout slot = new CssLayout();
	        slot.setWidth("100%");
	        slot.addStyleName("dashboard-panel-slot");

	        CssLayout card = new CssLayout();
	        card.setWidth("100%");
	        card.addStyleName(ValoTheme.LAYOUT_CARD);

	        HorizontalLayout toolbar = new HorizontalLayout();
	        toolbar.addStyleName("dashboard-panel-toolbar");
	        toolbar.setWidth("100%");

	        Label caption = new Label(content.getCaption());
	        caption.addStyleName(ValoTheme.LABEL_H4);
	        caption.addStyleName(ValoTheme.LABEL_COLORED);
	        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	        content.setCaption(null);

	        MenuBar tools = new MenuBar();
	        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
	        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

	            @Override
	            public void menuSelected(final MenuItem selectedItem) {
	                if (!slot.getStyleName().contains("max")) {
	                    selectedItem.setIcon(FontAwesome.COMPRESS);
	                    toggleMaximized(slot, true);
	                } else {
	                    slot.removeStyleName("max");
	                    selectedItem.setIcon(FontAwesome.EXPAND);
	                    toggleMaximized(slot, false);
	                }
	            }
	        });
	        max.setStyleName("icon-only");
	        
	        /*MenuItem root = tools.addItem("", FontAwesome.COG, null);
	        root.addItem("Configure", new Command() {
	            @Override
	            public void menuSelected(final MenuItem selectedItem) {
	                Notification.show("Not implemented in this demo");
	            }
	        });
	        root.addSeparator();
	        root.addItem("Close", new Command() {
	            @Override
	            public void menuSelected(final MenuItem selectedItem) {
	                Notification.show("Not implemented in this demo");
	            }
	        });*/

	        toolbar.addComponents(caption, tools);
	        toolbar.setExpandRatio(caption, 1);
	        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

	        card.addComponents(toolbar, content);
	        slot.addComponent(card);
	        return slot;
	    }
	 
	 private void toggleMaximized(final Component panel, final boolean maximized) {
	        for (Iterator<Component> it = root.iterator(); it.hasNext();) {
	            it.next().setVisible(!maximized);
	        }
	        dashboardPanels.setVisible(true);

	        for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
	            Component c = it.next();
	            c.setVisible(!maximized);
	        }

	        if (maximized) {
	            panel.setVisible(true);
	            panel.addStyleName("max");
	        } else {
	            panel.removeStyleName("max");
	        }
	    }
	 
	 public void switchToBoth () {
		if (distchart != null) {
			distchart.switchToBoth();
		}
		 
		if (confchart != null) {
			confchart.switchToBoth();
		}
		
		if (timechart != null) {
			timechart.switchToBoth();
		}
		
		if (wrap != null)  {
			wrap.switchToBoth();
		}
	 }
	 
	 public void switchToSms () {
		 if (distchart != null) {
				distchart.switchToSms();
			}
			 
			if (confchart != null) {
				confchart.switchToSMS();
			}
			
			if (timechart != null) {
				timechart.switchToSms();
			}
			
			if (wrap != null)  {
				wrap.switchToSms();
			}
	 }
	 
	 public void switchToVoice () {
		 if (distchart != null) {
				distchart.switchToVoice();
			}
			 
			if (confchart != null) {
				confchart.switchToVoice();
			}
			
			if (timechart != null) {
				timechart.switchToVoice();
			}
			
			if (wrap != null)  {
				wrap.switchToVoice();
			}
	 }
	 
	 public void receiveDirections (Directions dir) {
		 dirmap.receiveLocation(dir);
		 timechart.receiveLocation(dir);
		 distchart.receiveLocation(dir);
		 wrap.receiveLocation(dir);
	 }
	 
	 public void receiveStandardQuery (Queries qu) {
		 timechart.receiveStandardQuery(qu);
		 wrap.recieveStandardQuery(qu);
	 }

	public void receiveQuery(Queries qu) {
		if (qu.getClass1().equals("math")) {
			timechart.receiveStandardQuery(qu);
			confchart.receiveStandardQuery(qu);
			wrap.receiveWolfram(qu);
		}
		
	}

	public void receiveNews(News news) {
		confchart.receiveStandardQuery(news.getQueries());
		timechart.receiveStandardQuery(news.getQueries());
		wrap.receiveNews(news);
		
	}

	public void receiveWeather(Weather weather) {
		confchart.receiveStandardQuery(weather.getQueries());
		timechart.receiveStandardQuery(weather.getQueries());
		wrap.receiveWeather(weather);
		wemap.receiveWeather(weather);
		
	}

	
	
	
	
}
