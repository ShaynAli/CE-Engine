package com.design.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

@WebServlet("/echo")
public class TwilioServlet extends HttpServlet
{

	public void service(HttpServletRequest HttpRequest, HttpServletResponse HttpResponse) throws IOException
	{
		TwiMLResponse response = new TwiMLResponse();
		Say say = new Say("Hello world");
		
		try
		{
			response.append(say);
		}
		catch(TwiMLException e)
		{
			e.printStackTrace();
		}
		
		HttpResponse.setContentType("applicition/xml");
		HttpResponse.getWriter().print(response.toXML());
		
	} // service class
	
} // TwilioServlet class
