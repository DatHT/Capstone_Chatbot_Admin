package com.psib.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.psib.service.ILogManager;
import com.psib.util.FileUtils;

@Service
public class LogManager implements ILogManager {

	private static String START_LOG = ">>>>>";
	private static String END_LOG = "<<<<<";

	private static String status_code = "statusCode";
	private static String log_json = "logJson";

	private static int NOT_FOUND_CODE = 404;
	private static int NO_ENTRY_CODE = 300;

	private static String logPath = "/Users/HuyTCM/Desktop/log";

	public JSONObject logJson;

	@Override
	public JSONObject getLogJson() throws JSONException, IOException {
		if (logJson == null) {
			BufferedReader bufferedReader = FileUtils.readFile(logPath);
			StringBuilder stringBuilder = new StringBuilder();
			String tempString = null;
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuilder.append(tempString);
			}
			logJson = new JSONObject(stringBuilder.toString());
		}
		return logJson;
	}

	@Override
	public void updateLog() throws JSONException, IOException {
		JSONObject logJson = this.getLogJson();
		List<JSONObject> logs = this.getAllLogs();

		JSONArray jsonArray = new JSONArray();
		JSONObject log;
		for (JSONObject jsonObject : logs) {
			int statusCode = Integer.parseInt(jsonObject.get(status_code).toString());

			log = null;
			if (statusCode == NOT_FOUND_CODE) {
				log = this.getNotFoundLog(jsonObject);
			} else if (statusCode == NO_ENTRY_CODE) {
				log = this.getNoEntryLog(jsonObject);
			}
			if (log != null) {
				jsonArray.put(log);
			}
		}

		logJson.put("contents", jsonArray);
		logJson.put("modifiedDate", new Date());

		this.logJson = logJson;
		FileUtils.writleFile(logPath, this.logJson.toString());
	}

	/**
	 * Get logs from logs file.
	 * 
	 * @return logs List of JSON log file format {'statusCode' : code, 'log' :
	 *         log}
	 */
	@Override
	public List<JSONObject> getAllLogs() throws IOException, JSONException {
		String filePath = "/Users/HuyTCM/Desktop/121904288222129";
		BufferedReader bufferedReader = FileUtils.readFile(filePath);
		String line;
		StringBuffer log = new StringBuffer();
		List<JSONObject> logs = new ArrayList<JSONObject>();
		while ((line = bufferedReader.readLine()) != null) {
			if (line.length() < 5) {
				log.append(line);
				continue;
			}
			if (line.subSequence(0, 5).equals(START_LOG)) {
				log = new StringBuffer();
				continue;
			}
			if (line.subSequence(0, 5).equals(END_LOG)) {
				int logCode = Integer.valueOf(line.substring(5, 8));

				JSONObject jsonObject = new JSONObject();
				jsonObject.put(status_code, logCode);
				jsonObject.put(log_json, new JSONObject(log.toString()));

				logs.add(jsonObject);
				continue;
			}
			log.append(line);
		}
		return logs;
	}

	private JSONObject getNoEntryLog(JSONObject log) throws IOException, JSONException {
		String userSay = log.getJSONObject(log_json).getJSONObject("result").getString("resolvedQuery");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("errCode", log.getString(status_code));
		jsonObject.put("userSay", userSay);

		return jsonObject;
	}

	private JSONObject getNotFoundLog(JSONObject log) throws IOException, JSONException {
		JSONObject contextJson = log.getJSONObject(log_json).getJSONObject("result").getJSONArray("contexts")
				.getJSONObject(0).getJSONObject("parameters");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("errCode", log.getString(status_code));
		jsonObject.put("contexts", contextJson);

		return jsonObject;
	}
}
