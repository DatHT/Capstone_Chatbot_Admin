package com.psib.dao;

import com.psib.model.Product;

public interface IProductDao {
	public void insert(Product model);
	public long getProductIDByName(String name);
	public boolean checkExitsProduct(String link, String name);
}
