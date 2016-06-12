package com.psib.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psib.dto.jsonmapper.Entity;
import com.psib.dto.jsonmapper.Entry;
import com.psib.model.Product;
import com.psib.service.IFoodManager;
import com.psib.service.IForceParserManager;
import com.psib.util.CommonUtils;
import com.psib.util.JsonFileCreator;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IFoodManager manager;
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "blank";
	}
	
//	@RequestMapping(value = "/export", method = RequestMethod.GET)
//	public String export(Model model) {
//		model.addAttribute("ACTIVE", "yes");
//		return "entityList";
//	}
	
	
	@RequestMapping(value = "exportJson", method = RequestMethod.GET)
	public String exportJson() {
		List<Product> foods = manager.getAll();
		Entity eFood = new Entity();
		eFood.setName("Food");
//		Entity eStreet = new Entity();
//		eStreet.setName("Street");
		List<Entry> listEntry = new ArrayList<>();
//		for (Food food : foods) {
//			System.out.println(food.getName());
//			Entry entryFood = new Entry();
//			entryFood.setValue(food.getName());
//			entryFood.setSynonyms(Arrays.asList(CommonUtils.generateSynonym(food.getName())));
//			
//			listEntry.add(entryFood);
//		}
		
		for(Product food : foods) {
			List<String> listSynonym = Arrays.asList(CommonUtils.generateSynonym(food.getName()));
			for(String synonym : listSynonym) {
				Entry entryFood = new Entry();
				if (!CommonUtils.checkExistName(synonym, listEntry)) {
					entryFood.setValue(synonym.trim());
					List<String> listSyn = new ArrayList<>();
					listSyn.add(synonym.trim());
					entryFood.setSynonyms(listSyn);
				}
				listEntry.add(entryFood);
			}
		}
		eFood.setEntries(listEntry);
		JsonFileCreator.createFile(eFood, eFood.getName());
		return "export";
	}	
}
