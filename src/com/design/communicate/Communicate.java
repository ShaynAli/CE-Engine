/** 
 * Communicate class is used to send results back to the user
 */
package com.design.communicate;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.design.servlets.SMSServlet;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public interface Communicate {

	public static final String ACCOUNT_SID = "ACa5505abc27a7d474f55d817367c57f45";
	public static final String AUTH_TOKEN = "11685b45e64715afef26e26781f5ad98";
	
	/*public static void sendText (String text) {
    	TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
    	 
        // Build a filter for the MessageList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", text));
        //params.add(new BasicNameValuePair("To", SMSServlet.from));
        params.add(new BasicNameValuePair("From", "+12892721224"));
     
        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = null;
		try {
			message = messageFactory.create(params);
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(message.getSid());
    }*/
	
	public static void sendText (String text, String phone) {
		text = text.replace("&#39;", "'");
		text = text.replace("&quot;", "");
		
		if (text.length() > 1600) {
			int num = Math.floorDiv(text.length(), 1600) + 1;
			int length = text.length();
			System.out.println("Length: " + length);
			for (int i = 0; i < length; i += 1600) {
				int end = i + 1600;
				if (end > text.length()) {
					end = text.length() - 1;
				}
				System.out.println(i);
				Communicate.sendText(text.substring(i, end), phone);
			}
		} else {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	    	 
	        // Build a filter for the MessageList
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("Body", text));
	        params.add(new BasicNameValuePair("To", phone));
	        params.add(new BasicNameValuePair("From", "+12892721224"));
	     
	        MessageFactory messageFactory = client.getAccount().getMessageFactory();
	        Message message = null;
			try {
				message = messageFactory.create(params);
			} catch (TwilioRestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println(message.getSid());
		}

    }
}
