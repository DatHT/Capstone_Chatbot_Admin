package com.psib.dao;

import com.psib.model.Address;

public interface IAddressDao {
	void insert(Address model);
	boolean checkExitsAddress(String name);
	long getAddressIDByAddressName(String address);
}
