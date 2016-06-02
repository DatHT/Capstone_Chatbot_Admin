package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Product_Address")
public class ProductAddress implements Serializable {

	@Id
	@Column(name = "productId", nullable = false)
	private long productId;

	@Id
	@Column(name = "addressId", nullable = false)
	private long addressId;

	@Column(name = "productName", nullable = false, length = 4000)
	private String productName;

	@Column(name = "addressName", nullable = false, length = 4000)
	private String addressName;

	@Column(name = "urlrelate", nullable = false, length = 500)
	private String urlRelate;

	@Column(name = "thumbpath")
	private String thumbPath;

	@Column(name = "rate")
	private String rate;

	@Column(name = "numOfSearch", columnDefinition = "Integer default '0'")
	private int numOfSearch;

	@Column(name = "longitude", nullable = false)
	private double longitude;

	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "restaurantName")
	private String restaurantName;

	@Column(name = "districtName", nullable = false, length = 128)
	private String districtName;

	public ProductAddress() {
		// TODO Auto-generated constructor stub
	}

	public ProductAddress(long productId, long addressId, String productName, String addressName, String urlRelate,
			String thumbPath, String rate, int numOfSearch, double longitude, double latitude, String restaurantName,
			String districtName) {
		super();
		this.productId = productId;
		this.addressId = addressId;
		this.productName = productName;
		this.addressName = addressName;
		this.urlRelate = urlRelate;
		this.thumbPath = thumbPath;
		this.rate = rate;
		this.numOfSearch = numOfSearch;
		this.longitude = longitude;
		this.latitude = latitude;
		this.restaurantName = restaurantName;
		this.districtName = districtName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUrlRelate() {
		return urlRelate;
	}

	public void setUrlRelate(String urlRelate) {
		this.urlRelate = urlRelate;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public int getNumOfSearch() {
		return numOfSearch;
	}

	public void setNumOfSearch(int numOfSearch) {
		this.numOfSearch = numOfSearch;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

}
