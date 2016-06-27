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
	public void insert(Address model) {
		// TODO Auto-generated method stub
		addressDao.insert(model);
	}

	@Override
	@Transactional
	public boolean checkExitsAddress(String name) {
		// TODO Auto-generated method stub
		return addressDao.checkExitsAddress(name);
	}

	@Override
	@Transactional
	public long getAddressIDByAddressName(String address) {
		// TODO Auto-generated method stub
		return addressDao.getAddressIDByAddressName(address);
	}
}
