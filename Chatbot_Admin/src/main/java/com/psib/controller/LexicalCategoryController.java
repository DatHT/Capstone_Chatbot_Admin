/**
 * 
 */
package com.psib.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.common.JsonParser;
import com.psib.common.restclient.RestfulException;
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
	
	
}
