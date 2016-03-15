package com.design.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.sdk.verbs.Play;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

@WebServlet("/voice-servlet")
public class TwilioVoiceServlet extends HttpServlet
{
	
	public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException
	{
		String recordingUrl = httpRequest.getParameter("RecordingURL");
		System.out.println("Recording URL: " + recordingUrl);
		TwiMLResponse response = new TwiMLResponse();
		
		if (recordingUrl != null)
		{
			try
			{
				// Give user heads up on playing recording
				response.append(new Say ("Here is what was picked up"));
				response.append(new Play (recordingUrl));
				response.append(new Say ("End of message"));
			}
			catch(TwiMLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			httpResponse.sendRedirect("/echo");
			return;
		}
		
		httpResponse.setContentType("application/xml");
		httpResponse.getWriter().print(response.toXML());
	}
	
} // TwilioVoiceServlet class
