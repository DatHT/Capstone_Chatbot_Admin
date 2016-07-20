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
public class IntentDto implements Serializable {

	private static final long serialVersionUID = 6087038838423011178L;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("templates")
	private List<String> templates;
	
	@JsonProperty("userSays")
	private List<UserSayDto> userSays;
	
	public IntentDto() {
		// TODO Auto-generated constructor stub
	}

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


	public List<String> getTemplates() {
		return templates;
	}

	public void setTemplates(List<String> templates) {
		this.templates = templates;
	}

	public List<UserSayDto> getUserSays() {
		return userSays;
	}

	public void setUserSays(List<UserSayDto> userSays) {
		this.userSays = userSays;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
