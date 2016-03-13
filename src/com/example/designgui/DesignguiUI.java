package com.example.designgui;

import javax.servlet.annotation.WebServlet;

import com.design.charts.DirectionsPieChart;
import com.design.dashboard.Dashboard;
import com.design.dashboard.DashboardDesign;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("designgui")
public class DesignguiUI extends UI {

	@WebServlet(value = {"/app/*", "/VAADIN/*"}, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DesignguiUI.class, widgetset = "com.example.designgui.widgetset.DesignguiWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
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

}