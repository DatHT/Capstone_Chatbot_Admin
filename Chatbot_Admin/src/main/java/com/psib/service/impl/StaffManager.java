/**
 * 
 */
package com.psib.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.IStaffDao;
import com.psib.model.Staff;
import com.psib.service.IStaffManager;

/**
 * @author DatHT
 * Jun 22, 2016
 * @Email: datht0601@gmail.com
 */
@Service
public class StaffManager implements IStaffManager {
	
	@Autowired
	private IStaffDao staffDao;

	/* (non-Javadoc)
	 * @see com.psib.service.IStaffManager#getStaffByUsername(java.lang.String)
	 */
	@Override
	@Transactional
	public Staff getStaffByUsername(String username) {
		// TODO Auto-generated method stub
		return staffDao.getByUsername(username);
	}

}
