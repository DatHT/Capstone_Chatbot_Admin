/**
 * 
 */
package com.psib.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.IStaffDao;
import com.psib.model.Staff;
import com.psib.service.IStaffManager;

/**
 * @author DatHT Jun 22, 2016
 * @Email: datht0601@gmail.com
 */
@Service
public class StaffManager implements IStaffManager {

	@Autowired
	private IStaffDao staffDao;

	@Override
	public List<Staff> getAllStaff() {
		return staffDao.getAllStaff();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.service.IStaffManager#getStaffByUsername(java.lang.String)
	 */
	@Override
	@Transactional
	public Staff getStaffByUsername(String username) {
		// TODO Auto-generated method stub
		return staffDao.getByUsername(username);
	}

	@Override
	@Transactional
	public Staff createNewStaffAccount(String username, String email, Boolean isAdmin) {
		// generate password
		String password = UUID.randomUUID().toString().substring(0, 8);
		int roleId = isAdmin ? 1 : 2;

		Staff staff = new Staff();
		staff.setUsername(username);
		staff.setPassword(password);
		staff.setEmail(email);
		staff.setRoleId(roleId);

		staffDao.insertNewStaff(staff);

		return staff;
	}
}
