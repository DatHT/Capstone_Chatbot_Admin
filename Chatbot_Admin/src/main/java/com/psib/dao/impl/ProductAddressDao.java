package com.psib.dao.impl;

import com.psib.dao.IProductAddressDao;
import com.psib.model.ProductAddress;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductAddressDao extends BaseDao<ProductAddress, Long> implements IProductAddressDao {

    private static final Logger LOG = Logger.getLogger(ProductAddressDao.class);

    public ProductAddressDao(Class<ProductAddress> clazz) {
        super(clazz);
    }

    public ProductAddressDao() {
        setClazz(ProductAddress.class);
    }

    @Override
    @Transactional
    public List<ProductAddress> getAllItem() {
        LOG.info("[getAllItem] Start");
        LOG.info("[getAllItem] End");
        return getAll();
    }

    @Override
    @Transactional
    public void insertProductAddress(ProductAddress productAddress) {
        LOG.info("[insertProductAddress] Start: productName = " + productAddress.getProductName());
        insert(productAddress);
        LOG.info("[insertProductAddress] End");
    }

    @Override
    @Transactional
    public boolean checkProductExist(ProductAddress productAddress) {
        LOG.info("[checkProductExist] Start: productName = " + productAddress.getProductName());

        String sql = "FROM " + ProductAddress.class.getSimpleName()
                + " P WHERE P.productName = :productName AND P.addressName = :addressName AND P.restaurantName = :restaurantName";
        Query query = getSession().createQuery(sql);
        query.setParameter("productName", productAddress.getProductName());
        query.setParameter("addressName", productAddress.getAddressName());
        query.setParameter("restaurantName", productAddress.getRestaurantName());
        ProductAddress result = (ProductAddress) query.uniqueResult();
        if (result != null) {
            LOG.info("[checkProductExist] End");
            return true;
        }
        LOG.info("[checkProductExist] End");
        return false;
    }
}
