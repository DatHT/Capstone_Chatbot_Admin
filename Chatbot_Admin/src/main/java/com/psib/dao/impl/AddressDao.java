package com.psib.dao.impl;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.IAddressDao;
import com.psib.model.Address;
@Repository
public class AddressDao extends BaseDao<Address, Long> implements IAddressDao{
	public AddressDao(Class<Address> clazz) {
		super(Address.class);		
	}
	
	public AddressDao() {
		// TODO Auto-generated constructor stub
		setClazz(Address.class);
	}
	
	@Override
	public void insert(Address model) {
		// TODO Auto-generated method stub
		super.insert(model);
	}

	@Override
	public boolean checkExitsAddress(String name) {
		String sql = "select e from Address e where e.name =:name";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", name);
		Address result = (Address) query.uniqueResult();
		if(result!=null){
			return true;			
		}
		return false;
	}
	@Override
	public long getAddressIDByAddressName(String address) {
		String sql="from Address where name=:name order by Id desc";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", address);	
		Address result = (Address) query.uniqueResult();
		if(result!=null){
			return result.getId();
		}
		return 0;
	}
}
