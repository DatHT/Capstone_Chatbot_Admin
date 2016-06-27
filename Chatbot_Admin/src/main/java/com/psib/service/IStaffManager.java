/**
 * 
 */
package com.psib.service;

import java.util.List;

import com.psib.model.Staff;

/**
 * @author DatHT Jun 22, 2016
 * @Email: datht0601@gmail.com
 */
public interface IStaffManager {
	public List<Staff> getAllStaff();

	public Staff getStaffByUsername(String username);

	public Staff createNewStaffAccount(String username, String email, Boolean isAdmin);
	
	public void updateStaff(Staff staff);
}
