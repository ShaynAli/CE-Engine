import java.util.HashMap;
import java.util.Map;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
 
public class MakeCall {
 
    public static final String ACCOUNT_SID = "ACa5505abc27a7d474f55d817367c57f45";
    public static final String AUTH_TOKEN = "11685b45e64715afef26e26781f5ad98";
 
    public static void main(String[] args) throws TwilioRestException {
 
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account mainAccount = client.getAccount();
        Map<String, String> callParams = new HashMap<String, String>();
        callParams.put("To", "5105551212"); // Receiving number
        callParams.put("From", "+12892721224");
        callParams.put("Url", "http://demo.twilio.com/welcome/voice/");
//        callParams.add(new BasicNameValuePair("Method", "GET"));
//        callParams.add(new BasicNameValuePair("StatusCallback", "https://www.myapp.com/events"));
//        callParams.add(new BasicNameValuePair("StatusCallbackMethod", "POST"));
//        callParams.add(new BasicNameValuePair("StatusCallbackEvent", "initiated"));
//        callParams.add(new BasicNameValuePair("StatusCallbackEvent", "ringing"));
//        callParams.add(new BasicNameValuePair("StatusCallbackEvent", "answered"));
//        callParams.add(new BasicNameValuePair("StatusCallbackEvent", "completed"));
        // Make the call
        CallFactory callFactory = mainAccount.getCallFactory();
        Call call = callFactory.create(callParams)
        // Print the call SID (a 32 digit hex like CA123..)
        System.out.println(call.getSid());
    }
}