/**
 * 
 */
package com.psib.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.service.ILexicalCategoryManager;

/**
 * @author DatHT
 * Jun 4, 2016
 */
@Controller
public class LexicalCategoryController {
	private static final Logger logger = LoggerFactory.getLogger(LexicalCategoryController.class);
	
	@Autowired
	private ILexicalCategoryManager manager;
	
	@RequestMapping(value = "/lexical", method = RequestMethod.GET)
	public String loadLexicalCategory(Model model) {
		try {
			List<LexicalDto> lexicals = manager.getApiLexicals();
			for (LexicalDto lexicalDto : lexicals) {
				System.out.println(lexicalDto.getName());
			}
		} catch (IOException | RestfulException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "lexicalCategory";
	}
	
}
