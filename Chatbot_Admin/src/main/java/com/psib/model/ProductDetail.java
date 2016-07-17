package com.psib.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ProductDetail")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetail implements Serializable {

    @Id
    @Column(name = "productId", nullable = false)
    @JsonProperty("productId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(name = "productName", nullable = false, length = 4000)
    @JsonProperty("productName")
    private String productName;

    @Column(name = "addressName", nullable = false, length = 4000)
    @JsonProperty("addressName")
    private String addressName;

    @Column(name = "districtName", nullable = false, length = 128)
    @JsonProperty("districtName")
    private String districtName;

    @Column(name = "latitude", nullable = false)
    @JsonProperty("latitude")
    private double latitude;

    @Column(name = "longitude", nullable = false)
    @JsonProperty("longitude")
    private double longitude;

    @Column(name = "numOfSearch", columnDefinition = "Integer default '0'")
    @JsonProperty("numOfSearch")
    private int numOfSearch;

    @Column(name = "rate")
    private double rate;

    @Column(name = "restaurantName")
    @JsonProperty("restaurantName")
    private String restaurantName;

    @Column(name = "thumbpath")
    @JsonProperty("thumbPath")
    private String thumbPath;

    @Column(name = "urlrelate", nullable = false, length = 500)
    @JsonProperty("urlRelate")
    private String urlRelate;

    @Column(name = "addressId", nullable = false)
    @JsonProperty("addressId")
    private long addressId;

    @Column(name = "source")
    @JsonProperty("source")
    private String source;

    @Column(name = "synonymName")
    @JsonProperty("synonymName")
    private String synonymName;

    public ProductDetail() {
        // TODO Auto-generated constructor stub
    }

    public ProductDetail(long productId, long addressId, String productName, String addressName, String urlRelate,
                         String thumbPath, double rate, int numOfSearch, double longitude, double latitude, String restaurantName,
                         String districtName, String source, String synonymName) {
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
        this.source = source;
        this.synonymName = synonymName;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSynonymName() {
        return synonymName;
    }

    public void setSynonymName(String synonymName) {
        this.synonymName = synonymName;
    }
}
