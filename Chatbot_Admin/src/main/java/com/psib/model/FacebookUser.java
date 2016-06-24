package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FacebookUser")
public class FacebookUser implements Serializable {
	
	@Id
	@Column(name = "userId", nullable = false)
	private String userId;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "locale")
	private String locale;
	
	@Column(name = "gender")
	private boolean gender;
	
	@Column(name = "isBanned", columnDefinition = "Boolean default '0'")
	private boolean isBanned;
	
	@Column(name = "favorateProduct")
	private String favorateProduct;
	
	public FacebookUser() {
		// TODO Auto-generated constructor stub
	}

	


	




	public FacebookUser(String userId, String firstName, String lastName, String locale, boolean gender,
			boolean isBanned, String favorateProduct) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.locale = locale;
		this.gender = gender;
		this.isBanned = isBanned;
		this.favorateProduct = favorateProduct;
	}









	public boolean isBanned() {
		return isBanned;
	}



	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}




	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getLocale() {
		return locale;
	}




	public void setLocale(String locale) {
		this.locale = locale;
	}




	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getFavorateProduct() {
		return favorateProduct;
	}
	
	public void setFavorateProduct(String favorateProduct) {
		this.favorateProduct = favorateProduct;
	}
	

}
