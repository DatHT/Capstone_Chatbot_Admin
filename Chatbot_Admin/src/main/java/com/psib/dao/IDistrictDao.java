package com.psib.dao;

import java.util.List;

import com.psib.model.District;

public interface IDistrictDao {
	
	List<District> getAllDistrict();
	
	District getDistrictById(long id);
}
