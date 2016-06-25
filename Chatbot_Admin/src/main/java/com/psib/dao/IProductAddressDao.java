package com.psib.dao;

import java.util.List;

import com.psib.model.ProductAddress;

public interface IProductAddressDao {

	List<ProductAddress> getAllItem();

	void insertProductAddress(ProductAddress productAddress);
	
	boolean checkProductExist(ProductAddress productAddress);
}
