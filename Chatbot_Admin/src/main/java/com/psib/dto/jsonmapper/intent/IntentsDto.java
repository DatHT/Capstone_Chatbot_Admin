/**
 * 
 */
package com.psib.dto.jsonmapper.intent;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DatHT
 * Jun 9, 2016
 * @Email: datht0601@gmail.com
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntentsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8497345071035379916L;

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

	public IntentsDto(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * 
	 */
	public IntentsDto() {
		// TODO Auto-generated constructor stub
	}
}
