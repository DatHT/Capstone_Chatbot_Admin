/**
 * 
 */
package com.psib.service;

import java.io.IOException;
import java.util.List;

import com.psib.common.restclient.RestfulException;
import com.psib.constant.StatusCode;
import com.psib.dto.jsonmapper.intent.IntentDto;
import com.psib.dto.jsonmapper.intent.IntentsDto;
import com.psib.dto.jsonmapper.intent.QueryResult;

/**
 * @author DatHT
 * Jun 9, 2016
 * @Email: datht0601@gmail.com
 */
public interface IIntentManager {

	List<IntentsDto> getIntents()  throws IOException, RestfulException;
	
	String getIntentById(String id) throws IOException, RestfulException;
	
	IntentDto getIntentWithFormat(String id) throws IOException, RestfulException;
	
	StatusCode addPattern(String pattern, String id) throws IOException, RestfulException;
	
	boolean checkUserPattern(String pattern) throws IOException, RestfulException;;
}
