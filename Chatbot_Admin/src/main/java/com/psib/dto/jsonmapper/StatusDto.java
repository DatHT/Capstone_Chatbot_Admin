/**
 * 
 */
package com.psib.dto.jsonmapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DatHT
 * Jun 8, 2016
 * @Email: datht0601@gmail.com
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusDto implements Serializable {

	@JsonProperty("code")
	private int code;
	
	@JsonProperty("errorType")
	private String errorType;

	public StatusDto(int code, String errorType) {
		super();
		this.code = code;
		this.errorType = errorType;
	}
	
	/**
	 * 
	 */
	public StatusDto() {
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	
}
