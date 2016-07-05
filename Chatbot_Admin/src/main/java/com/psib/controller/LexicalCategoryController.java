/**
 * 
 */
package com.psib.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.common.JsonParser;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.CodeManager;
import com.psib.constant.StatusCode;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.service.ILexicalCategoryManager;
import com.psib.util.JsonFileCreator;

/**
 * @author DatHT
 * Jun 4, 2016
 */
@Controller
@RequestMapping("/lexical")
public class LexicalCategoryController {
	private static final Logger logger = LoggerFactory.getLogger(LexicalCategoryController.class);
	public static final String LEXICALS = "LEXICAL";
	public static final String LEXICAL = "LEXICALID";
	public static final String ERROR = "ERROR";
	
	@Autowired
	private ILexicalCategoryManager manager;
	
	@RequestMapping(method = RequestMethod.GET)
	public String loadLexicalCategory(Model model) {
		try {
			List<LexicalCategoryDto> lexicals = manager.getApiLexicals();
			model.addAttribute(LEXICALS, lexicals);
			return "lexicalCategory";
		} catch (IOException | RestfulException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());
			return "error";
			
		}
		
	}
	
	@RequestMapping(path="/{id}" , method = RequestMethod.GET
			, produces = "application/json; charset=utf-8")
	public @ResponseBody String loadLexicalById(@PathVariable("id") String id,
			Model model, HttpSession session) {
		String responseText = "";
		try {
			LexicalDto dto = manager.getApiLexicalById(id);
			responseText = JsonParser.toJson(dto);
			
			
		} catch (IOException | RestfulException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());
			
		}
		return responseText;
		
	}
	
	@RequestMapping(path="/delete", method = RequestMethod.POST)
	public @ResponseBody String deletePhrase(@RequestParam("name") String name,
			@RequestParam("lexicalName") String lexicalName, Model model) {
		String responseText = "";
		List<String> listPhrase = new ArrayList<>();
		listPhrase.add(name);
		try {
			StatusCode code = manager.deletePhrase(listPhrase, lexicalName);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());
		} catch (RestfulException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());
		}
		return responseText;
	}
	
	@RequestMapping(path="/add", method = RequestMethod.POST)
	public @ResponseBody String insertPharse(@RequestParam("name") String name,
			@RequestParam("id") String id, Model model) {
		String responseText = "";
		Entry entry = new Entry();
		entry.setValue(name);
		List<String> synonym = new ArrayList<>();
		synonym.add(name);
		entry.setSynonyms(synonym);
		try {
			StatusCode code = manager.addPhrase(entry, id);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());
		} catch (RestfulException e) {
			// TODO Auto-generated catch block
			model.addAttribute(ERROR, e.getMessage());
		}
		
		
		return responseText;
	}
	
	
}
