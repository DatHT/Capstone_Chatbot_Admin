package com.psib.dto.jsonmapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DatHT Jul 4, 2016
 * @Email: datht0601@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDto implements Serializable {

	@JsonProperty("id")
	private String id;

	@JsonProperty("status")
	private StatusDto status;

	public ResultDto() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
	}

}
