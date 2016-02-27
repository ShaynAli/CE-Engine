package com.design.dashboard;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class DashboardDesign extends Dashboard {

	public DashboardDesign () {
		super();
		clickListeners();
	}
	
	public void clickListeners () {
		
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
				
			}
			
		});
		
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
	
}
