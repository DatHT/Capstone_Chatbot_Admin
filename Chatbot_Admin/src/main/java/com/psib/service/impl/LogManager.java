package com.psib.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.common.JsonParser;
import com.psib.common.factory.FacebookMessengerFactory;
import com.psib.common.factory.LexicalCategoryFactory;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.LogStatus;
import com.psib.constant.StatusCode;
import com.psib.dao.IFileServerDao;
import com.psib.dao.IProductDetailDao;
import com.psib.dto.DateDto;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.TrainDto;
import com.psib.model.ProductDetail;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.ILogManager;
import com.psib.service.IProductManager;
import com.psib.service.ISynonymManager;
import com.psib.util.CommonUtils;
import com.psib.util.FileUtils;
import com.psib.util.SentenceUtils;
import com.psib.util.SpringPropertiesUtil;

@Service
public class LogManager implements ILogManager {
	@Autowired
	private IProductDetailDao productDetailDao;

	@Autowired
	private IFileServerDao fileServerDao;

	@Autowired
	private ILexicalCategoryManager lexicalCategoryManager;

	@Autowired
	private IProductManager productManager;

	@Autowired
	private ISynonymManager synonymManager;

	@Autowired
	private FacebookMessengerFactory facebookFactory;
	
	@Autowired
	private LexicalCategoryFactory lexicalCategoryFactory;

	private static final Logger logger = Logger.getLogger(LogManager.class);

	private static String START_LOG = ">>>>>";
	private static String END_LOG = "<<<<<";

	private static String status_code = "statusCode";
	private static String log_json = "logJson";
	private static String status_of_log = "status";

	private static int SUCCESS_CODE = 200;
	private static int NOT_FOUND_CODE = 404;
	private static int NO_ENTRY_CODE = 300;
	private static int REPORT_CODE = 400;
	private static int TRAINING_CODE = 202;
	private static int FEEDBACK_CODE = 609;

	private String chatLogsFolder;

	private static String LOG_JSON_FORMAT_MODIFIED_DATE = "modifiedDate";
	private static String LOG_JSON_FORMAT_CONTENTS = "contents";

	private static String id = "id";
	private static String sessionId = "sessionId";
	private static String errCode = "errCode";
	private static String userSay = "userSay";
	private static String contexts = "contexts";
	// private static String action = "action";
	// private static String intentName = "intentName";
	private static String result = "result";
	private static String resolvedQuery = "resolvedQuery";

	private static String count = "count";

	public JSONObject logJSON;

	private String getLogFilePath() {
		if (chatLogsFolder == null) {
			chatLogsFolder = fileServerDao.getByName(SpringPropertiesUtil.getProperty("log_folder_path")).getUrl();
		}
		return chatLogsFolder + "/log";
		// // for debug
		// return "/Users/HuyTCM/Desktop/Logs/log";
	}

	private String getTrainingFilePath() {
		return fileServerDao.getByName(SpringPropertiesUtil.getProperty("log_folder_path")).getUrl() + "/training";
	}

	private String getTrainingPoolPath() {
		if (chatLogsFolder == null) {
			chatLogsFolder = fileServerDao.getByName(SpringPropertiesUtil.getProperty("log_folder_path")).getUrl();
		}
		return chatLogsFolder + "/training";
	}

	private JSONArray getTrainingList() throws IOException, JSONException {
		logger.info("[getTrainingList] : Start");
		BufferedReader bufferedReader = FileUtils.readFile(this.getTrainingPoolPath());
		StringBuilder stringBuilder = new StringBuilder();
		String tempString = null;
		while ((tempString = bufferedReader.readLine()) != null) {
			stringBuilder.append(tempString);
		}
		logger.info("[getTrainingList] : End");
		if (stringBuilder.length() != 0) {
			return new JSONArray(stringBuilder.toString());
		}
		return new JSONArray();
	}

	@Override
	public JSONObject getLogs() throws JSONException, IOException {
		logger.info("[getLogs] : Start");
		if (logJSON != null) {
			return logJSON;
		}

		logJSON = readJSONLogFile(this.getLogFilePath());
		if (logJSON == null) {
			logJSON = new JSONObject();

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 0);

			logJSON.put(LOG_JSON_FORMAT_MODIFIED_DATE, CommonUtils.getDateStringFormat(calendar.getTime()));
			logJSON.put(LOG_JSON_FORMAT_CONTENTS, new JSONArray());
		}
		logger.info("[getLogs] : End");
		return logJSON;
	}

	private JSONObject readJSONLogFile(String logPath) throws IOException, JSONException {
		logger.info("[readJSONLogFile] : Start");
		BufferedReader bufferedReader = FileUtils.readFile(logPath);
		StringBuilder stringBuilder = new StringBuilder();
		String tempString = null;
		while ((tempString = bufferedReader.readLine()) != null) {
			stringBuilder.append(tempString);
		}
		logger.info("[readJSONLogFile] : End");
		if (stringBuilder.length() != 0) {
			return new JSONObject(stringBuilder.toString());
		}
		return null;
	}

	private String readJsonTrainingFile(String logPath) throws IOException {
		logger.info("[readJsonTrainingFile] : Start");
		BufferedReader bufferedReader = FileUtils.readFile(logPath);
		StringBuilder stringBuilder = new StringBuilder();
		String tempString = null;
		while ((tempString = bufferedReader.readLine()) != null) {
			stringBuilder.append(tempString);
		}
		logger.info("[readJsonTrainingFile] : End");
		if (stringBuilder.length() != 0) {
			return stringBuilder.toString();
		}
		return null;
	}

	@Override
	public void updateLog() throws JSONException, IOException {
		logger.info("[updateLog] : Start");
		JSONObject logJson = this.getLogs();
		List<JSONObject> logs = this.getAllLogsFromFile(null);
		JSONArray trainArr = this.getTrainingList();

		JSONArray jsonArray;
		// // uncomment when run in production by timer.
		jsonArray = new JSONArray(logJson.get(LOG_JSON_FORMAT_CONTENTS).toString());
		// jsonArray = new JSONArray();

		JSONObject log;
		JSONObject tempJson;
		for (JSONObject jsonObject : logs) {
			int statusCode = Integer.parseInt(jsonObject.get(status_code).toString());

			log = null;
			String filePath = jsonObject.getString("filePath");
			tempJson = jsonObject.getJSONObject(log_json);
			tempJson.put("filePath", filePath);

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
				if (!checkTrainingExist(trainArr, log)) {
					trainArr.put(log);
					log = null;
				}
			} else if (statusCode == FEEDBACK_CODE) {
				log = this.getFeedbackLog(tempJson);
			}
			if (log == null) {
				continue;
			}

			log.put(errCode, statusCode);
			log.put(status_of_log, LogStatus.UNREAD);

			if (!checkExistLog(jsonArray, log)) {
				jsonArray.put(log);
			}
		}

		logJson.put(LOG_JSON_FORMAT_CONTENTS, jsonArray);
		logJson.put(LOG_JSON_FORMAT_MODIFIED_DATE, CommonUtils.getDateStringFormat(new Date()));

		this.logJSON = logJson;

		FileUtils.writeFile(this.getTrainingPoolPath(), trainArr.toString(4));
		FileUtils.writeFile(this.getLogFilePath(), this.logJSON.toString(4));
		logger.info("[updateLog] : End");
	}

	/**
	 * Get logs from logs file.
	 * 
	 * @return logs List of JSON log file format {'statusCode' : code, 'log' :
	 *         log}
	 * @throws JSONException
	 */
	@Override
	public List<JSONObject> getAllLogsFromFile(String atDate) throws IOException, JSONException {
		logger.info("[getAllLogsFromFile] : Start");
		List<JSONObject> logs = new ArrayList<JSONObject>();

		List<String> fileList = getFileLogPaths(atDate);

		if (fileList != null) {
			for (String filePath : fileList) {
				this.parseJSONObjectFromLogFile(filePath, logs);
			}
		}
		logger.info("[getAllLogsFromFile] : End");
		return logs;
	}

	private void parseJSONObjectFromLogFile(String filePath, List<JSONObject> logs) throws IOException {
		logger.info("[parseJSONObjectFromLogFile] : Start");
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
					jsonObject.put("filePath", filePath);
					jsonObject.put(log_json, new JSONObject(log.toString()));
					logs.add(jsonObject);
				} catch (JSONException e) {
					logger.error("Parsing JSON error!", e);
				}

				continue;
			}
			log.append(line);
		}
		logger.info("[parseJSONObjectFromLogFile] : End");
	}

	private JSONObject getNoEntryLog(JSONObject log) throws IOException, JSONException {
		logger.info("[getNoEntryLog] : Start");
		String userSay = log.getJSONObject(result).getString(resolvedQuery);
		String sessionIdStr = log.getString(sessionId);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(LogManager.userSay, userSay);

		List<String> productNames = new ArrayList<>();
		Set<String> productSet = productManager.findNewProductName(userSay);
		if (productSet != null) {
			productNames = new ArrayList<>(productSet);
		}

		JSONArray productNameArr = new JSONArray();
		for (String productName : productNames) {
			productNameArr.put(productName);
		}
		jsonObject.put("productName", productNameArr);

		JSONArray arrId = new JSONArray();

		JSONObject idObj = new JSONObject();
		idObj.put(id, log.get(id));
		idObj.put(sessionId, sessionIdStr);
		idObj.put("filePath", log.getString("filePath"));
		arrId.put(idObj);

		jsonObject.put(count, arrId);
		jsonObject.put("totalCount", arrId.length());

		logger.info("[getNoEntryLog] : End");
		return jsonObject;
	}

	private JSONObject getNotFoundLog(JSONObject log) throws IOException, JSONException {
		logger.info("[getNotFoundLog] : Start");
		JSONObject contextJson = log.getJSONObject(result).getJSONArray("contexts").getJSONObject(0)
				.getJSONObject("parameters");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(contexts, contextJson);

		JSONArray arrId = new JSONArray();

		JSONObject idObj = new JSONObject();
		idObj.put(id, log.get(id));
		arrId.put(idObj);

		jsonObject.put(count, arrId);
		jsonObject.put("totalCount", arrId.length());

		logger.info("[getNotFoundLog] : End");
		return jsonObject;
	}

	private JSONObject getReportLog(JSONObject log) throws JSONException {
		logger.info("[getReportLog] : Start");

		long productId = log.getLong("itemId");

		ProductDetail productDetail = productDetailDao.getProductDetailById(productId);

		JSONObject jsonObject = null;
		if (productDetail != null) {
			String stringJsonProductAddress = JsonParser.toJson(productDetail);
			jsonObject = new JSONObject(stringJsonProductAddress);
		}
		jsonObject.put(id, productId);

		logger.info("[getReportLog] : End");
		return jsonObject;
	}

	private JSONObject getTrainingLog(JSONObject log) throws JSONException, IOException {
		logger.info("[getTrainingLog] : Start");
		String userSay = log.getJSONObject("result").getString("resolvedQuery");
		String patternStr = "train:";

		String train = userSay.substring(patternStr.length());

		logger.info("[getTrainingLog] : End");
		return new JSONObject().put("train", train).put("isDeleted", false);
	}

	private boolean checkTrainingExist(JSONArray jsonArray, JSONObject log) throws JSONException {
		logger.info("[checkTrainingExist] : Start");
		boolean isExist = false;
		int i = 0;
		if (jsonArray.length() == 0) {
			return false;
		}
		while (i < jsonArray.length() && !isExist) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			isExist = log.get("train").equals(jsonObject.get("train"));
			i++;
		}
		logger.info("[checkTrainingExist] : End");
		return isExist;
	}

	private JSONObject getFeedbackLog(JSONObject log) throws JSONException {
		logger.info("[getFeedbackLog] : Start");
		String userSay = log.getJSONObject("result").getString("resolvedQuery");
		String patternStr = "feedback:";

		String feedback = userSay.substring(patternStr.length());

		logger.info("[getFeedbackLog] : End");
		return new JSONObject().put("feedback", feedback);
	}

	private List<String> getFileLogPaths(String strDate) throws IOException, JSONException {
		logger.info("[getFileLogPaths] : Start");
		List<String> fileLogPaths = new ArrayList<String>();

		String lastModifiedDate = getLogs().get(LOG_JSON_FORMAT_MODIFIED_DATE).toString();
		File file = new File(chatLogsFolder);
		if (!file.isDirectory()) {
			return null;
		}

		for (File dirList : file.listFiles()) {
			if (!dirList.isDirectory()) {
				continue;
			}
			if (strDate != null) {
				if (dirList.getName().equals(strDate)) {
					for (File logFile : dirList.listFiles()) {
						fileLogPaths.add(logFile.getPath());
					}
				}
				continue;
			} else {
				if (dirList.getName().compareTo(lastModifiedDate) >= 0) {
					for (File logFile : dirList.listFiles()) {
						fileLogPaths.add(logFile.getPath());
					}
				}
			}
		}
		logger.info("[getFileLogPaths] : End");
		return fileLogPaths;
	}

	@Override
	public JSONObject getLogByLogId(String logId) throws JSONException, IOException {
		logger.info("[getFileLogPaths] : Start");
		JSONArray logs = this.getLogs().getJSONArray(LOG_JSON_FORMAT_CONTENTS);
		JSONObject log = null;
		for (int i = 0; i < logs.length(); i++) {
			log = logs.getJSONObject(i);
			if (log.has(id) && log.getString(id).equals(logId)) {
				break;
			}
		}
		logger.info("[getFileLogPaths] : End");
		return log;
	}

	private boolean checkExistLog(JSONArray jsonArray, JSONObject jsonObject) throws JSONException {
		logger.info("[checkExistLog] : Start");
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
					String sentence = checkSentence(jsonObject.get(userSay).toString(), log.get(userSay).toString());
					if (sentence != null) {
						// if (jsonObject.get(userSay).equals(log.get(userSay)))
						// {
						JSONArray arrId = log.getJSONArray(count);
						boolean isCount = true;
						for (int j = 0; j < arrId.length(); j++) {
							JSONObject jsonId = arrId.getJSONObject(j);
							if (jsonId.get(id).equals(jsonObject.get(id))) {
								isCount = false;
							}
						}

						if (isCount) {
							arrId.put(jsonObject.getJSONArray(count).get(0));
						}
						log.put(id, jsonObject.get(id));
						log.put(userSay, sentence);
						log.put("totalCount", arrId.length());
						return true;
					}
				} else if (statusCode == NOT_FOUND_CODE) {
					try {
						isExist = jsonObject.getJSONObject(contexts).get("Food")
								.equals(log.getJSONObject(contexts).get("Food"))
								&& jsonObject.getJSONObject(contexts).get("Location")
										.equals(log.getJSONObject(contexts).get("Location"));
						if (isExist) {
							JSONArray arrId = log.getJSONArray(count);
							boolean isCount = true;
							for (int j = 0; j < arrId.length(); j++) {
								JSONObject jsonId = arrId.getJSONObject(j);
								if (jsonId.get(id).equals(jsonObject.get(id))) {
									isCount = false;
								}
							}

							if (isCount) {
								arrId.put(jsonObject.getJSONArray(count).get(0));
							}
							log.put("totalCount", arrId.length());
						}
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

		logger.info("[checkExistLog] : End");
		return isExist;
	}

	private String checkSentence(String sentence1, String sentence2) {
		String repSen1 = synonymManager.replaceSentenceBySynonym(sentence1);
		String repSen2 = synonymManager.replaceSentenceBySynonym(sentence2);
		logger.info("[checkSentence] - sentence1: " + repSen1);
		logger.info("[checkSentence] - sentence2: " + repSen2);
		if (SentenceUtils.checkContainSentencePercent(repSen1, repSen2) >= 0.6f) {

			return sentence1.trim().length() > sentence2.trim().length() ? sentence1 : sentence2;
		}
		return null;
	}

	public void deleteLog(String logString) throws JSONException, IOException {
		logger.info("[deleteLog] : Start");
		JSONObject logJson = this.getLogs();

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

		this.logJSON = logJson;
		FileUtils.writeFile(this.getLogFilePath(), this.logJSON.toString());
		logger.info("[deleteLog] : End");
	}

	@Override
	public StatusCode addPhrase(String listPhrase, String logId) throws JSONException, IOException, RestfulException {
		logger.info("[addPhrase] : Start");
		JSONObject jsonObject = new JSONObject(listPhrase);

		@SuppressWarnings("unchecked")
		Iterator<String> keys = jsonObject.keys();
		StatusCode code = StatusCode.SUCCESS;
		while (keys.hasNext() && code == StatusCode.SUCCESS) {
			String key = keys.next();
			String value = jsonObject.getString(key);
			if (lexicalCategoryFactory.getLexicalById(value).getName().equals("Food")) {
				Thread thread = new Thread("NotifyFacebookUserThread") {
					public void run() {
						try {
							notifyUser(logId, key);
						} catch (JSONException | IOException e) {
							logger.error("[Thread: NotifyFacebookUserThread] -- "+ e.getMessage());
						}
					}
				};
				thread.start();
			}

			Entry entry = new Entry();
			entry.setValue(key);
			List<String> synonym = new ArrayList<>();
			synonym.add(key);
			entry.setSynonyms(synonym);

			code = lexicalCategoryManager.addPhrase(entry, value);
		}
		logger.info("[addPhrase] : End");
		return code;
	}

	@Override
	public List<TrainDto> getTraingPool() throws IOException {
		logger.info("[getTraingPool] : Start");
		String trainJson = "";
		trainJson = readJsonTrainingFile(this.getTrainingFilePath());
		List<TrainDto> list = null;
		if (!trainJson.isEmpty()) {
			list = JsonParser.toList(trainJson, TrainDto.class);
		}
		logger.info("[getTraingPool] : End");
		return list;
	}

	@Override
	public void updateTrainingLog(String data) throws IOException {
		logger.info("[updateTrainingLog] : Start");
		FileUtils.writeFile(this.getTrainingFilePath(), data);
		logger.info("[updateTrainingLog] : End");
	}

	/*
	 * Collect all conversation with BOT by sessionId.
	 */
	public JSONArray conversationCollector(String atDate) throws IOException, JSONException {
		logger.info("[conversationCollector] : Start");
		List<JSONObject> allLog = this.getAllLogsFromFile(atDate);
		JSONArray jsonArray = new JSONArray();

		String strSessionId = "";
		for (JSONObject logJson : allLog) {
			JSONObject log = logJson.getJSONObject(log_json);

			int statusCode = logJson.getInt(status_code);
			if (statusCode == SUCCESS_CODE || statusCode == NO_ENTRY_CODE || statusCode == NOT_FOUND_CODE) {
				JSONObject userSayObject = null;
				try {
					strSessionId = log.getString(sessionId);
					userSayObject = new JSONObject();
					userSayObject.put(userSay, log.getJSONObject(result).getString(resolvedQuery));
					userSayObject.put(status_code, statusCode);
				} catch (JSONException e) {
					logger.error("JSON format is wrong", e);
				}

				// if log json with wrong format, ignore it.
				if (strSessionId.equals("") || userSayObject == null) {
					continue;
				}

				boolean sessionIdIsExist = false;
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					if (jsonObject.getString(sessionId).equals(strSessionId)) {

						jsonObject.getJSONArray("contents").put(userSayObject);
						sessionIdIsExist = true;
					}
				}
				if (!sessionIdIsExist) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put(sessionId, strSessionId);
					jsonObject.put("contents", new JSONArray().put(userSayObject));

					jsonArray.put(jsonObject);
				}
			}
		}
		FileUtils.writeFile(fileServerDao.getByName(SpringPropertiesUtil.getProperty("log_folder_path")).getUrl() + "/"
				+ atDate + "/collector" + atDate, jsonArray.toString(4));
		logger.info("[conversationCollector] : Start");
		return jsonArray;
	}

	@Override
	public List<DateDto> getListDateLog() {
		logger.info("[getListDateLog] : Start");
		if (chatLogsFolder == null) {
			chatLogsFolder = fileServerDao.getByName(SpringPropertiesUtil.getProperty("log_folder_path")).getUrl();
		}
		List<DateDto> listDateFolderName = new ArrayList<>();
		File file = new File(chatLogsFolder);
		if (!file.isDirectory()) {
			return null;
		}

		for (File dirList : file.listFiles()) {
			if (!dirList.isDirectory()) {
				continue;
			}
			String dateFolder = CommonUtils.parseDateFormat(dirList.getName());
			if (dateFolder != null) {
				DateDto date = new DateDto(dateFolder, dirList.getName());
				listDateFolderName.add(date);
			}
		}
		logger.info("[getListDateLog] : End");
		return listDateFolderName;
	}

	@Override
	public JSONObject setLogStatus(String logId, LogStatus logStatus) throws JSONException, IOException {
		logger.info("[setLogStatus] Start");
		JSONArray logs = this.getLogs().getJSONArray(LOG_JSON_FORMAT_CONTENTS);
		for (int i = 0; i < logs.length(); i++) {
			JSONObject log = logs.getJSONObject(i);
			if (log.has(id) && log.getString(id).equals(logId)) {
				log.put(status_of_log, logStatus);
				break;
			}
		}
		FileUtils.writeFile(this.getLogFilePath(), this.logJSON.toString(4));
		logger.info("[setLogStatus] End");
		return this.getLogs();
	}

	private JSONObject getConversationLog(String strSession, String filePath) throws IOException, JSONException {
		logger.info("[getConversationLog] : Start");
		List<JSONObject> logs = new ArrayList<>();
		this.parseJSONObjectFromLogFile(filePath, logs);

		JSONObject conversationLog = null;
		JSONArray contents = new JSONArray();

		for (JSONObject logJson : logs) {
			JSONObject log = logJson.getJSONObject(log_json);
			int statusCode = logJson.getInt(status_code);

			if (statusCode == SUCCESS_CODE || statusCode == NO_ENTRY_CODE || statusCode == NOT_FOUND_CODE) {
				if (log.getString(sessionId).equals(strSession)) {
					JSONObject userSayObject = null;
					try {
						userSayObject = new JSONObject();
						userSayObject.put(userSay, log.getJSONObject(result).getString(resolvedQuery));
						userSayObject.put(status_code, statusCode);
					} catch (JSONException e) {
						logger.error("JSON format is wrong", e);
					}
					if (userSayObject != null) {
						contents.put(userSayObject);
					}
				}
			}
		}
		if (contents.length() > 0) {
			conversationLog = new JSONObject();
			conversationLog.put(sessionId, strSession);
			conversationLog.put("contents", contents);
		}

		logger.info("[getConversationLog] : End");
		return conversationLog;
	}

	@Override
	public JSONArray getAllConversations(String logId) throws JSONException, IOException {
		logger.info("[getAllConversations] : Start");
		JSONArray logs = this.getLogs().getJSONArray(LOG_JSON_FORMAT_CONTENTS);

		JSONArray conversations = new JSONArray();
		for (int i = 0; i < logs.length(); i++) {
			JSONObject log = logs.getJSONObject(i);
			if (log.has(id) && log.getString(id).equals(logId)) {
				JSONArray countArr = log.getJSONArray(count);
				for (int j = 0; j < countArr.length(); j++) {
					JSONObject countObj = countArr.getJSONObject(j);

					if (!checkExistConversation(conversations, countObj.getString(sessionId))) {
						JSONObject conversation = this.getConversationLog(countObj.getString(sessionId),
								countObj.getString("filePath"));
						if (conversation != null) {
							conversations.put(conversation);
						}
					}
				}
				break;
			}
		}
		logger.info("[getAllConversations] : End");
		return conversations;
	}

	private boolean checkExistConversation(JSONArray conversations, String sessionStr) throws JSONException {
		for (int i = 0; i < conversations.length(); i++) {
			JSONObject jsonObject = conversations.getJSONObject(i);
			if (jsonObject.has(sessionId) && jsonObject.getString(sessionId).equals(sessionStr)) {
				return true;
			}
		}
		return false;
	}

	public void notifyUser(String logId, String foodName) throws JSONException, IOException {
		logger.info("[notifyUser] : Start");
		JSONArray logs = this.getLogs().getJSONArray(LOG_JSON_FORMAT_CONTENTS);
		Set<String> recipients = new LinkedHashSet<String>();
		for (int i = 0; i < logs.length(); i++) {
			JSONObject log = logs.getJSONObject(i);
			if (log.has(id) && log.getString(id).equals(logId) && log.has(count)) {
				JSONArray countArr = log.getJSONArray(count);
				for (int j = 0; j < countArr.length(); j++) {
					File file = new File(countArr.getJSONObject(j).getString("filePath"));
					recipients.add(file.getName());
				}
			}
		}
		if (!recipients.isEmpty()) {
			for (String recipient : recipients) {
				facebookFactory.sendMessage(recipient, "Món " + foodName
						+ "  bạn yêu cầu trước đó đã được xử lý. Bạn có thể thử yêu cầu lại. Ví dụ: tìm " + foodName);
			}
		}
		logger.info("[notifyUser] : End");
	}
}
