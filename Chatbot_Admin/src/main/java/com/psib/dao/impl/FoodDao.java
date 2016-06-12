package com.psib.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.IFoodDao;
import com.psib.model.Address;
import com.psib.model.District;
import com.psib.model.Product;
import com.psib.model.ProductAddress;

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
	
	@Override
	public void insert(Product model) {
		// TODO Auto-generated method stub
		super.insert(model);
	}
}
