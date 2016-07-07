/**
 * 
 */
package com.psib.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.model.Scheduler;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.IPhraseManager;
import com.psib.service.ISchedulerManager;

/**
 * @author DatHT Jun 4, 2016
 */

@Controller
@RequestMapping("config")
public class DataConfigController {

	private static final Logger logger = LoggerFactory.getLogger(DataConfigController.class);

	public static final String ERROR = "ERROR";
	
	private static final String API = "api_sync";
	private static final String LOG = "log_sync";

	@Autowired
	private ISchedulerManager manager;

	@RequestMapping(method = RequestMethod.GET)
	public String loadDataConfig(Locale locale, Model model) {
		long d1 = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
		String formattedDate = df.format(d1);
		System.out.println("TIME-----------" + formattedDate);
		
		model.addAttribute(API, manager.getSchedularByName("api"));
		model.addAttribute(LOG, manager.getSchedularByName("log"));
		return "dataConfig";
	}

	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	public @ResponseBody String synchronize(Model model, @RequestParam("api") String api,
			@RequestParam("db") String db) {
		//
		long d1 = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
		String formattedDate = df.format(d1);
		System.out.println("TIME-----------" + formattedDate);
		//
		String responseText = "";
			if (api.equals("yes")) {
				Scheduler apiScheduler = manager.getSchedularByName("api");
				apiScheduler.setStatus(true);
				manager.updateShedulerStatus(apiScheduler);
				responseText = "done";
			}
			// sync to api
			if (db.equals("yes")) {
				Scheduler logScheduler = manager.getSchedularByName("api");
				
			}
		
		return responseText;
	}
}
