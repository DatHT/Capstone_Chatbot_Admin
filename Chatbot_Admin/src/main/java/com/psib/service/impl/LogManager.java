package com.psib.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.psib.service.ILogManager;
import com.psib.util.FileUtils;

public class LogManager implements ILogManager {

	private static String START_LOG = ">>>>>";
	private static String END_LOG = "<<<<<";
	
	private static int NOT_FOUND_CODE = 404;
	private static int NO_ENTRY_CODE = 300;
	
	/**
     * Get logs from logs file.
     * @return logs List of JSON log file format {'statusCode' : code, 'log' : log}
     */
	@Override
	public List<JSONObject> getLogs() throws IOException, JSONException {
		// TODO Auto-generated method stub
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
				jsonObject.put("statusCode", logCode);
				jsonObject.put("log", log.toString());
				
				logs.add(jsonObject);
				continue;
			}
			log.append(line);
		}
		return logs;
	}
}
