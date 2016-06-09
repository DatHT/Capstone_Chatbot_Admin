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
import com.psib.dto.jsonmapper.IntentDto;
import com.psib.service.IIntentManager;
import com.psib.service.impl.IntentManager;

/**
 * @author DatHT
 * Jun 4, 2016
 */

@Controller
public class ExampleController {

	
	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);
	
	@Autowired
	private IIntentManager manager;
	
	public static final String INTENTS = "INTENTS";
	public static final String ERROR = "ERROR";
	

	@RequestMapping(value = "/example", method = RequestMethod.GET)
	public String loadExample(Model model) {
		try {
			List<IntentDto> list = manager.getIntents();
			model.addAttribute(INTENTS, list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestfulException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "example";
	}
}
