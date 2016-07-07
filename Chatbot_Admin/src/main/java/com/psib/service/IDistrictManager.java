package com.psib.service;

import com.psib.model.District;

public interface IDistrictManager {
	public long insert(District model);
	public boolean checkExitDistrict(String name);
	public District getDistrictByName(String name);
}
