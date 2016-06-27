package com.psib.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.IProductAddressDao;
import com.psib.model.ProductAddress;
@Repository
public class ProductAddressDao extends BaseDao<ProductAddress, Long> implements IProductAddressDao{
	public ProductAddressDao(Class<ProductAddress> clazz) {
		// TODO Auto-generated constructor stub
		super(ProductAddress.class);
	}
	public ProductAddressDao() {
		// TODO Auto-generated constructor stub
		setClazz(ProductAddress.class);
	}
	@Override
	public void insert(ProductAddress model) {
		// TODO Auto-generated method stub
		super.insert(model);
	}
	@Override
	public boolean checkExitFoodAddress(long foodid, long addressid) {
		// TODO Auto-generated method stub
		String sql = "from ProductAddress where productId =:productId and addressId=:addressId";
		Query query = getSession().createQuery(sql);
		query.setParameter("productId", foodid);
		query.setParameter("addressId", addressid);
		ProductAddress result = (ProductAddress) query.uniqueResult();
		if(result!=null){
			System.out.println("co product" + result.getProductId() + result.getAddressId());
			return true;			
		}
		return false;
	}
	
}
