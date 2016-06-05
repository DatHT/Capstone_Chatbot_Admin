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
import com.psib.dto.jsonmapper.LexicalDto;
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
	public List<LexicalDto> getApiLexicals() throws IOException, RestfulException {
		// TODO Auto-generated method stub
		
		return factory.getLexicals();
	}

}
