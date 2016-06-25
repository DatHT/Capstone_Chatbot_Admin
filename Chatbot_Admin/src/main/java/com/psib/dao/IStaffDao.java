/**
 * 
 */
package com.psib.dao;

import java.util.List;

import com.psib.model.Staff;

/**
 * @author DatHT Jun 22, 2016
 * @Email: datht0601@gmail.com
 */
public interface IStaffDao {
	public List<Staff> getAllStaff();

	public Staff getByUsername(String username);

	public void insertNewStaff(Staff staff);

}
