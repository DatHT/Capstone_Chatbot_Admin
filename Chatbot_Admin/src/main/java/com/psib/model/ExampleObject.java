package com.psib.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExampleObject implements Serializable {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("address")
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ExampleObject(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	
	public ExampleObject() {
		// TODO Auto-generated constructor stub
	}
}
