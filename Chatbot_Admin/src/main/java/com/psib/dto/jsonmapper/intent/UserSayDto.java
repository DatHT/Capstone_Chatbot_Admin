package com.psib.dto.jsonmapper.intent;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DatHT
 * Jul 19, 2016
 * @Email: datht0601@gmail.com
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSayDto implements Serializable {

	private static final long serialVersionUID = -2317754393924133146L;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("data")
	private List<DataDto> data;
	
	@JsonProperty("isTemplate")
	private boolean isTemplate;
	
	public UserSayDto() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<DataDto> getData() {
		return data;
	}

	public void setData(List<DataDto> data) {
		this.data = data;
	}
	
	public boolean isTemplate() {
		return isTemplate;
	}
	
	
	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}
}
