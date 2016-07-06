package com.psib.dto.jsonmapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author DatHT
 * Jul 6, 2016
 * @Email: datht0601@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainDto implements Serializable {

	private static final long serialVersionUID = 3337519900577136940L;

	@JsonProperty("isDeleted")
	private boolean delete;
	
	@JsonProperty("train")
	private String train;
	
	public TrainDto() {
	}

	

	public boolean isDelete() {
		return delete;
	}



	public void setDelete(boolean delete) {
		this.delete = delete;
	}



	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}
	
	
	
	
}
