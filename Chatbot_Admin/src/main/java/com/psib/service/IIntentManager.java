/**
 * 
 */
package com.psib.service;

import java.io.IOException;
import java.util.List;

import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.IntentDto;

/**
 * @author DatHT
 * Jun 9, 2016
 * @Email: datht0601@gmail.com
 */
public interface IIntentManager {

	List<IntentDto> getIntents()  throws IOException, RestfulException;;
}
