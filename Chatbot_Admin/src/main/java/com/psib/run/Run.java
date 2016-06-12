package com.psib.run;

import java.io.IOException;

import org.json.JSONException;

import com.psib.service.impl.LogManager;
import com.psib.util.JsonFileCreator;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ExampleObject ex = new ExampleObject();
//		ex.setName("dat");
//		ex.setAddress("123");
		//JsonFileCreator.createFile(ex);
		LogManager logManager = new LogManager();
		try {
			logManager.updateLog();
			System.out.println(logManager.getLogJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
