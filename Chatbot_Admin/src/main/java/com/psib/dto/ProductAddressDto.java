package com.psib.dto;

import com.psib.model.ProductDetail;

/**
 * Created by manlm1 on 6/28/2016.
 */
public class ProductAddressDto {

    public ProductAddressDto(int number, ProductDetail productAddress) {
        this.number = number;
        this.productId = productAddress.getProductId();
        this.addressId = productAddress.getAddressId();
        this.productName = productAddress.getProductName();
        this.addressName = productAddress.getAddressName();
        this.urlRelate = productAddress.getUrlRelate();
        this.thumbPath = productAddress.getThumbPath();
        this.rate = productAddress.getRate();
        this.restaurantName = productAddress.getRestaurantName();
        this.districtName = productAddress.getDistrictName();
    }

    private long number;

    private long productId;

    private long addressId;

    private String productName;

    private String addressName;

    private String urlRelate;

    private String thumbPath;

    private double rate;

    private String restaurantName;

    private String districtName;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
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
