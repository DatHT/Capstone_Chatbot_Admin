package com.psib.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.psib.common.restclient.RestfulException;
import com.psib.constant.LogStatus;
import com.psib.constant.StatusCode;
import com.psib.dto.DateDto;
import com.psib.dto.jsonmapper.TrainDto;

public interface ILogManager {

	JSONObject getLogs() throws JSONException, IOException;
	
	public JSONObject getLogByLogId(String logId) throws JSONException, IOException;

	void updateLog() throws JSONException, IOException;

	List<JSONObject> getAllLogsFromFile(String atDate) throws IOException, JSONException;

	public StatusCode addPhrase(String listPhrase) throws JSONException, IOException, RestfulException;

	public List<TrainDto> getTraingPool() throws IOException;
	
	public void updateTrainingLog(String data) throws IOException ;
	
	public JSONArray conversationCollector(String atDate) throws IOException, JSONException;
	
	public List<DateDto> getListDateLog();
	
	public JSONObject setLogStatus(String logId, LogStatus logStatus) throws JSONException, IOException;
	
	public JSONArray getAllConversations(String logId) throws JSONException, IOException;
}
