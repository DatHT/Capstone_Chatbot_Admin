/**
 * 
 */
package com.psib.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.common.factory.IntentFactory;
import com.psib.common.factory.LexicalCategoryFactory;
import com.psib.common.factory.QueryFactory;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.CodeManager;
import com.psib.constant.QueryConstant;
import com.psib.constant.StatusCode;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.dto.jsonmapper.StatusDto;
import com.psib.dto.jsonmapper.intent.ContextDto;
import com.psib.dto.jsonmapper.intent.IntentDto;
import com.psib.dto.jsonmapper.intent.IntentsDto;
import com.psib.dto.jsonmapper.intent.QueryDto;
import com.psib.dto.jsonmapper.intent.QueryResult;
import com.psib.service.IIntentManager;
import com.psib.util.CommonUtils;

/**
 * @author DatHT Jun 9, 2016
 * @Email: datht0601@gmail.com
 */

@Service
public class IntentManager implements IIntentManager {

	private static final Logger LOG = Logger.getLogger(IntentManager.class);

	@Autowired
	private IntentFactory factory;

	@Autowired
	private QueryFactory queryFactory;
	
	@Autowired
    private LexicalCategoryFactory lexicalFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.service.IIntentManager#getIntents()
	 */
	@Override
	public List<IntentsDto> getIntents() throws IOException, RestfulException {
		return factory.getIntents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.service.IIntentManager#getIntentById(java.lang.String)
	 */
	@Override
	public String getIntentById(String id) throws IOException, RestfulException {
		return factory.getIntentById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.service.IIntentManager#addPattern(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public StatusCode addPattern(String pattern, String id) throws IOException, RestfulException {
		StatusDto status = factory.insertPattern(pattern, id);
		switch (status.getCode()) {
		case 200:
			return StatusCode.SUCCESS;
		case 0:
			return StatusCode.SUCCESS;
		case 409:
			return StatusCode.CONFLICT;
		default:
			return StatusCode.ERROR;
		}
	}

	@Override
	public IntentDto getIntentWithFormat(String id) throws IOException, RestfulException {
		return factory.getIntentByIdWithJsonFormat(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.service.IIntentManager#checkUserPattern(java.lang.String)
	 */
	@Override
	public boolean checkUserPattern(String pattern) throws IOException, RestfulException {
		LOG.info("[checkUserPattern] start");
		QueryDto query = new QueryDto();
		query.setLang("en");
		List<String> list = new ArrayList<>();
		list.add(pattern.trim());
		query.setQuery(list);

		List<ContextDto> ctx = new ArrayList<>();
		ContextDto cDto = new ContextDto();
		cDto.setName(QueryConstant.QUERY_CONTEXT);
		ctx.add(cDto);
		query.setContexts(ctx);
		QueryResult result = queryFactory.testUserQuery(query);
		if (result.getResult().getAction().equalsIgnoreCase(QueryConstant.QUERY_ACTION)) {
			LOG.info("[checkUserPattern] end-true");
			return true;
		}

		LOG.info("[checkUserPattern] end-false");
		return false;
	}
	
	@Override
	public String generatePattern(String sentence) throws IOException, RestfulException {
		LOG.info("[generatePattern] start");
		List<LexicalCategoryDto> listPhrase = lexicalFactory.getLexicals();
		sentence = sentence.toLowerCase();
		for(LexicalCategoryDto dto : listPhrase) {
			LexicalDto phrases = lexicalFactory.getLexicalById(dto.getId());
			for(Entry entry : phrases.getEntries()) {
				String name = entry.getValue().toLowerCase();
				if (CommonUtils.isContain(sentence, name)) {
					sentence = sentence.replace(name, "*" + dto.getName() + "," + name + "*");
				}
			}
		}
		LOG.info("[generatePattern] sentence: " + sentence);
		LOG.info("[generatePattern] end");
		return sentence;
	}
	

}
