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
import com.psib.dto.jsonmapper.LexicalDto;

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
	
	public List<LexicalDto> getLexicals() throws IOException, RestfulException {
		RestResult result = client.createInvoker(RequestMethod.GET)
				.addHeader(AUTH_KEY, AUTH_VALUE)
				.invoke();
		return responseList(result, LexicalDto.class);
	}
}
