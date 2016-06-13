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
public class AffectedContext implements Serializable {

	private static final long serialVersionUID = 4659265160426423792L;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("lifespan")
	private int lifespan;
	
	/**
	 * 
	 */
	public AffectedContext() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLifespan() {
		return lifespan;
	}

	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}
	
	
}
