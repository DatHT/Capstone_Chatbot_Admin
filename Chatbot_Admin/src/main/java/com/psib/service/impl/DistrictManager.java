package com.psib.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.IDistrictDao;
import com.psib.model.District;
import com.psib.service.IDistrictManager;

@Service
public class DistrictManager implements IDistrictManager{
	@Autowired
	private IDistrictDao districtDao;
	
	@Override
	@Transactional
	public void insert(District model) {
		// TODO Auto-generated method stub
		districtDao.insert(model);
	}

	@Override
	@Transactional
	public boolean checkExitDistrict(String name) {
		// TODO Auto-generated method stub
		return districtDao.checkExitDistrict(name);
	}

	@Override
	@Transactional
	public long getDistrictIDByDistrictName(String name) {
		// TODO Auto-generated method stub
		return districtDao.getDistrictIDByDistrictName(name);
	}

}
