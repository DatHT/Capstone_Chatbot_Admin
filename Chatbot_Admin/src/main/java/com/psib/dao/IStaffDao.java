/**
 * 
 */
package com.psib.dao;

import com.psib.model.Staff;

/**
 * @author DatHT
 * Jun 22, 2016
 * @Email: datht0601@gmail.com
 */
public interface IStaffDao {
	public Staff getByUsername(String username);
}
