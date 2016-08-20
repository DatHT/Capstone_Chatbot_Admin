package com.psib.common.factory;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.psib.common.JsonParser;
import com.psib.common.restclient.RequestMethod;
import com.psib.common.restclient.RestClient;
import com.psib.common.restclient.RestResult;
import com.psib.util.SpringPropertiesUtil;

@Component
public class FacebookMessengerFactory {
	private RestClient restClient;
	private String access_token;
	private String graphAPI;
	
	public FacebookMessengerFactory() {
		graphAPI = SpringPropertiesUtil.getProperty("graphAPIURL") + "/" + SpringPropertiesUtil.getProperty("graphAPIVersion");
		access_token = SpringPropertiesUtil.getProperty("access_token");
		restClient = new RestClient(graphAPI + "/me/messages");
	}
	public boolean sendMessage(String recipient, String message) throws JSONException, IOException {
		JSONObject body = new JSONObject();
		JSONObject recipientObj = new JSONObject();
		recipientObj.put("id", recipient);
		JSONObject messageObj = new JSONObject();
		messageObj.put("text", message);
		body.put("recipient", recipientObj);
		body.put("message", messageObj);
		
		Object obj = JsonParser.toObject(body.toString(), Object.class);
		
		RestResult result = restClient.createInvoker(RequestMethod.POST).addHeader("Content-Type", "application/json")
				.addParameter("access_token", access_token).invoke(obj);
		return result.isOk();
	}
}
