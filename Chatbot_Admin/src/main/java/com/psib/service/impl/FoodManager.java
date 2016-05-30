package com.psib.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.IBaseDao;
import com.psib.dao.IFoodDao;
import com.psib.model.Food;
import com.psib.service.IFoodManager;

@Service
public class FoodManager implements IFoodManager {

	@Autowired
	private IFoodDao foodDao;
	
	@Override
	public void insert(Food food) {
		//foodDao.insert(food);
		
	}

	@Override
	@Transactional
	public List<Food> getAll() {
		// TODO Auto-generated method stub
		return foodDao.getAllFoods();
	}

	@Override
	@Transactional
	public Food getById(long id) {
		// TODO Auto-generated method stub
		return foodDao.getFoodById(id);
	}

}
