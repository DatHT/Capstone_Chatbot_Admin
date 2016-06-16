package com.psib.service;

import com.psib.model.Address;

public interface IAddressManager {
	public void insert(Address model);
	public boolean checkExitsAddress(String name);
	public long getAddressIDByAddressName(String address);
}
