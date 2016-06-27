package com.psib.service;

import com.psib.model.ProductAddress;

public interface IProductAddressManager {
	public void insert(ProductAddress model);
	boolean checkExitFoodAddress(long foodid, long addressid);
}
