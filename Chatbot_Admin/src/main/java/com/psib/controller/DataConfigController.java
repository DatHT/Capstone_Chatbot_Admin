/**
 * 
 */
package com.psib.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.psib.constant.CodeManager;
import com.psib.constant.StatusCode;
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
		try {
			if (api.equals("yes")) {
				List<LexicalCategoryDto> lexicals = lexicalManager.getApiLexicals();
				for (LexicalCategoryDto dto : lexicals) {
					if (lexicalManager.checkExistLexical(dto.getName()) == null) {
						// sync from lexical to db
						LexicalCategory lexical = new LexicalCategory();
						lexical.setName(dto.getName());
						lexical.setLastModify(new Date());
						long id = lexicalManager.insertLexicalToDatabase(lexical);
						LexicalDto entry = lexicalManager.getApiLexicalById(String.valueOf(dto.getId()));
						for (Entry item : entry.getEntries()) {
							Phrase phrase = phraseManager.checkExist(item.getValue());
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
			// sync to api
			if (db.equals("yes")) {
				//delete phrase in api
//				LexicalDto food = lexicalManager.getApiLexicalById("bf8bb68e-dde4-4a7a-ac7c-9d4101e9aaf7");
//				List<String> values = new ArrayList<>();
//				for(Entry e : food.getEntries()) {
//					values.add(e.getValue());
//				}
//				StatusCode code1 = lexicalManager.deletePhrase(values, food.getName());
//				switch (code1) {
//				case SUCCESS:
//					logger.info("[Delete to api] success ");
//					break;
//				case ERROR:
//					logger.info("[Delete to api] error ");
//					break;
//				case CONFLICT:
//					logger.info("[Delete to api] conflit ");;
//					break;
//				}
				List<Phrase> phrases = phraseManager.getAll();
				for (int i = 0; i < phrases.size(); i++) {
					Phrase p = phrases.get(i);
					Entry entry = new Entry();
					entry.setValue(p.getName());
					List<String> synonym = new ArrayList<>();
					synonym.add(p.getName());
					entry.setSynonyms(synonym);
					StatusCode code = lexicalManager.addPhrase(entry, "bf8bb68e-dde4-4a7a-ac7c-9d4101e9aaf7");
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
					logger.info("[Sync to api] done " + i);
				}
				
			}
		} catch (IOException e) {
			responseText = e.getMessage();
		} catch (RestfulException e) {
			responseText = e.getMessage();
		}
		return responseText;
	}
}
