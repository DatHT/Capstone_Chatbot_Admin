/**
 * 
 */
package com.psib.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author DatHT
 * Jun 4, 2016
 */
@Controller
public class LexicalCategoryController {
	private static final Logger logger = LoggerFactory.getLogger(LexicalCategoryController.class);
	
	@RequestMapping(value = "/lexical", method = RequestMethod.GET)
	public String loadLexicalCategory(Model model) {
		model.addAttribute("ACTIVE", "lexical");
		return "lexicalCategory";
	}
	
}
