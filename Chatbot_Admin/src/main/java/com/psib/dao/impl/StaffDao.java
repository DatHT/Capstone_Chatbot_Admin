/**
 * 
 */
package com.psib.dao.impl;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.IStaffDao;
import com.psib.model.Staff;

/**
 * @author DatHT
 * Jun 22, 2016
 * @Email: datht0601@gmail.com
 */
@Repository
public class StaffDao extends BaseDao<Staff, String> implements IStaffDao {

	public StaffDao(Class<Staff> clazz) {
		super(clazz);
	}
	public StaffDao() {
		setClazz(Staff.class);
	}
	
	
	/* (non-Javadoc)
	 * @see com.psib.dao.IStaffDao#getByUsername(java.lang.String)
	 */
	@Override
	@Transactional
	public Staff getByUsername(String username) {
		String sql = "from " + Staff.class.getSimpleName() + " where username = :username";
		Query query = getSession().createQuery(sql);
		query.setParameter("username", username);
		Staff staff = (Staff) query.uniqueResult();
		if (staff != null) {
			return staff;
		}
		return null;
	}

}
