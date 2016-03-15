package com.design.dashboard;

import com.design.persistence.Queries;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class ConsoleView extends Console {
	
	private String filter;

	public ConsoleView () {
		ui();
		filter = null;
	}
	
	private void ui () {
		titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        textarea.addStyleName("v-textfield-my-text");
        
        super.filter.addClickListener(new ClickListener () {

			@Override
			public void buttonClick(ClickEvent event) {
				filter = field.getValue().toString();
				field.clear();
				
			}
        	
        });
       // textarea.add
	}

	public void receiveString(String type, Queries qu) {
		System.out.println("1heere");
		System.out.println("+1" + filter + "     vs.   " + qu.getPhone().getPhone());
		if (filter == null || (filter != null &&  qu.getPhone().getPhone().equals("+1" + filter))) {
			System.out.println("heere");
			if (type.equals("input")) {
				super.textarea.clear();
				if (qu.getType().equals("sms")) {
					super.textarea.setValue(textarea.getValue() + "\n<h1><b>Received Text:</b></h1> \"" + qu.getQuery() + "\" from " + qu.getPhone().getPhone());
				} else {
					super.textarea.setValue(textarea.getValue() + "\n<h1><b>Received Call:</b></h1> from " + qu.getPhone().getPhone());
				}
			} else if (type.equals("recog")) {
				super.textarea.setValue(textarea.getValue() + "\n<h1><b>Identified Call Content:</b></h1> " + qu.getQuery());
			} else if (type.equals("class")) {
				super.textarea.setValue(textarea.getValue() + "\n<h1><b>Identified Class:</b></h1> " + qu.getClass1() + " with " + 
						qu.getConfidence()*100 + "% confidence");
			} else if (type.equals("twilio")) {
				super.textarea.setValue(textarea.getValue() + "\n<h1><b>Output Sent to Twilio</b></h1>");
			} else  {
				super.textarea.setValue(textarea.getValue() + "\n<h1><b>Determined Output:</b></h1> " + type);
			} 
		}			
	}
	
}