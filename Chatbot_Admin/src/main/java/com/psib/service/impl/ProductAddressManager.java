package com.psib.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.IProductAddressDao;
import com.psib.model.ProductAddress;
import com.psib.service.IProductAddressManager;
@Service
public class ProductAddressManager implements IProductAddressManager{
	@Autowired
	private IProductAddressDao productaddressDao;
	
	@Override
	@Transactional
	public void insert(ProductAddress model) {
		// TODO Auto-generated method stub
		productaddressDao.insert(model);
	}

	@Override
	@Transactional
	public boolean checkExitFoodAddress(long foodid, long addressid) {
		// TODO Auto-generated method stub
		return productaddressDao.checkExitFoodAddress(foodid, addressid);
	}

}
