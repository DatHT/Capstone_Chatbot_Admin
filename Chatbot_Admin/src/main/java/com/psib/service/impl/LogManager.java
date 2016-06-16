package com.psib.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.common.restclient.RestfulException;
import com.psib.constant.StatusCode;
import com.psib.dto.jsonmapper.Entry;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.ILogManager;
import com.psib.util.CommonUtils;
import com.psib.util.FileUtils;

@Service
public class LogManager implements ILogManager {
	@Autowired
	private ILexicalCategoryManager lexicalCategoryManager;

	private static String START_LOG = ">>>>>";
	private static String END_LOG = "<<<<<";

	private static String status_code = "statusCode";
	private static String log_json = "logJson";

	private static int NOT_FOUND_CODE = 404;
	private static int NO_ENTRY_CODE = 300;

	private static String chatLogsFolder = "/Users/HuyTCM/Desxktop/Loxgs";
	private static String logPath = chatLogsFolder + "/log";

	private static String LOG_JSON_FORMAT_MODIFIED_DATE = "modifiedDate";
	private static String LOG_JSON_FORMAT_CONTENTS = "contents";

	private static String errCode = "errCode";
	private static String userSay = "userSay";
	private static String contexts = "contexts";
	private static String action = "action";
	private static String intentName = "intentName";

	public JSONObject logJson;

	public void initialLogManager() throws JSONException, IOException {
		logJson = new JSONObject();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 0);

		logJson.put(LOG_JSON_FORMAT_MODIFIED_DATE, CommonUtils.getDateStringFormat(calendar.getTime()));
		logJson.put(LOG_JSON_FORMAT_CONTENTS, new JSONArray());

		this.updateLog();
	}

	@Override
	public JSONObject getLogJson() throws JSONException, IOException {
		if (logJson == null) {
			BufferedReader bufferedReader = FileUtils.readFile(logPath);
			StringBuilder stringBuilder = new StringBuilder();
			String tempString = null;
			while ((tempString = bufferedReader.readLine()) != null) {
				stringBuilder.append(tempString);
			}
			if (stringBuilder.length() != 0) {
				logJson = new JSONObject(stringBuilder.toString());
			}
		}
		return logJson;
	}

	@Override
	public void updateLog() throws JSONException, IOException {
		JSONObject logJson = this.getLogJson();
		List<JSONObject> logs = this.getAllLogs();

		JSONArray jsonArray;
		// // uncomment when run in production by timer.
		jsonArray = new JSONArray(logJson.get(LOG_JSON_FORMAT_CONTENTS).toString());
		// jsonArray = new JSONArray();

		JSONObject log;
		for (JSONObject jsonObject : logs) {
			int statusCode = Integer.parseInt(jsonObject.get(status_code).toString());

			log = null;
			if (statusCode == NOT_FOUND_CODE) {
				log = this.getNotFoundLog(jsonObject);
			} else if (statusCode == NO_ENTRY_CODE) {
				log = this.getNoEntryLog(jsonObject);
			}
			if (log != null && !checkExistLog(jsonArray, log)) {
				log.put(action, jsonObject.getJSONObject(log_json).getJSONObject("result").get(action));
				log.put(intentName, jsonObject.getJSONObject(log_json).getJSONObject("result").getJSONObject("metadata")
						.get(intentName));

				jsonArray.put(log);
			}
		}

		logJson.put(LOG_JSON_FORMAT_CONTENTS, jsonArray);
		logJson.put(LOG_JSON_FORMAT_MODIFIED_DATE, CommonUtils.getDateStringFormat(new Date()));

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
		List<JSONObject> logs = new ArrayList<JSONObject>();

		List<String> fileList = getNewFileLog();

		if (fileList != null) {
			for (String filePath : fileList) {
				BufferedReader bufferedReader = FileUtils.readFile(filePath);
				String line;
				StringBuffer log = new StringBuffer();

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
			}
		}

		return logs;
	}

	private JSONObject getNoEntryLog(JSONObject log) throws IOException, JSONException {
		String userSay = log.getJSONObject(log_json).getJSONObject("result").getString("resolvedQuery");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(errCode, log.getString(status_code));
		jsonObject.put(LogManager.userSay, userSay);

		return jsonObject;
	}

	private JSONObject getNotFoundLog(JSONObject log) throws IOException, JSONException {
		JSONObject contextJson = log.getJSONObject(log_json).getJSONObject("result").getJSONArray("contexts")
				.getJSONObject(0).getJSONObject("parameters");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(errCode, log.getString(status_code));
		jsonObject.put(contexts, contextJson);

		return jsonObject;
	}

	private List<String> getNewFileLog() throws JSONException, IOException {
		List<String> newFileLogPath = new ArrayList<String>();

		String lastModifiedDate = getLogJson().get(LOG_JSON_FORMAT_MODIFIED_DATE).toString();
		File file = new File(chatLogsFolder);
		if (!file.isDirectory()) {
			return null;
		}

		for (File dirList : file.listFiles()) {
			if (!dirList.isDirectory()) {
				continue;
			}
			if (dirList.getName().compareTo(lastModifiedDate) >= 0) {
				for (File logFile : dirList.listFiles()) {
					newFileLogPath.add(logFile.getPath());
				}
			}
		}

		return newFileLogPath;
	}

	public boolean checkExistLog(JSONArray jsonArray, JSONObject jsonObject)
			throws NumberFormatException, JSONException {
		int statusCode = Integer.parseInt(jsonObject.get(errCode).toString());
		boolean isExist = false;
		int i = 0;
		if (jsonArray.length() == 0) {
			return false;
		}
		while (i < jsonArray.length() && !isExist) {
			JSONObject log = jsonArray.getJSONObject(i);
			int logStatusCode = Integer.parseInt(log.get(errCode).toString());
			if (statusCode == logStatusCode) {
				if (statusCode == NO_ENTRY_CODE) {
					isExist = jsonObject.get(userSay).equals(log.get(userSay));
				} else if (statusCode == NOT_FOUND_CODE) {
					isExist = jsonObject.getJSONObject(contexts).get("Food")
							.equals(log.getJSONObject(contexts).get("Food"))
							&& jsonObject.getJSONObject(contexts).get("Location")
									.equals(log.getJSONObject(contexts).get("Location"));
				}
			}
			i++;
		}

		return isExist;
	}

	public void deleteLog(String logString) throws JSONException, IOException {
		JSONObject logJson = this.getLogJson();

		JSONArray jsonArray = new JSONArray(logJson.get(LOG_JSON_FORMAT_CONTENTS).toString());
		JSONArray newJson = new JSONArray();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject log = jsonArray.getJSONObject(i);
			int logStatusCode = Integer.parseInt(log.get(errCode).toString());
			if (logStatusCode == NO_ENTRY_CODE) {
				if (!log.get(userSay).equals(logString)) {
					newJson.put(log);
				}
			}
		}
		logJson.put(LOG_JSON_FORMAT_CONTENTS, jsonArray);
		logJson.put(LOG_JSON_FORMAT_MODIFIED_DATE, CommonUtils.getDateStringFormat(new Date()));

		this.logJson = logJson;
		FileUtils.writleFile(logPath, this.logJson.toString());
	}

	@Override
	public boolean addPhrase(String listPhrase) throws JSONException, IOException, RestfulException {
		JSONObject jsonObject = new JSONObject(listPhrase);

		@SuppressWarnings("unchecked")
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = jsonObject.getString(key);

			Entry entry = new Entry();
			entry.setValue(key);
			List<String> synonym = new ArrayList<>();
			synonym.add(key);
			entry.setSynonyms(synonym);

			StatusCode code = lexicalCategoryManager.addPhrase(entry, value);
			switch (code) {
			case SUCCESS:
				deleteLog(key);
				return true;
			default:
				return false;
			}
		}
		return true;
	}
}
