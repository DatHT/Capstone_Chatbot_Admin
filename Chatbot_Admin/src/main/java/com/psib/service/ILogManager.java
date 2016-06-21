package com.psib.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.psib.common.restclient.RestfulException;
import com.psib.constant.StatusCode;

public interface ILogManager {

	JSONObject getLogJson() throws JSONException, IOException;

	void updateLog() throws JSONException, IOException;

	List<JSONObject> getAllLogs() throws IOException, JSONException;

	public StatusCode addPhrase(String listPhrase) throws JSONException, IOException, RestfulException;
}
