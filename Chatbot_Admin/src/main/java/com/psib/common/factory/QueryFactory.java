/**
 * @author: DatHT
 * Jul 23, 2016
 * @Email: datht0601@gmail.com
 */
package com.psib.common.factory;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.psib.common.restclient.RequestMethod;
import com.psib.common.restclient.RestResult;
import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.intent.QueryDto;
import com.psib.dto.jsonmapper.intent.QueryResult;

/**
 * @author: DatHT
 * Jul 23, 2016
 * @Email: datht0601@gmail.com
 */
@Component
public class QueryFactory extends AbstractFactory {
	
	public QueryFactory() {
		super("/query?v=20150910");
	}
	
	public QueryResult testUserQuery(QueryDto query) throws IOException, RestfulException {
		RestResult result = client.createInvoker(RequestMethod.POST)
				.addHeader(AUTH_KEY, AUTH_VALUE).invoke(query);
		return response(result, QueryResult.class);
	}

}

