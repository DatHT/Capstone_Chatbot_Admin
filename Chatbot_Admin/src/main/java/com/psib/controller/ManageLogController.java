/**
 * 
 */
package com.psib.controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.service.ILexicalCategoryManager;
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
	@Autowired
	private ILexicalCategoryManager lexicalManager;

	@RequestMapping(value = "/manageLog", method = RequestMethod.GET)
	public String loadLog(Model model) {
		try {
			List<LexicalCategoryDto> lexicals = lexicalManager.getApiLexicals();
			model.addAttribute("LEXICALS", lexicals);
		} catch (IOException | RestfulException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		}
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
	@RequestMapping(value = "/addPhrase", method = RequestMethod.POST)
	public @ResponseBody boolean addPhrase(@RequestParam("listPhrase") String listPhrase) {
		try {
			return logManager.addPhrase(listPhrase);
		} catch (JSONException | IOException | RestfulException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
