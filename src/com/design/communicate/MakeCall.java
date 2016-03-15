package com.design.communicate;



import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
 
public class MakeCall {
 
    public static final String ACCOUNT_SID = "ACa5505abc27a7d474f55d817367c57f45";
    public static final String AUTH_TOKEN = "11685b45e64715afef26e26781f5ad98";
    
    // The number to call
//    String number;
 
    public static void main(String[] args) throws TwilioRestException {
 
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account mainAccount = client.getAccount();
        ArrayList<NameValuePair> callParams = new ArrayList<NameValuePair>();
//        callParams.add(new BasicNameValuePair ("To", number)); // Receiving number
//        callParams.add(new BasicNameValuePair ("To", "+14165623181")); // Receiving number
        callParams.add(new BasicNameValuePair ("From", "+12892721224"));
        callParams.add(new BasicNameValuePair ("Url", "http://demo.twilio.com/welcome/voice/")); // WIP: change URL to server URL
        callParams.add(new BasicNameValuePair("Method", "GET"));
        callParams.add(new BasicNameValuePair("StatusCallback", "https://www.myapp.com/events")); // WIP: change URL to server URL
        callParams.add(new BasicNameValuePair("StatusCallbackMethod", "POST"));
        callParams.add(new BasicNameValuePair("StatusCallbackEvent", "initiated"));
        callParams.add(new BasicNameValuePair("StatusCallbackEvent", "ringing"));
        callParams.add(new BasicNameValuePair("StatusCallbackEvent", "answered"));
        callParams.add(new BasicNameValuePair("StatusCallbackEvent", "completed"));
        // Make the call
        CallFactory callFactory = mainAccount.getCallFactory();
        Call call = callFactory.create(callParams);
        // Print the call SID (a 32 digit hex like CA123..)
        System.out.println("Call SID:" + call.getSid());
    }
    
    /*
     * This method makes calls with simplified parameter
     * @param String number The number to be called
     * @param String message The message to relay
     */
//    void call(String number, String message)
//    {
//    	this.number = number;
//    }
}