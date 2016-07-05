package com.psib.dao;

import java.util.List;

import com.psib.dto.ProductDetailDto;
import com.psib.model.ProductDetail;

public interface IProductDetailDao {

    List<ProductDetail> getAllItem();

    long countBySearchPhrase(String searchPhrase);

    List<ProductDetailDto> getBySearchPhraseAndSort(String searchPhrase, String sortProductName, String sortAddressName
            , String sortDistrictName, String sortRate, String sortRestaurantName
            , int maxResult, int skipResult);

    void insertProductDetail(ProductDetail productDetail);

    void updateProductDetail(ProductDetail productDetail);

    void deleteProductDetail(ProductDetail productDetail);

    void deleteById(ProductDetail productDetail);

    long checkProductExist(ProductDetail productDetail);
}
