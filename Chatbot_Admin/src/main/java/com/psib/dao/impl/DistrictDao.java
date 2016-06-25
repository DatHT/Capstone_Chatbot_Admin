package com.psib.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.psib.dao.IDistrictDao;
import com.psib.model.District;

@Repository
public class DistrictDao extends BaseDao<District, Long> implements IDistrictDao {

	private static final Logger logger = LoggerFactory.getLogger(DistrictDao.class);

	public DistrictDao(Class<District> clazz) {
		super(clazz);
	}

	public DistrictDao() {
		setClazz(District.class);
	}

	@Override
	@Transactional
	public List<District> getAllDistrict() {
		logger.info("[getAllDistrict] Start");
		logger.info("[getAllDistrict] End");
		return getAll();
	}

	@Override
	@Transactional
	public District getDistrictById(long id) {
		// TODO Auto-generated method stub
		return getById(id);
	}

}
