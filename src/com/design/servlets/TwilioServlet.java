package com.design.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.sdk.verbs.Record;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

@WebServlet("/echo")
public class TwilioServlet extends HttpServlet
{

	public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException
	{
		TwiMLResponse response = new TwiMLResponse();
//		Say say = new Say("VOICE VOICE VOICE VOICE VOICE VOICE VOICE VOICE");
		
		String callerNumber = httpRequest.getParameter("From");
		
		Record rec = new Record();
		rec.setMaxLength(30);
		
		// Send a response
		try
		{
//			response.append(say);
			response.append(rec);
		}
		catch(TwiMLException e)
		{
			e.printStackTrace();
		}
		
		httpResponse.setContentType("application/xml");
		httpResponse.getWriter().print(response.toXML());
		
	} // service class
	
} // TwilioServlet class
