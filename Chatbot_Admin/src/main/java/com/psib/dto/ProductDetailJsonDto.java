package com.psib.dto;

import com.psib.model.ProductDetail;

/**
 * Created by manlm1 on 6/28/2016.
 */
public class ProductDetailJsonDto {

    public ProductDetailJsonDto(int number, ProductDetailDto productDetailDto) {
        this.number = number;
        this.productId = productDetailDto.getProductId();
        this.productName = productDetailDto.getProductName();
        this.addressName = productDetailDto.getAddressName();
        this.rate = productDetailDto.getRate();
        this.restaurantName = productDetailDto.getRestaurantName();
    }

    private long number;

    private long productId;

    private String productName;

    private String addressName;

    private double rate;

    private String restaurantName;

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
}
