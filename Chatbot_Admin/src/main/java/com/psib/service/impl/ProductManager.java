package com.psib.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.IProductDao;
import com.psib.model.Product;
import com.psib.service.IProductManager;
@Service
public class ProductManager implements IProductManager{
	@Autowired
	private IProductDao productDao;
	
	@Override
	@Transactional
	public void insert(Product model) {
		// TODO Auto-generated method stub
		productDao.insert(model);
	}

	@Override
	@Transactional
	public long getProductIDByName(String name) {
		// TODO Auto-generated method stub
		return productDao.getProductIDByName(name);
	}

	@Override
	@Transactional
	public boolean checkExitsProduct(String link, String name) {
		// TODO Auto-generated method stub
		return productDao.checkExitsProduct(link, name);
	}

}
