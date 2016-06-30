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

import com.psib.common.JsonParser;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.StatusCode;
import com.psib.dao.IProductDetailDao;
import com.psib.dto.jsonmapper.Entry;
import com.psib.model.ProductDetail;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.ILogManager;
import com.psib.util.CommonUtils;
import com.psib.util.FileUtils;

@Service
public class LogManager implements ILogManager {
	@Autowired
	private IProductDetailDao productDetailDao;

	@Autowired
	private ILexicalCategoryManager lexicalCategoryManager;

	private static String START_LOG = ">>>>>";
	private static String END_LOG = "<<<<<";

	private static String status_code = "statusCode";
	private static String log_json = "logJson";

	private static int NOT_FOUND_CODE = 404;
	private static int NO_ENTRY_CODE = 300;
	private static int REPORT_CODE = 400;
	private static int TRAINING_CODE = 202;
	private static int FEEDBACK_CODE = 609;

	private static String chatLogsFolder = "G:/OneDrive/Documents/FPT/NewFPTOnedrive/Semester9/Capstone/Chatbot/repository/engine/Capstone_Chatbot_Engine/bin";
	private static String logPath = chatLogsFolder + "/log";

	private static String LOG_JSON_FORMAT_MODIFIED_DATE = "modifiedDate";
	private static String LOG_JSON_FORMAT_CONTENTS = "contents";

	private static String id = "id";
	private static String errCode = "errCode";
	private static String userSay = "userSay";
	private static String contexts = "contexts";

	private static String count = "count";

	public JSONObject logJson;

	@Override
	public JSONObject getLogJson() throws JSONException, IOException {
		if (logJson != null) {
			return logJson;
		}

		logJson = readJsonLogFile(logPath);
		if (logJson == null) {
			logJson = new JSONObject();

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 0);

			logJson.put(LOG_JSON_FORMAT_MODIFIED_DATE, CommonUtils.getDateStringFormat(calendar.getTime()));
			logJson.put(LOG_JSON_FORMAT_CONTENTS, new JSONArray());
		}
		return logJson;
	}

	private JSONObject readJsonLogFile(String logPath) throws IOException, JSONException {
		BufferedReader bufferedReader = FileUtils.readFile(logPath);
		StringBuilder stringBuilder = new StringBuilder();
		String tempString = null;
		while ((tempString = bufferedReader.readLine()) != null) {
			stringBuilder.append(tempString);
		}
		if (stringBuilder.length() != 0) {
			return new JSONObject(stringBuilder.toString());
		}
		return null;
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
		JSONObject tempJson;
		for (JSONObject jsonObject : logs) {
			int statusCode = Integer.parseInt(jsonObject.get(status_code).toString());

			log = null;
			tempJson = jsonObject.getJSONObject(log_json);

			if (statusCode == NOT_FOUND_CODE) {
				log = this.getNotFoundLog(tempJson);
				log.put(id, tempJson.get(id));
			} else if (statusCode == NO_ENTRY_CODE) {
				log = this.getNoEntryLog(tempJson);
				log.put(id, tempJson.get(id));
			} else if (statusCode == REPORT_CODE) {
				log = this.getReportLog(tempJson);
			} else if (statusCode == TRAINING_CODE) {
				log = this.getTrainingLog(tempJson);
			} else if (statusCode == FEEDBACK_CODE) {
				log = this.getFeedbackLog(tempJson);
			}
			if (log == null) {
				continue;
			}

			log.put(errCode, statusCode);

			if (!checkExistLog(jsonArray, log)) {
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
	 * @throws JSONException
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
						try {
							jsonObject.put(status_code, logCode);
							jsonObject.put(log_json, new JSONObject(log.toString()));
							logs.add(jsonObject);
						} catch (JSONException e) {
							System.out.println("Parsing JSON error!");
						}

						continue;
					}
					log.append(line);
				}
			}
		}

		return logs;
	}

	private JSONObject getNoEntryLog(JSONObject log) throws IOException, JSONException {
		String userSay = log.getJSONObject("result").getString("resolvedQuery");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(LogManager.userSay, userSay);

		JSONArray arrId = new JSONArray();

		JSONObject idObj = new JSONObject();
		idObj.put(id, log.get(id));
		arrId.put(idObj);

		jsonObject.put(count, arrId);
		jsonObject.put("totalCount", arrId.length());

		return jsonObject;
	}

	private JSONObject getNotFoundLog(JSONObject log) throws IOException, JSONException {
		JSONObject contextJson = log.getJSONObject("result").getJSONArray("contexts").getJSONObject(0)
				.getJSONObject("parameters");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(contexts, contextJson);

		return jsonObject;
	}

	private JSONObject getReportLog(JSONObject log) throws JSONException {

		long productId = log.getLong("itemId");
		long addressId = log.getLong("addressId");

		ProductDetail productDetail = productDetailDao.getProductDetailById(productId);

		JSONObject jsonObject = null;
		if (productDetail != null) {
			String stringJsonProductAddress = JsonParser.toJson(productDetail);
			jsonObject = new JSONObject(stringJsonProductAddress);
		}

		return jsonObject;
	}

	private JSONObject getTrainingLog(JSONObject log) throws JSONException {
		String userSay = log.getJSONObject("result").getString("resolvedQuery");
		String patternStr = "train:";
		
		String train = userSay.substring(patternStr.length());
		
		return new JSONObject().put("train", train);
	}
	
	private JSONObject getFeedbackLog(JSONObject log) throws JSONException {
		String userSay = log.getJSONObject("result").getString("resolvedQuery");
		String patternStr = "feedback:";
		
		String feedback = userSay.substring(patternStr.length());
		
		return new JSONObject().put("feedback", feedback);
	}

	private List<String> getNewFileLog() throws IOException, JSONException {
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

	public boolean checkExistLog(JSONArray jsonArray, JSONObject jsonObject) throws JSONException {
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
					if (jsonObject.get(userSay).equals(log.get(userSay))) {
						JSONArray arrId = log.getJSONArray(count);
						boolean isCount = true;
						for (int j = 0; j < arrId.length(); j++) {
							JSONObject jsonId = arrId.getJSONObject(j);
							if (jsonId.get(id).equals(jsonObject.get(id))) {
								isCount = false;
							}
						}

						if (isCount) {
							JSONObject idObj = new JSONObject();
							idObj.put(id, jsonObject.get(id));
							arrId.put(idObj);
						}
						log.put("totalCount", arrId.length());
						return true;
					}
				} else if (statusCode == NOT_FOUND_CODE) {
					try {
						isExist = jsonObject.getJSONObject(contexts).get("Food")
								.equals(log.getJSONObject(contexts).get("Food"))
								&& jsonObject.getJSONObject(contexts).get("Location")
										.equals(log.getJSONObject(contexts).get("Location"));
					} catch (Exception e) {
						isExist = true;
					}

				} else if (statusCode == REPORT_CODE) {
					isExist = jsonObject.get("productId").equals(log.get("productId"))
							&& jsonObject.get("addressId").equals(log.get("addressId"));
				} else if (statusCode == TRAINING_CODE) {
					isExist = jsonObject.get("train").equals(log.get("train"));
				} else if (statusCode == FEEDBACK_CODE) {
					isExist = jsonObject.get("feedback").equals(log.get("feedback"));
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
	public StatusCode addPhrase(String listPhrase) throws JSONException, IOException, RestfulException {
		JSONObject jsonObject = new JSONObject(listPhrase);

		@SuppressWarnings("unchecked")
		Iterator<String> keys = jsonObject.keys();
		StatusCode code = StatusCode.SUCCESS;
		while (keys.hasNext() && code == StatusCode.SUCCESS) {
			String key = keys.next();
			String value = jsonObject.getString(key);

			Entry entry = new Entry();
			entry.setValue(key);
			List<String> synonym = new ArrayList<>();
			synonym.add(key);
			entry.setSynonyms(synonym);

			code = lexicalCategoryManager.addPhrase(entry, value);
		}
		return code;
	}
}
