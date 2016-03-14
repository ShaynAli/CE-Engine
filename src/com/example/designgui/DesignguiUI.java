package com.example.designgui;

import javax.servlet.annotation.WebServlet;

import com.design.charts.DirectionsPieChart;
import com.design.dashboard.Dashboard;
import com.design.dashboard.DashboardDesign;
import com.design.persistence.Directions;
import com.design.persistence.News;
import com.design.persistence.Queries;
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

	@Override
	protected void init(VaadinRequest request) {
		Broadcaster.register(this);
		
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(new DashboardDesign ());

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		//layout.addComponent(new DirectionsPieChart());
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
		Notification.show("Received Query.");
		System.out.println("received query");
	}

	@Override
	public void receiveBroadcast(Directions dir) {
		Notification.show("Received directions");
		System.out.println("received directions");
	}

	@Override
	public void receiveBroadcast(News news) {
		this.access(new Runnable ()  {
			@Override
			public void run () {
				Notification.show("received " + news.getQueries().getQuery(), Type.ERROR_MESSAGE);
				UI.getCurrent().push();
			}
		});
		
		System.out.println("received news");
	}

}