/**
 * 
 */
package com.psib.dto.jsonmapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DatHT
 * Jun 9, 2016
 * @Email: datht0601@gmail.com
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntentDto implements Serializable {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IntentDto(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * 
	 */
	public IntentDto() {
		// TODO Auto-generated constructor stub
	}
}
