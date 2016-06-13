package com.psib.service;

import com.psib.model.Product;

public interface IProductManager {
	public void insert(Product model);
	public long getProductIDByName(String name);
	boolean checkExitsProduct(String link, String name);
}
