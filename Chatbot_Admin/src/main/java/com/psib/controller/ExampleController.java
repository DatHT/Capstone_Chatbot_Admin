/**
 * 
 */
package com.psib.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.common.JsonParser;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.CodeManager;
import com.psib.constant.StatusCode;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.dto.jsonmapper.StatusDto;
import com.psib.dto.jsonmapper.intent.IntentDto;
import com.psib.dto.jsonmapper.intent.IntentsDto;
import com.psib.service.IIntentManager;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.ILogManager;
import com.psib.service.impl.IntentManager;

/**
 * @author DatHT
 * Jun 4, 2016
 */

@Controller
@RequestMapping("/example")
public class ExampleController {

	
	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);
	
	@Autowired
	private IIntentManager manager;
	
	@Autowired
	private ILexicalCategoryManager lexicalManager;
	

	@Autowired
	private ILogManager logManager;
	
	public static final String INTENTS = "INTENTS";
	public static final String ERROR = "ERROR";
	public static final String LOGS = "LOGS";
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET
			, produces = "application/json; charset=utf-8")
	public @ResponseBody String getIntent(@PathVariable("id") String id, Model model) {
		
		String responseText = "";
		try {
			responseText = manager.getIntentById(id);
			
			
		} catch (IOException | RestfulException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());
			
		}
		return responseText;
	}
	

	@RequestMapping(method = RequestMethod.GET)
	public String loadExample(Model model) {
		try {
			List<IntentsDto> list = manager.getIntents();
			model.addAttribute(INTENTS, list);
			List<LexicalCategoryDto> lexicals = lexicalManager.getApiLexicals();
			model.addAttribute(LexicalCategoryController.LEXICALS, lexicals);
			
			 JSONObject listLog = logManager.getLogJson();
			 JSONArray jsonArray = listLog.getJSONArray("contents");
			 List<String> usersays = new ArrayList<>();
			 for(int i = 0; i < jsonArray.length(); i++) {
				 JSONObject obj = (JSONObject) jsonArray.get(i);
				 if(obj.getString("errCode").equals("300")) {
					 usersays.add(obj.getString("userSay"));
				 }
			 }
			 
			 model.addAttribute(ExampleController.LOGS, usersays);
			
			return "example";
		} catch (IOException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
			
		} catch (RestfulException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		} catch (JSONException e) {
			model.addAttribute(ERROR, e.getMessage());
			return "error";
		} 
		
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.POST)
	public @ResponseBody String insertPattern(@RequestParam("pattern") String pattern,
			@RequestParam("id") String id ,Model model) {
		String responseText = "";
		System.out.println("DAT: " + pattern);
		System.out.println("ID: " + id);
		try {
			StatusCode status = manager.addPattern(pattern, id);
			switch (status) {
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
		} catch (IOException e) {
			model.addAttribute(ERROR, e.getMessage());
		} catch (RestfulException e) {
			model.addAttribute(ERROR, e.getMessage());
		}
		
		return responseText;
		
	}
}
