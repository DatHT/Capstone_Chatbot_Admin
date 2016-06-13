/**
 * 
 */
package com.psib.dto.jsonmapper.intent;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DatHT
 * Jun 12, 2016
 * @Email: datht0601@gmail.com
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameter implements Serializable {
	
	private static final long serialVersionUID = -1376863095817663180L;

	@JsonProperty("dataType")
	private String dataType;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("value")
	private String value;
	
	public Parameter() {
		// TODO Auto-generated constructor stub
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
