package com.psib.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.psib.dao.IProductAddressDao;
import com.psib.model.ProductAddress;
import com.psib.model.Staff;

@Repository
public class ProductAddressDao extends BaseDao<ProductAddress, Long> implements IProductAddressDao {

	private static final Logger logger = LoggerFactory.getLogger(ProductAddressDao.class);

	public ProductAddressDao(Class<ProductAddress> clazz) {
		super(clazz);
	}

	public ProductAddressDao() {
		setClazz(ProductAddress.class);
	}

	@Override
	@Transactional
	public List<ProductAddress> getAllItem() {
		logger.info("[getAllItem] Start");
		logger.info("[getAllItem] End");
		return getAll();
	}

	@Override
	@Transactional
	public void insertProductAddress(ProductAddress productAddress) {
		logger.info("[insertProductAddress] Start: productName = " + productAddress.getProductName());
		insert(productAddress);
		logger.info("[insertProductAddress] End");
	}

	@Override
	@Transactional
	public boolean checkProductExist(ProductAddress productAddress) {
		logger.info("[checkProductExist] Start: productName = " + productAddress.getProductName());

		String sql = "FROM " + ProductAddress.class.getSimpleName()
				+ " WHERE productName = :productName AND addressName = :addressName AND restaurantName = :restaurantName";
		Query query = getSession().createQuery(sql);
		query.setParameter("productName", productAddress.getProductName());
		query.setParameter("addressName", productAddress.getAddressName());
		query.setParameter("restaurantName", productAddress.getRestaurantName());
		ProductAddress result = (ProductAddress) query.uniqueResult();
		if (result != null) {
			logger.info("[checkProductExist] End");
			return true;
		}
		logger.info("[checkProductExist] End");
		return false;
	}
}
