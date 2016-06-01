package com.psib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.psib.dao.IFoodDao;
import com.psib.model.Product;

@Repository
public class FoodDao extends BaseDao<Product, Long> implements IFoodDao {

	public FoodDao(Class<Product> clazz) {
		super(Product.class);
		

	}
	
	public FoodDao() {
		// TODO Auto-generated constructor stub
		setClazz(Product.class);
	}

	@Override
	public List<Product> getAllFoods() {
		// TODO Auto-generated method stub
		return getAll();
	}

	@Override
	public Product getFoodById(long id) {
		// TODO Auto-generated method stub
		return getById(id);
	}

}
