/**
 * 
 */
package com.psib.dto.jsonmapper.intent;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DatHT
 * Jun 12, 2016
 * @Email: datht0601@gmail.com
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseIntent implements Serializable {

	
	private static final long serialVersionUID = -3399198333687420592L;

	@JsonProperty("action")
	private String action;
	
	@JsonProperty("affectedContexts")
	private List<AffectedContext> affectedContexts;
	
	@JsonProperty("parameters")
	private List<Parameter> parameters;
	
	@JsonProperty("speech")
	private List<String> speech;
	
	/**
	 * 
	 */
	public ResponseIntent() {
		// TODO Auto-generated constructor stub
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<AffectedContext> getAffectedContexts() {
		return affectedContexts;
	}

	public void setAffectedContexts(List<AffectedContext> affectedContexts) {
		this.affectedContexts = affectedContexts;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public List<String> getSpeech() {
		return speech;
	}

	public void setSpeech(List<String> speech) {
		this.speech = speech;
	}
	
	
}
