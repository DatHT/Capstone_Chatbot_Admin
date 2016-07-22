package com.psib.dto.configuration;

import java.io.Serializable;

public class PageDetailsDTO implements Serializable{

	private String restaurantName;
	private String address;
	private String userRate;
	
	
	public PageDetailsDTO() {
	}
	public PageDetailsDTO(String restaurantName, String address) {
		super();
		this.restaurantName = restaurantName;
		this.address = address;
	}
	public PageDetailsDTO(String restaurantName, String address, String userRate) {
		super();
		this.restaurantName = restaurantName;
		this.address = address;
		this.userRate = userRate;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserRate() {
		return userRate;
	}
	public void setUserRate(String userRate) {
		this.userRate = userRate;
	}
	
	
}
