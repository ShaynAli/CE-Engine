package com.design.servlets;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.sdk.verbs.Record;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
@WebServlet("/echo")
public class TwilioServlet extends HttpServlet
{
	// Recording time in seconds
	static int REC_DURATION = 30;
	
	public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException
	{
		TwiMLResponse response = new TwiMLResponse();
		
//		String callerNumber = httpRequest.getParameter("From");
		
		Record rec = new Record();
		rec.setMaxLength(REC_DURATION);

		System.out.println("00: About to run voice servlet statement in TwilioServlet.java");
		
		rec.setAction("/voice-servlet");
		
		System.out.println("01: Voice servlet statement in TwilioServlet.java passed");

//		String recordingUrl = httpRequest.getParameter("RecordingURL");
//		System.out.println("Recording URL: " + recordingUrl);
		
		// Send a response
//		if (recordingUrl != null)
//		{
//			System.out.println("recordingURL was not null");
			try
			{
				// Prompt
				response.append(new Say("Please say your query in a clear tone"));
				response.append(rec);
//				response.append(new Say ("Here is what was picked up"));
//				response.append(new Play (recordingUrl));
//				response.append(new Say ("End of message"));
			}
			catch(TwiMLException e)
			{
				e.printStackTrace();
			}
//		}
//		else
//		{
//			System.out.println("recordingURL was null");
//			httpResponse.sendRedirect("/echo");
//			return;
//		}
		httpResponse.setContentType("application/xml");
		httpResponse.getWriter().print(response.toXML());
		
	} // service class
	
} // TwilioServlet class