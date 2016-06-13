package com.psib.dao;

import com.psib.model.ProductAddress;

public interface IProductAddressDao {
	public void insert(ProductAddress model);
	boolean checkExitFoodAddress(long foodid, long addressid);
}
