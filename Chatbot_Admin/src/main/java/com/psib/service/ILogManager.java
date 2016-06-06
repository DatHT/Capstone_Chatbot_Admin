package com.psib.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public interface ILogManager {
	public List<JSONObject> getLogs() throws IOException, JSONException;
}
