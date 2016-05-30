package com.psib.dao;

import java.util.List;

import com.psib.model.Food;

public interface IFoodDao {
	List<Food> getAllFoods();
	Food getFoodById(long id);
}
