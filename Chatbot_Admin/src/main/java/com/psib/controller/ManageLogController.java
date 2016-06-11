/**
 * 
 */
package com.psib.controller;

import java.io.IOException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.service.ILogManager;

/**
 * @author DatHT Jun 4, 2016
 */
@Controller
public class ManageLogController {

	private static final Logger logger = LoggerFactory.getLogger(ManageLogController.class);

	public static final String ERROR = "ERROR";

	@Autowired
	private ILogManager logManager;

	@RequestMapping(value = "/manageLog", method = RequestMethod.GET)
	public String loadLog(Model model) {
		model.addAttribute("ACTIVE", "manageLog");
		return "log";
	}

	@RequestMapping(value = "/getLog", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getLog(Model model) {
		String response = "";

		try {
			response = logManager.getLogJson().toString();
		} catch (JSONException | IOException e) {
			model.addAttribute(ERROR, e.getMessage());
			e.printStackTrace();
			logger.error(e.toString());
		}

		return response;
	}
	
	@RequestMapping(value = "/updateLog", method = RequestMethod.GET)
	public void updateLog() {
		try {
			logManager.updateLog();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
