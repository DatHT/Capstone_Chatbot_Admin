package com.psib.dao;

import java.util.List;

import com.psib.dto.ProductDetailDto;
import com.psib.model.ProductDetail;

public interface IProductDetailDao {
    public ProductDetail getProductDetailById(long productId);

    long countBySearchPhrase(String searchPhrase);

    List<ProductDetailDto> getBySearchPhraseAndSort(String searchPhrase, String sortProductName, String sortAddressName
            , String sortDistrictName, String sortRate, String sortRestaurantName
            , int maxResult, int skipResult);

    ProductDetail getById(ProductDetail productDetail);

    void insertProductDetail(ProductDetail productDetail);

    void updateProductDetail(ProductDetail productDetail);

    void deleteById(ProductDetail productDetail);

    ProductDetail checkProductExist(ProductDetail productDetail);

    List<ProductDetail> getProductSortById(int skip, int limit);
}
