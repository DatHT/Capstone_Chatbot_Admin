/**
 * @author: DatHT
 * Jul 24, 2016
 * @Email: datht0601@gmail.com
 */
package com.psib.dto.jsonmapper.intent;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: DatHT
 * Jul 24, 2016
 * @Email: datht0601@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultResponseDto implements Serializable {

	@JsonProperty("resolvedQuery")
	private String resolvedQuery;
	
	@JsonProperty("action")
	private String action;
	
	@JsonProperty("contexts")
	List<ContextDto> contexts;
	
	/**
	 * 
	 */
	public ResultResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public String getResolvedQuery() {
		return resolvedQuery;
	}

	public void setResolvedQuery(String resolvedQuery) {
		this.resolvedQuery = resolvedQuery;
	}

	public List<ContextDto> getContexts() {
		return contexts;
	}

	public void setContexts(List<ContextDto> contexts) {
		this.contexts = contexts;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	
}

