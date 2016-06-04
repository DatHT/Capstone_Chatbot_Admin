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
public class DataConfigController {

	private static final Logger logger = LoggerFactory.getLogger(DataConfigController.class);

	@RequestMapping(value = "/dataConfig", method = RequestMethod.GET)
	public String loadProduct(Model model) {
		model.addAttribute("ACTIVE", "dataConfig");
		return "dataConfig";
	}
}
