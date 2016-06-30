/**
 * 
 */
package com.psib.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.common.DatabaseException;
import com.psib.common.factory.LexicalCategoryFactory;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.StatusCode;
import com.psib.dao.ILexicalCategoryDao;
import com.psib.dao.IPhraseDao;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.dto.jsonmapper.StatusDto;
import com.psib.model.LexicalCategory;
import com.psib.model.Phrase;
import com.psib.service.ILexicalCategoryManager;

/**
 * @author DatHT
 * Jun 5, 2016
 * @Email: datht0601@gmail.com
 */
@Service
public class LexicalCategoryManager implements ILexicalCategoryManager {

	@Autowired
	private LexicalCategoryFactory factory;
	
	@Autowired
	private ILexicalCategoryDao dao;
	
	/* (non-Javadoc)
	 * @see com.psib.service.ILexicalCategoryManager#getApiLexicals()
	 */
	@Override
	public List<LexicalCategoryDto> getApiLexicals() throws IOException, RestfulException {
		// TODO Auto-generated method stub
		
		return factory.getLexicals();
	}

	/* (non-Javadoc)
	 * @see com.psib.service.ILexicalCategoryManager#getApiLexicalById(java.lang.String)
	 */
	@Override
	public LexicalDto getApiLexicalById(String id) throws IOException, RestfulException {
		// TODO Auto-generated method stub
		return factory.getLexicalById(id);
	}

	/* (non-Javadoc)
	 * @see com.psib.service.ILexicalCategoryManager#addPhrase(com.psib.dto.jsonmapper.Entry)
	 */
	@Override
	public StatusCode addPhrase(Entry entry, String id) throws IOException, RestfulException {
		StatusDto status = factory.insertPhrase(entry, id);
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



	/* (non-Javadoc)
	 * @see com.psib.service.ILexicalCategoryManager#insertLexicalToDatabase(com.psib.model.LexicalCategory)
	 */
	@Override
	public long insertLexicalToDatabase(LexicalCategory lexicalCategory) {
		try {
			return dao.insertLexical(lexicalCategory);
		}catch (Exception e) {
			throw new DatabaseException("Can not insert to database", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.psib.service.ILexicalCategoryManager#checkExistLexical(java.lang.String)
	 */
	@Override
	public String checkExistLexical(String name) {
		return dao.checkExistName(name);
	}

}
