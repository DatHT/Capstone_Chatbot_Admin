package com.psib.dao;

import com.psib.model.Address;

public interface IAddressDao {
	
	long inserAddress(Address address);
	
	long checkAddressExist(Address address);
}
