package com.psib.service;

import java.util.List;

import com.psib.model.Food;

public interface IFoodManager {

	public void insert(Food food);
	public Food getById(long id);
	public List<Food> getAll();
}
