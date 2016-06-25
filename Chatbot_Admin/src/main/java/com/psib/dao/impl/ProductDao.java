package com.psib.dao.impl;

import com.psib.dao.IProductDao;
import com.psib.model.Product;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class ProductDao extends BaseDao<Product, Long> implements IProductDao {

    private static final Logger LOG = Logger.getLogger(ProductDao.class);

    @Override
    @Transactional
    public long insertProduct(Product product) {
        LOG.info("[insertProduct] Start: name = " + product.getName());
        insert(product);
        LOG.info("[insertProduct] End");
        return product.getId();
    }

    @Override
    @Transactional
    public long checkProductExist(Product product) {
        LOG.info("[checkProductExist] Start: name = " + product.getName());

        String sql = "FROM " + Product.class.getSimpleName() + " P WHERE P.name = :name AND P.urlRelate = :urlrelate";
        Query query = getSession().createQuery(sql);
        query.setParameter("name", product.getName());
        query.setParameter("urlrelate", product.getUrlRelate());
        Product result = (Product) query.uniqueResult();
        if (result != null) {
            LOG.info("[checkProductExist] End");
            return result.getId();
        }
        LOG.info("[checkProductExist] End");
        return 0;
    }
}
