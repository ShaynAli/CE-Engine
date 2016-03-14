package com.design.dashboard;

import com.vaadin.ui.themes.ValoTheme;

public class ConsoleView extends Console {

	public ConsoleView () {
		ui();
	}
	
	private void ui () {
		titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
       // textarea.add
	}
	
}