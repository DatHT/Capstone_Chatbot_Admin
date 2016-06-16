package com.psib.service;

import com.psib.model.District;

public interface IDistrictManager {
	public void insert(District model);
	public boolean checkExitDistrict(String name);
	public long getDistrictIDByDistrictName(String name);
}
