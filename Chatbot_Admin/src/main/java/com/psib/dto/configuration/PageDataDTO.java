package com.psib.dto.configuration;

import java.io.Serializable;

public class PageDataDTO implements Serializable{
	
	private String nextPageUrl;
	private String productName;
	private String imageLink;
	
	public PageDataDTO() {
	}
	public PageDataDTO(String nextPageUrl, String productName, String imageLink) {
		super();
		this.nextPageUrl = nextPageUrl;
		this.productName = productName;
		this.imageLink = imageLink;
	}
	public String getNextPageUrl() {
		return nextPageUrl;
	}
	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	
}
