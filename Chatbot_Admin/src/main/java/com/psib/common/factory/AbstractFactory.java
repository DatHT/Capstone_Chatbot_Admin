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
	protected static final String AUTH_VALUE = "Bearer f3d71301c93646e59acbe09ec080cf14";
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
	
	protected <T> String responseString(RestResult result) throws RestfulException, IOException {
		if (result.isOk()) {
			return result.toString();
		}
		
		throw new RestfulException(result);
	}
}
