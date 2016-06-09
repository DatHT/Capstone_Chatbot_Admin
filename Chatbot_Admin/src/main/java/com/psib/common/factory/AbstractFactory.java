/**
 * 
 */
package com.psib.common.factory;

import java.io.IOException;
import java.util.List;

import org.springframework.web.client.RestClientException;

import com.psib.common.restclient.RestClient;
import com.psib.common.restclient.RestResult;
import com.psib.common.restclient.RestfulException;

/**
 * @author DatHT
 * Jun 5, 2016
 * @Email: datht0601@gmail.com
 */
public class AbstractFactory {

	protected static final String AUTH_KEY = "Authorization";
	protected static final String AUTH_VALUE = "Bearer 3f37b4cef46d45ad801a627559865999";
	protected RestClient client;
	protected static final String url = "https://api.api.ai/v1";
	
	public AbstractFactory() {
		client = new RestClient(url);
	}
	
	public AbstractFactory(String subPath) {
		String path = url + subPath;
		client = new RestClient(path);
	}
	
	protected <T> T response(RestResult result, Class<T> clazz) throws IOException, RestfulException, IOException {
		if (result.isOk()) {
			return result.parse(clazz);
		}
		throw new RestfulException(result);
	}
	
	protected <T> List<T> responseList(RestResult result, Class<T> clazz) throws IOException, RestfulException, IOException {
		if (result.isOk()) {
			return result.toList(clazz);
		}
		throw new RestfulException(result);
	}
}
