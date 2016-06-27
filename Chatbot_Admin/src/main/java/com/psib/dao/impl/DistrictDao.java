package com.psib.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.IDistrictDao;
import com.psib.model.District;
@Repository
public class DistrictDao extends BaseDao<District, Long> implements IDistrictDao{
	public DistrictDao(Class<District> clazz) {
		// TODO Auto-generated constructor stub
		super(District.class);
	}
	public DistrictDao() {
		// TODO Auto-generated constructor stub
		setClazz(District.class);
	}
	@Override
	public void insert(District model) {
		// TODO Auto-generated method stub
		super.insert(model);
	}
	@Override
	public boolean checkExitDistrict(String name) {
		// TODO Auto-generated method stub
		String sql = "from District where name =:name";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", name);
		District result = (District) query.setMaxResults(1).uniqueResult();
		if(result!=null){
			return true;			
		}
		return false;
	}

	@Override
	public long getDistrictIDByDistrictName(String name) {
		// TODO Auto-generated method stub
		String sql="from District where name=:name order by ID DESC";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", name);	
		District result = (District) query.uniqueResult();
		if(result!=null){
			return result.getId();
		}
		return 0;
	}

}
