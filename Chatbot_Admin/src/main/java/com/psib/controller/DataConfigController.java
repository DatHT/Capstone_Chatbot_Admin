/**
 * 
 */
package com.psib.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.model.LexicalCategory;
import com.psib.model.Phrase;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.IPhraseManager;

/**
 * @author DatHT Jun 4, 2016
 */

@Controller
@RequestMapping("config")
public class DataConfigController {

	private static final Logger logger = LoggerFactory.getLogger(DataConfigController.class);

	public static final String ERROR = "ERROR";

	@Autowired
	private ILexicalCategoryManager lexicalManager;
	@Autowired
	private IPhraseManager phraseManager;

	@RequestMapping(method = RequestMethod.GET)
	public String loadDataConfig(Locale locale, Model model) {

		return "dataConfig";
	}
	


	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	public @ResponseBody String synchronize(Model model, @RequestParam("api") String api,
			@RequestParam("db") String db) {
		String responseText = "";
		System.out.println("DATdadasddas");
		try {
			if (api.equals("yes")) {
				List<LexicalCategoryDto> lexicals = lexicalManager.getApiLexicals();
				for (LexicalCategoryDto dto : lexicals) {
					logger.info("[Before Exist]");
					if (lexicalManager.checkExistLexical(dto.getName()) == null) {
						logger.info("[After Exist]");
						// sync from lexical to db
						LexicalCategory lexical = new LexicalCategory();
						lexical.setName(dto.getName());
						lexical.setLastModify(new Date());
						long id = lexicalManager.insertLexicalToDatabase(lexical);
						LexicalDto entry = lexicalManager.getApiLexicalById(String.valueOf(dto.getId()));
						for (Entry item : entry.getEntries()) {
							Phrase phrase =phraseManager.checkExist(item.getValue());
							if (phrase == null) {
								phrase = new Phrase();
								phrase.setAsynchronized(true);
								phrase.setLexicalId((int) id);
								phrase.setName(item.getValue());
								phraseManager.insertPhraseToDatabase(phrase);
								logger.info("[Sync done]");
							}
						}

					}
				}
				responseText = "done";
			}
		} catch (IOException e) {
			model.addAttribute(ERROR, e.getMessage());
		} catch (RestfulException e) {
			model.addAttribute(ERROR, e.getMessage());
		}
		return responseText;
	}
}
