package com.design.servlets;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.design.communicate.VoiceProcessing;
import com.twilio.sdk.verbs.Play;
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
				// Give user heads up on playing recording
				response.append(new Say ("Here is what was picked up"));
				response.append(new Play (recordingUrl));
				response.append(new Say ("End of message"));
				
				response.append(new Say ("Processing request"));
				URL queryURL = new URL(recordingUrl);
				File queryAudio = new File(queryURL.getFile());
//				String queryResponse = new String(VoiceProcessing.getString(queryAudio));
//				response.append(new Say(queryResponse));
				response.append(new Say(VoiceProcessing.getString(queryAudio)));
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
