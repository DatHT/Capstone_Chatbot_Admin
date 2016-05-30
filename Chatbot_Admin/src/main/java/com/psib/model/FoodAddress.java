package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Food_Address")
public class FoodAddress implements Serializable {


	@Id
	@Column(name = "food_id", nullable = false)
	private long foodId;
	
	@Id
	@Column(name = "address_id", nullable = false)
	private long addressId;
	
	public FoodAddress() {
		// TODO Auto-generated constructor stub
	}

	public long getFoodId() {
		return foodId;
	}

	public void setFoodId(long foodId) {
		this.foodId = foodId;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	
	
}
