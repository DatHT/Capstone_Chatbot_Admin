/**
 * 
 */
package com.psib.dao.impl;

import org.springframework.stereotype.Repository;

import com.psib.dao.IRoleDao;
import com.psib.model.Role;

/**
 * @author DatHT Jun 22, 2016
 * @Email: datht0601@gmail.com
 */
@Repository
public class RoleDao extends BaseDao<Role, Integer> implements IRoleDao {

	public RoleDao(Class<Role> clazz) {
		super(clazz);
	}

	public RoleDao() {
		setClazz(Role.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.dao.IRoleDao#getRoleById(long)
	 */
	@Override
	public Role getRoleById(int id) {
		// TODO Auto-generated method stub
		return getById(id);
	}

}
