package com.psib.dao;

import java.util.List;

import com.psib.model.ProductAddress;

public interface IProductAddressDao {

    List<ProductAddress> getAllItem();

    long countBySearchPhrase(String searchPhrase);

    List<ProductAddress> getBySearchPhraseAndSort(String searchPhrase, String sortProductName, String sortAddressName
            , String sortDistrictName, String sortRate, String sortRestaurantName
            , int maxResult, int skipResult);

    void insertProductAddress(ProductAddress productAddress);

    boolean checkProductExist(ProductAddress productAddress);
}
