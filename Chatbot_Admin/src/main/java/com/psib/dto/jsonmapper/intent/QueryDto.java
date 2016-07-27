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
 * @author: DatHT Jul 23, 2016
 * @Email: datht0601@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryDto implements Serializable {

	private static final long serialVersionUID = 7163639262029281098L;

	@JsonProperty("query")
	private List<String> query;

	@JsonProperty("lang")
	private String lang;

	@JsonProperty("contexts")
	private List<ContextDto> contexts;

	public QueryDto() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getQuery() {
		return query;
	}

	public void setQuery(List<String> query) {
		this.query = query;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public List<ContextDto> getContexts() {
		return contexts;
	}

	public void setContexts(List<ContextDto> contexts) {
		this.contexts = contexts;
	}
}
