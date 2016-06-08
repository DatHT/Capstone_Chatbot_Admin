/**
 * 
 */
package com.psib.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.common.factory.LexicalCategoryFactory;
import com.psib.common.restclient.RestfulException;
import com.psib.constant.StatusCode;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.dto.jsonmapper.StatusDto;
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
		case 409:
			return StatusCode.CONFLICT;
		default:
			return StatusCode.ERROR;
		}
		
	}

}