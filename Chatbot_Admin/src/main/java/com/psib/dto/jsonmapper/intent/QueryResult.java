/**
 * @author: DatHT
 * Jul 23, 2016
 * @Email: datht0601@gmail.com
 */
package com.psib.dto.jsonmapper.intent;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: DatHT
 * Jul 23, 2016
 * @Email: datht0601@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResult implements Serializable {

	private static final long serialVersionUID = -6527875516542104890L;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("result")
	private ResultResponseDto result;
	
	
	
	public QueryResult() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ResultResponseDto getResult() {
		return result;
	}

	public void setResult(ResultResponseDto result) {
		this.result = result;
	}

	
	
}

