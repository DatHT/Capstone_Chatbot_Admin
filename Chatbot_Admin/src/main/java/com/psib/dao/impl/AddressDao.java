package com.psib.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.psib.dao.IAddressDao;
import com.psib.model.Address;
import com.psib.model.ProductAddress;

@Repository
public class AddressDao extends BaseDao<Address, Long> implements IAddressDao {

	private static final Logger logger = LoggerFactory.getLogger(AddressDao.class);

	public AddressDao(Class<Address> clazz) {
		super(clazz);
	}

	public AddressDao() {
		setClazz(Address.class);
	}

	@Override
	@Transactional
	public long inserAddress(Address address) {
		logger.info("[inserAddress] Start: name = " + address.getName());
		insert(address);
		logger.info("[inserAddress] End");
		return address.getId();
	}

	@Override
	@Transactional
	public long checkAddressExist(Address address) {
		logger.info("[checkAddressExist] Start: name = " + address.getName());
		
		String sql = "FROM " + Address.class.getSimpleName() + " WHERE name = :name";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", address.getName());
		Address result = (Address) query.uniqueResult();
		if (result != null) {
			logger.info("[checkAddressExist] End");
			return result.getId();
		}
		logger.info("[checkAddressExist] End");
		return 0;
	}
}
