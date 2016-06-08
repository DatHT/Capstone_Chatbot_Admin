package com.psib.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public interface ILogManager {
	
	public JSONObject getLogJson() throws JSONException, IOException;
	public void updateLog() throws JSONException, IOException;
	public List<JSONObject> getAllLogs() throws IOException, JSONException;
}
