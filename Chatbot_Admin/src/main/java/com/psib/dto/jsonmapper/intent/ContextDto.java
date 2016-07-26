/**
 * @author: DatHT
 * Jul 23, 2016
 * @Email: datht0601@gmail.com
 */
package com.psib.dto.jsonmapper.intent;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: DatHT
 * Jul 23, 2016
 * @Email: datht0601@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContextDto implements Serializable {
	private static final long serialVersionUID = -939754103931533388L;
	@JsonProperty("name")
	private String name;
	
	public ContextDto() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

