/**
 * 
 */
package com.psib.common.factory;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.psib.common.restclient.RequestMethod;
import com.psib.common.restclient.RestResult;
import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.dto.jsonmapper.ResultDto;
import com.psib.dto.jsonmapper.StatusDto;

/**
 * @author DatHT
 * Jun 5, 2016
 * @Email: datht0601@gmail.com
 */
@Component
public class LexicalCategoryFactory extends AbstractFactory {

	public LexicalCategoryFactory() {
		super("/entities");
	}
	
	public List<LexicalCategoryDto> getLexicals() throws IOException, RestfulException {
		RestResult result = client.createInvoker(RequestMethod.GET)
				.addHeader(AUTH_KEY, AUTH_VALUE)
				.invoke();
		return responseList(result, LexicalCategoryDto.class);
	}
	
	public LexicalDto getLexicalById(String id) throws IOException, RestfulException {
		RestResult result = client.createInvoker(RequestMethod.GET)
				.addHeader(AUTH_KEY, AUTH_VALUE)
				.addRoute(id).invoke();
		return response(result, LexicalDto.class);
	}
	
	public ResultDto insertPhrase(Entry entry, String id) throws IOException, RestfulException {
		RestResult result = client.createInvoker(RequestMethod.POST)
				.addHeader(AUTH_KEY, AUTH_VALUE)
				.addRoute(id).addRoute("entries")
				.invoke(entry);
		
		return response(result, ResultDto.class);
		
	}
	
	public StatusDto insertPhrases(List<Entry> entries, String id) throws IOException, RestfulException {
		RestResult result = client.createInvoker(RequestMethod.POST)
				.addHeader(AUTH_KEY, AUTH_VALUE)
				.addRoute(id).addRoute("entries")
				.invoke(entries);
		
		return response(result, StatusDto.class);
		
	}
	
	public ResultDto deletePhrase(String name, List<String> value) throws IOException, RestfulException {
		RestResult result = client.createInvoker(RequestMethod.DELETE)
				.addHeader(AUTH_KEY, AUTH_VALUE)
				.addRoute(name).addRoute("entries")
				.invoke(value);
		
		return response(result, ResultDto.class);
	}
	
}
