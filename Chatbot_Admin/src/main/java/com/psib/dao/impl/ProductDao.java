package com.psib.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.psib.dao.IProductDao;
import com.psib.model.Address;
import com.psib.model.Product;

@Repository
public class ProductDao extends BaseDao<Product, Long> implements IProductDao {

	private static final Logger logger = LoggerFactory.getLogger(ProductDao.class);

	@Override
	@Transactional
	public long insertProduct(Product product) {
		logger.info("[insertProduct] Start: name = " + product.getName());
		insert(product);
		logger.info("[insertProduct] End");
		return product.getId();
	}

	@Override
	@Transactional
	public long checkProductExist(Product product) {
		logger.info("[checkProductExist] Start: name = " + product.getName());

		String sql = "FROM " + Product.class.getSimpleName() + " WHERE name = :name AND urlrelate = :urlrelate";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", product.getName());
		query.setParameter("urlrelate", product.getUrlRelate());
		Product result = (Product) query.uniqueResult();
		if (result != null) {
			logger.info("[checkProductExist] End");
			return result.getId();
		}
		logger.info("[checkProductExist] End");
		return 0;
	}
}
