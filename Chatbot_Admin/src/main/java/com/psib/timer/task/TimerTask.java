package com.psib.timer.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.JSONException;
import org.seleniumhq.jetty7.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.psib.common.JsonParser;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.StatusCode;
import com.psib.dao.IFileServerDao;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.dto.jsonmapper.intent.IntentsDto;
import com.psib.model.LexicalCategory;
import com.psib.model.Phrase;
import com.psib.model.ProductDetail;
import com.psib.model.Scheduler;
import com.psib.service.IIntentManager;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.ILogManager;
import com.psib.service.IPhraseManager;
import com.psib.service.IProductManager;
import com.psib.service.ISchedulerManager;
import com.psib.service.ISynonymManager;
import com.psib.util.CommonUtils;
import com.psib.util.FileUtils;
import com.psib.util.SpringPropertiesUtil;

@Component
public class TimerTask {

	private static final Logger LOG = Logger.getLogger(TimerTask.class);

	@Autowired
	private ILexicalCategoryManager lexicalManager;
	@Autowired
	private IPhraseManager phraseManager;

	@Autowired
	ISchedulerManager manager;

	@Autowired
	private IFileServerDao fileServerDao;

	@Autowired
	private IIntentManager intentManager;

	@Autowired
	private ILogManager logManager;

	@Autowired
	private IProductManager productManager;
	
	@Autowired
	private ISynonymManager synonymManager;

	public void startTimerForApiAndLog() {
		//sync phrase from api to db
		synchronizePhraseFromAPItoDB();
		
		//sync intent to file
		synchronizeIntentToBD();
		
		//sync log
		synchronizeLog();
		//insertStreetToPhrase();
		//synchronizeFromDBToAPI();
		
		//sync synonym to API and DB
		productManager.calcSynonymName();
		synchronizeSynonymToAPI();
		

	}
	
	private List<String> generateSynonym(String name) {
		String temp = synonymManager.calcSynonym(name);
		return new ArrayList<String>(Arrays.asList(temp.split(";")));
	}
	
	private void synchronizeSynonymToAPI() {
		Scheduler synonymScheduler = manager.getSchedularByName("synonym");
		if (synonymScheduler.isStatus()) {
			try {
				LOG.info("[doTimer] start update Phrase Food");
				String id = "";
				List<LexicalCategoryDto> lexicals = lexicalManager.getApiLexicals();
				for(LexicalCategoryDto lexical : lexicals) {
					if (lexical.getName().equalsIgnoreCase("Food")) {
						id = lexical.getId();
						break;
					}
				}
				LexicalDto apiLexicalFood = lexicalManager.getApiLexicalById(id);
				for(Entry entry : apiLexicalFood.getEntries()) {
					String entryName = entry.getValue();
					
					List<String> synonyms = generateSynonym(entryName);
					entry.setSynonyms(synonyms);
				}
				StatusCode status = lexicalManager.updatePhrase(apiLexicalFood, id);
				LOG.info("[doTimer] end update Phrase Food-" + status.name());
			} catch (IOException e) {
				LOG.info("[doTimer] getAll Lexicals-" + e.getMessage());
			} catch (RestfulException e) {
				LOG.info("[doTimer] getAll Lexicals-" + e.getMessage());
			}
		}
		
	}

	private void insertStreetToPhrase() {
		List<ProductDetail> list = productManager.getAllProductDetail();
		List<String> addresses = new ArrayList<>();
		for (ProductDetail productDetail : list) {
			addresses.add(productDetail.getAddressName());
		}
		List<String> result = CommonUtils.splitAddresses(addresses);
		int count = 1;
		for (String string : result) {
			LOG.info("[doTimer] insert Phrase: " + count + "-" + string);
			if (phraseManager.checkExist(string) == null) {
				int id = lexicalManager.checkExistLexical("Location");
				if (id != -1) {
					Phrase phrase = new Phrase();
					phrase.setAsynchronized(false);
					phrase.setLexicalId(id);
					phrase.setName(string);
					phraseManager.insertPhraseToDatabase(phrase);
					LOG.info("[doTimer] insert Success Phrase: " + count + "-" + string);
				}

			}
			count++;
		}

	}

	private void synchronizeLog() {
		LOG.info("[doTimer] Start - Syn Log");
		Scheduler apiScheduler = manager.getSchedularByName("log");
		if (apiScheduler.isStatus()) {
			try {
				logManager.updateLog();
				LOG.info("[doTimer] End - Syn Log");
			} catch (JSONException e) {
				LOG.info("[doTimer] log-error-" + e.getMessage());

			} catch (IOException e) {
				LOG.info("[doTimer] log-error-" + e.getMessage());
			}
		}
	}

	private void synchronizeIntentToBD() {
		LOG.info("[doTimer] Start - Syn Intent");
		Scheduler apiScheduler = manager.getSchedularByName("api");
		if (apiScheduler.isStatus()) {
			String folderUrl = fileServerDao.getByName(SpringPropertiesUtil.getProperty("file_server_intent")).getUrl();
			try {
				List<IntentsDto> listIntent = intentManager.getIntents();
				for (IntentsDto dto : listIntent) {
					String content = intentManager.getIntentById(dto.getId());
					FileUtils.writleFile(folderUrl + "/" + dto.getName() + ".json", content);
					LOG.info("[doTimer] Write file sucess");
				}

				LOG.info("[doTimer] End - Syn Intent");
			} catch (IOException e) {
				LOG.error("[Sync error] " + e.getMessage());
			} catch (RestfulException e) {
				LOG.error("[Sync error] " + e.getMessage());
			}
		}
	}

	private void synchronizePhraseFromAPItoDB() {
		LOG.info("[doTimer] Start - Syn Phrase");
		Scheduler apiScheduler = manager.getSchedularByName("api");
		if (apiScheduler.isStatus()) {
			try {
				List<LexicalCategoryDto> lexicals = lexicalManager.getApiLexicals();
				for (LexicalCategoryDto dto : lexicals) {
					long id = 0;
					if ((id = lexicalManager.checkExistLexical(dto.getName())) == -1) {
						// sync from lexical to db
						LexicalCategory lexical = new LexicalCategory();
						lexical.setName(dto.getName());
						lexical.setLastModify(new Date());
						id = lexicalManager.insertLexicalToDatabase(lexical);
						LOG.info("[Sync lexical phrase done]");
					}
					LexicalDto entry = lexicalManager.getApiLexicalById(String.valueOf(dto.getId()));
					for (Entry item : entry.getEntries()) {
						Phrase phrase = phraseManager.checkExist(item.getValue());
						if (phrase == null) {
							phrase = new Phrase();
							phrase.setAsynchronized(true);
							phrase.setLexicalId((int) id);
							phrase.setName(item.getValue());
							phraseManager.insertPhraseToDatabase(phrase);
							LOG.info("[Sync insert phrase done]");
						}
					}
				}
				LOG.info("[doTimer] End  - Syn Phrase");
			} catch (IOException e) {
				LOG.error("[Sync error] " + e.getMessage());
			} catch (RestfulException e) {
				LOG.error("[Sync error] " + e.getMessage());
			}
		}
	}

	// Sync Location to API
	public void synchronizeFromDBToAPI() {
		try {
			List<Phrase> phrases = phraseManager.getAll();
			for (int i = 0; i < phrases.size(); i++) {
				Phrase p = phrases.get(i);
				if (!p.isAsynchronized()) {
					LOG.info("[doTimer] start sync location to api");
					Entry entry = new Entry();
					entry.setValue(p.getName());
					List<String> synonym = new ArrayList<>();
					synonym.addAll(Arrays.asList(CommonUtils.generateSynonym(p.getName())));
					entry.setSynonyms(synonym);
					StatusCode code = lexicalManager.addPhrase(entry, "b4941838-ac17-4777-b870-36b0a21a6eeb");
					switch (code) {
					case SUCCESS:
						LOG.info("[Sync to api] success ");
						p.setAsynchronized(true);
						phraseManager.updatePhrase(p);
						break;
					case ERROR:
						LOG.info("[Sync to api] error ");
						break;
					case CONFLICT:
						LOG.info("[Sync to api] conflict ");
						break;
					}
					LOG.info("[Sync to api] done " + i);
				}

			}
		} catch (IOException e) {
			LOG.error("[Sync error] " + e.getMessage());
		} catch (RestfulException e) {
			LOG.error("[Sync error] " + e.getMessage());
		}
	}

	// TODO: use for use again in delete function
	// delete phrase in api
	// LexicalDto food =
	// lexicalManager.getApiLexicalById("bf8bb68e-dde4-4a7a-ac7c-9d4101e9aaf7");
	// List<String> values = new ArrayList<>();
	// for(Entry e : food.getEntries()) {
	// values.add(e.getValue());
	// }
	// StatusCode code1 = lexicalManager.deletePhrase(values, food.getName());
	// switch (code1) {
	// case SUCCESS:
	// logger.info("[Delete to api] success ");
	// break;
	// case ERROR:
	// logger.info("[Delete to api] error ");
	// break;
	// case CONFLICT:
	// logger.info("[Delete to api] conflit ");;
	// break;
	// }
}
