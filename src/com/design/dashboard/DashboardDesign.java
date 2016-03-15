package com.design.dashboard;

import com.design.persistence.Directions;
import com.design.persistence.Queries;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class DashboardDesign extends Dashboard {
	
	DashView usage;
	DashView directions;
	DashView news;
	DashView weather;
	DashView math;
	ConsoleView console;

	public DashboardDesign () {
		super();
		usage = new DashView("Usage", "usage", "Queries");
		directions = new DashView("Directions", "directions", "Direction Queries");
		news = new DashView ("News", "news", "News Queries");
		weather = new DashView ("Weather", "weather", "Weather Queries");
		math = new DashView ("Wolfram Alpha", "math", "Wolfram Alpha Queries");
		console = new ConsoleView();
		
		select.removeAllItems();
		
		select.addItem("Both");
		select.addItem("SMS");
		select.addItem("Voice");
		select.select("Both");
		select.setNullSelectionAllowed(false);
		
		clickListeners();
		
		clear.addStyleName(ValoTheme.BUTTON_DANGER);
		
		scroll_panel.setContent(usage);
		usageButton.setStyleName("menu-button selected");
	}
	
	public void clickListeners () {
		
		select.addValueChangeListener(new ValueChangeListener () {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (select.getValue().equals("Both")) {
					directions.switchToBoth();
					news.switchToBoth();
					math.switchToBoth();
					weather.switchToBoth();
					usage.switchToBoth();
				} else if (select.getValue().equals("SMS")) {
					directions.switchToSms();
					news.switchToSms();
					math.switchToSms();
					weather.switchToSms();
					usage.switchToSms();
				} else if (select.getValue().equals("Voice")) {
					directions.switchToVoice();
					news.switchToVoice();
					math.switchToVoice();
					weather.switchToVoice();
					usage.switchToVoice();
				}
			}
			
		});
		
		newsButton.setIcon(FontAwesome.NEWSPAPER_O);
		
		usageButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				usageButton.setStyleName("menu-button selected");
				mapsButton.setStyleName("menu-button");
				mathButton.setStyleName("menu-button");
				newsButton.setStyleName("menu-button");
				weatherButton.setStyleName("menu-button");
				queryButton.setStyleName("menu-button");
				databaseButton.setStyleName("menu-button");
				
				scroll_panel.setContent(usage);
		
			}
			
		});
		
		mapsButton.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				usageButton.setStyleName("menu-button");
				mapsButton.setStyleName("menu-button selected");
				mathButton.setStyleName("menu-button");
				newsButton.setStyleName("menu-button");
				weatherButton.setStyleName("menu-button");
				queryButton.setStyleName("menu-button");
				databaseButton.setStyleName("menu-button");
				
				scroll_panel.setContent(directions);
				
				
				
			}
			
		});
		
		mathButton.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				usageButton.setStyleName("menu-button");
				mapsButton.setStyleName("menu-button");
				mathButton.setStyleName("menu-button selected");
				newsButton.setStyleName("menu-button");
				weatherButton.setStyleName("menu-button");
				queryButton.setStyleName("menu-button");
				databaseButton.setStyleName("menu-button");
				
				scroll_panel.setContent(math);
				
			}
			
		});
		
		newsButton.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				usageButton.setStyleName("menu-button");
				mapsButton.setStyleName("menu-button");
				mathButton.setStyleName("menu-button");
				newsButton.setStyleName("menu-button selected");
				weatherButton.setStyleName("menu-button");
				queryButton.setStyleName("menu-button");
				databaseButton.setStyleName("menu-button");
				scroll_panel.setContent(news);
			}
			
		});
		
		weatherButton.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				usageButton.setStyleName("menu-button");
				mapsButton.setStyleName("menu-button");
				mathButton.setStyleName("menu-button");
				newsButton.setStyleName("menu-button");
				weatherButton.setStyleName("menu-button selected");
				queryButton.setStyleName("menu-button");
				databaseButton.setStyleName("menu-button");		
				
				scroll_panel.setContent(weather);
			}
			
		});
		
		queryButton.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				usageButton.setStyleName("menu-button");
				mapsButton.setStyleName("menu-button");
				mathButton.setStyleName("menu-button");
				newsButton.setStyleName("menu-button");
				weatherButton.setStyleName("menu-button");
				queryButton.setStyleName("menu-button selected");
				databaseButton.setStyleName("menu-button");
				
				scroll_panel.setContent(console);
				
			}
			
		});
		
		queryButton.setIcon(FontAwesome.TERMINAL);
		queryButton.setCaption("Console");
		
		databaseButton.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				usageButton.setStyleName("menu-button");
				mapsButton.setStyleName("menu-button");
				mathButton.setStyleName("menu-button");
				newsButton.setStyleName("menu-button");
				weatherButton.setStyleName("menu-button");
				queryButton.setStyleName("menu-button");
				databaseButton.setStyleName("menu-button selected");
				
			}
			
		});
		
	}
	
	public void receiveDirections (Directions dir) {
		directions.receiveDirections(dir);
		usage.receiveStandardQuery(dir.getQueries());
	}

	public void receiveQuery(Queries qu) {
		if (qu.getClass1().equals("math")) {
			math.receiveQuery(qu);
			usage.receiveStandardQuery(qu);
			
		}
	}
	
}
