package com.example.designgui;

import javax.servlet.annotation.WebServlet;

import com.design.charts.DirectionsPieChart;
import com.design.dashboard.Dashboard;
import com.design.dashboard.DashboardDesign;
import com.design.persistence.Directions;
import com.design.persistence.News;
import com.design.persistence.Queries;
import com.design.persistence.Weather;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("designgui")
@Push 
public class DesignguiUI extends UI implements Broadcaster.BroadcastListener {

	@WebServlet(value = {"/app/*", "/VAADIN/*"}, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DesignguiUI.class, widgetset = "com.example.designgui.widgetset.DesignguiWidgetset")
	public static class Servlet extends VaadinServlet {
	}
	
	private DashboardDesign dashboard;

	@Override
	protected void init(VaadinRequest request) {
		Broadcaster.register(this);
		
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		dashboard = new DashboardDesign();
		setContent(dashboard);
	}
	
	@Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }

	@Override
	public void receiveBroadcast(String message) {
		access (new Runnable () {
			@Override
			public void run () {
				
			}
		});
		
	}

	@Override
	public void receiveBroadcast(Queries qu) {
		this.access(new Runnable () {
			@Override
			public void run () {
				dashboard.receiveQuery(qu);
				UI.getCurrent().push();
			}
		});
	}

	@Override
	public void receiveBroadcast(Directions dir) {
		this.access(new Runnable () {
			@Override
			public void run () {
				dashboard.receiveDirections(dir);
				UI.getCurrent().push();
			}
		});
	}

	@Override
	public void receiveBroadcast(News news) {
		this.access(new Runnable ()  {
			@Override
			public void run () {
				dashboard.receiveNews(news);
				UI.getCurrent().push();
			}
		});
		
		System.out.println("received news");
	}

	@Override
	public void receiveBroadcast(Weather weather) {
		this.access(new Runnable () {
			@Override
			public void run () {
				dashboard.receiveWeather(weather);
				UI.getCurrent().push();
			}
		});
	}

}