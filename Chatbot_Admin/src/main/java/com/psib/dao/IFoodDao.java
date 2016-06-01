package com.psib.dao;

import java.util.List;

import com.psib.model.Product;

public interface IFoodDao {
	List<Product> getAllFoods();
	Product getFoodById(long id);
}
