package com.psib.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.IAddressDao;
import com.psib.model.Address;
import com.psib.service.IAddressManager;

@Service
public class AddressManager implements IAddressManager{
	@Autowired
	private IAddressDao addressDao;

	@Override
	@Transactional
	public long insert(Address address) {
		// TODO Auto-generated method stub
		return addressDao.inserAddress(address);
	}

	@Override
	@Transactional
	public long checkExitsAddress(Address address) {
		// TODO Auto-generated method stub
		return addressDao.checkAddressExist(address);
	}

	@Override
	@Transactional
	public long getAddressIDByAddressName(String address) {
		// TODO Auto-generated method stub
		return addressDao.getAddressIDByAddressName(address);
	}
}
