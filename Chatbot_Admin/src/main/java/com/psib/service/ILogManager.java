package com.psib.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.psib.common.restclient.RestfulException;

public interface ILogManager {
	
	public void initialLogManager() throws JSONException, IOException;
	JSONObject getLogJson() throws JSONException, IOException;
	void updateLog() throws JSONException, IOException;
	List<JSONObject> getAllLogs() throws IOException, JSONException;
	public boolean addPhrase(String listPhrase) throws JSONException, IOException, RestfulException;
}
