/**
 * 
 */
package com.psib.common.factory;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.psib.common.JsonParser;
import com.psib.common.restclient.RequestMethod;
import com.psib.common.restclient.RestResult;
import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.StatusDto;
import com.psib.dto.jsonmapper.intent.IntentDto;
import com.psib.dto.jsonmapper.intent.IntentsDto;

/**
 * @author DatHT Jun 8, 2016
 * @Email: datht0601@gmail.com
 */

@Component
public class IntentFactory extends AbstractFactory {

	public IntentFactory() {
		super("/intents");
	}

	public List<IntentsDto> getIntents() throws IOException, RestfulException {

		RestResult result = client.createInvoker(RequestMethod.GET).addHeader(AUTH_KEY, AUTH_VALUE).invoke();

		return responseList(result, IntentsDto.class);
	}

	public String getRawIntents() throws IOException, RestfulException {

		RestResult result = client.createInvoker(RequestMethod.GET).addHeader(AUTH_KEY, AUTH_VALUE).invoke();

		return responseString(result);
	}

	public String getIntentById(String id) throws IOException, RestfulException {

		RestResult result = client.createInvoker(RequestMethod.GET).addHeader(AUTH_KEY, AUTH_VALUE).addRoute(id)
				.invoke();
		return responseString(result);
	}

	public IntentDto getIntentByIdWithJsonFormat(String id) throws IOException, RestfulException {
		RestResult result = client.createInvoker(RequestMethod.GET).addHeader(AUTH_KEY, AUTH_VALUE).addRoute(id)
				.invoke();
		return response(result, IntentDto.class);
	}

	public StatusDto insertPattern(String pattern, String id) throws IOException, RestfulException {
		Object obj = JsonParser.toObject(pattern, Object.class);

		RestResult result = client.createInvoker(RequestMethod.PUT).addHeader(AUTH_KEY, AUTH_VALUE).addRoute(id)
				.invoke(obj);

		return response(result, StatusDto.class);

	}
}
