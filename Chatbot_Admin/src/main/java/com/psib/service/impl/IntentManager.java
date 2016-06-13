/**
 * 
 */
package com.psib.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.common.factory.IntentFactory;
import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.intent.IntentDto;
import com.psib.dto.jsonmapper.intent.IntentsDto;
import com.psib.service.IIntentManager;

/**
 * @author DatHT
 * Jun 9, 2016
 * @Email: datht0601@gmail.com
 */

@Service
public class IntentManager implements IIntentManager {
	
	@Autowired
	private IntentFactory factory;

	/* (non-Javadoc)
	 * @see com.psib.service.IIntentManager#getIntents()
	 */
	@Override
	public List<IntentsDto> getIntents() throws IOException, RestfulException {
		// TODO Auto-generated method stub
		return factory.getIntents();
	}

	/* (non-Javadoc)
	 * @see com.psib.service.IIntentManager#getIntentById(java.lang.String)
	 */
	@Override
	public String getIntentById(String id) throws IOException, RestfulException {
		// TODO Auto-generated method stub
		return factory.getIntentById(id);
	}

}
