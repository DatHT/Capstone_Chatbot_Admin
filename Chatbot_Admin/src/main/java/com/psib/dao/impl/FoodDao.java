package com.psib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.psib.dao.IFoodDao;
import com.psib.model.Food;

@Repository
public class FoodDao extends BaseDao<Food, Long> implements IFoodDao {

	public FoodDao(Class<Food> clazz) {
		super(Food.class);
		

	}
	
	public FoodDao() {
		// TODO Auto-generated constructor stub
		setClazz(Food.class);
	}

	@Override
	public List<Food> getAllFoods() {
		// TODO Auto-generated method stub
		return getAll();
	}

	@Override
	public Food getFoodById(long id) {
		// TODO Auto-generated method stub
		return getById(id);
	}

}
