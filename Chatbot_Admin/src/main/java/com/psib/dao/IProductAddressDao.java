package com.psib.dao;

import java.util.List;

import com.psib.model.ProductDetail;

public interface IProductAddressDao {

    List<ProductDetail> getAllItem();

    long countBySearchPhrase(String searchPhrase);

    List<ProductDetail> getBySearchPhraseAndSort(String searchPhrase, String sortProductName, String sortAddressName
            , String sortDistrictName, String sortRate, String sortRestaurantName
            , int maxResult, int skipResult);

    void insertProductAddress(ProductDetail productAddress);

    boolean checkProductExist(ProductDetail productAddress);
}
