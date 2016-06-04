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
	
	@Column(name = "name", nullable = false, length = 512)
	private String name;
	
	@Column(name = "isBanned")
	private boolean isBanned;
	
	public FacebookUser() {
		// TODO Auto-generated constructor stub
	}

	

	public FacebookUser(String userId, String name, boolean isBanned) {
		super();
		this.userId = userId;
		this.name = name;
		this.isBanned = isBanned;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
