package com.psib.dao;

import java.util.List;

import com.psib.model.District;

public interface IDistrictDao {
	long inserDistrict(District district);
	
	List<District> getAllDistrict();
	
	District getDistrictById(long id);

	District getDistrictByName(String name);
	
	public long checkExitDistrict(String name);
}
