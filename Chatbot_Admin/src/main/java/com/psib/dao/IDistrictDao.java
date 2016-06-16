package com.psib.dao;

import com.psib.model.District;

public interface IDistrictDao {
	public void insert(District model);
	public boolean checkExitDistrict(String name);
	public long getDistrictIDByDistrictName(String name);
}
