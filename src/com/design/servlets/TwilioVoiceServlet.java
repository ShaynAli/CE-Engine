package com.design.servlets;

import java.io.IOException;
import java.net.URL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.design.communicate.ProcessUser;
import com.design.communicate.VoiceProcessing;
import com.design.persistence.Queries;
import com.example.designgui.Broadcaster;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

@WebServlet("/voice-servlet")
public class TwilioVoiceServlet extends HttpServlet
{
	
	public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException
	{
		String recordingUrl = httpRequest.getParameter("RecordingUrl");
		System.out.println("Recording URL: " + recordingUrl);
		TwiMLResponse response = new TwiMLResponse();
		
		if (recordingUrl != null)
		{
			System.out.println("Attempting to record call");
			try
			{
				String queryResponse = VoiceProcessing.processAudio(new URL(recordingUrl));
				
				Queries qu = new Queries();
				qu.setType("voice");
				qu.setQuery(queryResponse);
				Broadcaster.broadcast("recog", qu);
				
				response.append(new Say("You said " + queryResponse));
				response.append(new Say(ProcessUser.processVoiceQuery(queryResponse, httpRequest.getParameter("From"))));
				response.append(new Say("End of message."));
				
			}
			catch(TwiMLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("recordingURL was null");
			httpResponse.sendRedirect("/DesignGui/echo");
			return;
		}
		
		httpResponse.setContentType("application/xml");
		httpResponse.getWriter().print(response.toXML());
	}
	
} // TwilioVoiceServlet class
