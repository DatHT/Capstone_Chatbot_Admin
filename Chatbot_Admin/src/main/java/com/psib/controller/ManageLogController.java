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
import com.psib.constant.CodeManager;
import com.psib.constant.StatusCode;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.intent.IntentsDto;
import com.psib.service.IIntentManager;
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
	@Autowired
	private IIntentManager intentManager;

	@RequestMapping(value = "/manageLog", method = RequestMethod.GET)
	public String loadLog(Model model) {
		try {
			List<IntentsDto> list = intentManager.getIntents();
			model.addAttribute(ExampleController.INTENTS, list);
			List<LexicalCategoryDto> lexicals = lexicalManager.getApiLexicals();
			model.addAttribute(LexicalCategoryController.LEXICALS, lexicals);
		} catch (IOException | RestfulException e) {
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
	public @ResponseBody String addPhrase(@RequestParam("listPhrase") String listPhrase, Model model) {
		String responseText = "";
		try {
			StatusCode code = logManager.addPhrase(listPhrase);
			switch (code) {
			case SUCCESS:
				responseText = CodeManager.SUCCESS;
				break;
			case ERROR:
				responseText = CodeManager.ERROR;
				break;
			case CONFLICT:
				responseText = CodeManager.EXISTED;
				break;
			}
		} catch (JSONException | IOException | RestfulException e) {
			e.printStackTrace();
			model.addAttribute(ERROR, e.getMessage());
		}
		return responseText;
	}

	@RequestMapping(value = "/updateLog", method = RequestMethod.GET)
	public @ResponseBody boolean updateLog() {
		try {
			logManager.updateLog();
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

}
