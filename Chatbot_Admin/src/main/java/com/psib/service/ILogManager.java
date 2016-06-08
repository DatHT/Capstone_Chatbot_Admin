package com.psib.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public interface ILogManager {
	
	JSONObject getLogJson() throws JSONException, IOException;
	void updateLog() throws JSONException, IOException;
	List<JSONObject> getAllLogs() throws IOException, JSONException;
}
