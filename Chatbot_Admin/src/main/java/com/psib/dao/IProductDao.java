package com.psib.dao;

import com.psib.model.Product;

public interface IProductDao {
	
	long insertProduct(Product product);
	
	long checkProductExist(Product product);
}
