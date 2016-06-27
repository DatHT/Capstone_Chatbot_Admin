package com.psib.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.IProductDao;
import com.psib.model.Product;
@Repository
public class ProductDao extends BaseDao<Product, Long> implements IProductDao{

	public ProductDao(Class<Product> clazz) {
		// TODO Auto-generated constructor stub
		super(Product.class);
	}
	public ProductDao() {
		// TODO Auto-generated constructor stub
		setClazz(Product.class);
	}
	@Override
	public void insert(Product model) {
		// TODO Auto-generated method stub
		super.insert(model);
	}
	@Override
	public long getProductIDByName(String name) {
		// TODO Auto-generated method stub
		String sql="from Product where name=:name ORDER BY Id DESC";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", name);	
		Product result = (Product) query.setMaxResults(1).uniqueResult();
		if(result!=null){
			return result.getId();
		}
		return 0;
	}

	@Override
	public boolean checkExitsProduct(String link, String name) {
		// TODO Auto-generated method stub
		String sql = "from Product where name =:name and urlrelate=:urlrelate";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", name);
		query.setParameter("urlrelate", link);
		Product result = (Product) query.setMaxResults(1).uniqueResult();
		if(result!=null){
			return true;			
		}
		return false;
	}

}
