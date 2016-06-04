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
public class ManageLogController {
	
	private static final Logger logger = LoggerFactory.getLogger(ManageLogController.class);

	@RequestMapping(value = "/manageLog", method = RequestMethod.GET)
	public String loadLog(Model model) {
		model.addAttribute("ACTIVE", "manageLog");
		return "log";
	}
	

}