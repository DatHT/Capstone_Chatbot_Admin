package com.psib.dao;

import java.util.List;

import com.psib.model.ProductAddress;

public interface IProductAddressDao {

    List<ProductAddress> getAllItem();

    List<ProductAddress> getBySearchPhrase(String searchPhrase);

    void insertProductAddress(ProductAddress productAddress);

    boolean checkProductExist(ProductAddress productAddress);
}
