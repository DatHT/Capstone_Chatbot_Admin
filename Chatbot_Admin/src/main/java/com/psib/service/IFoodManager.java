package com.psib.service;

import java.util.List;

import com.psib.model.Product;

public interface IFoodManager {

	public void insert(Product food);
	public Product getById(long id);
	public List<Product> getAll();
}
