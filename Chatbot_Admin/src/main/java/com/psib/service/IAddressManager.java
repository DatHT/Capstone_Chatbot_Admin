package com.psib.service;

import com.psib.model.Address;

public interface IAddressManager {
	public long insert(Address address);
	public long checkExitsAddress(Address address);
	public long getAddressIDByAddressName(String address);
}
