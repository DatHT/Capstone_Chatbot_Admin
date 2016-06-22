/**
 * 
 */
package com.psib.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.IRoleDao;
import com.psib.model.Role;
import com.psib.service.IRoleManager;

/**
 * @author DatHT
 * Jun 22, 2016
 * @Email: datht0601@gmail.com
 */
@Service
public class RoleManager implements IRoleManager {
	
	
	@Autowired
	private IRoleDao roleDao;
	

	/* (non-Javadoc)
	 * @see com.psib.service.IRoleManager#getRoleById(long)
	 */
	@Override
	@Transactional
	public Role getRoleById(int id) {
		// TODO Auto-generated method stub
		return roleDao.getRoleById(id);
	}

}
