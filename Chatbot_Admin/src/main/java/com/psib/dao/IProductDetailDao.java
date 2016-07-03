package com.psib.dao;

import java.util.List;

import com.psib.model.ProductDetail;

public interface IProductDetailDao {

    List<ProductDetail> getAllItem();

    long countBySearchPhrase(String searchPhrase);

    List<ProductDetail> getBySearchPhraseAndSort(String searchPhrase, String sortProductName, String sortAddressName
            , String sortDistrictName, String sortRate, String sortRestaurantName
            , int maxResult, int skipResult);

    void insertProductDetail(ProductDetail productDetail);

    void updateProductDetail(ProductDetail productDetail);

    long checkProductExist(ProductDetail productDetail);
}
