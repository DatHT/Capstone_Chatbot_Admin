package com.psib.dao.impl;

import com.psib.dao.IProductAddressDao;
import com.psib.model.ProductDetail;
import com.psib.util.SpringPropertiesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductAddressDao extends BaseDao<ProductDetail, Long> implements IProductAddressDao {

    private static final Logger LOG = Logger.getLogger(ProductAddressDao.class);

    public ProductAddressDao(Class<ProductDetail> clazz) {
        super(clazz);
    }

    public ProductAddressDao() {
        setClazz(ProductDetail.class);
    }

    @Override
    @Transactional
    public List<ProductDetail> getAllItem() {
        LOG.info("[getAllItem] Start");
        LOG.info("[getAllItem] End");
        return getAll();
    }

    @Override
    @Transactional
    public long countBySearchPhrase(String searchPhrase) {
        LOG.info("[countBySearchPhrase] Start: searchPhrase = " + searchPhrase);

        String sql = String.valueOf(new StringBuilder("SELECT COUNT(P.productId) FROM ")
                .append(ProductDetail.class.getSimpleName())
                .append(" P WHERE P.productName LIKE :searchPhrase")
                .append(" OR P.addressName LIKE :searchPhrase")
                .append(" OR P.districtName LIKE :searchPhrase")
                .append(" OR str(P.rate) LIKE :searchPhrase")
                .append(" OR P.restaurantName LIKE :searchPhrase"));

        Query query = getSession().createQuery(sql);
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");

        Long count = (Long) query.uniqueResult();

        LOG.info("[countBySearchPhrase] End");
        return count;
    }

    @Override
    @Transactional
    public List<ProductDetail> getBySearchPhraseAndSort(String searchPhrase, String sortProductName, String sortAddressName
            , String sortDistrictName, String sortRate, String sortRestaurantName
            , int maxResult, int skipResult) {
        LOG.info(new StringBuilder("[getBySearchPhraseAndSort] Start: searchPhrase = ").append(searchPhrase)
                .append(", sortProductName = ").append(sortProductName)
                .append(", sortAddressName = ").append(sortAddressName)
                .append(", sortDistrictName = ").append(sortDistrictName)
                .append(", sortRate = ").append(sortRate)
                .append(", sortRestaurantName = ").append(sortRestaurantName)
                .append(", maxResult = ").append(maxResult)
                .append(", skipResult = ").append(skipResult));

        StringBuilder sql = new StringBuilder("FROM ").append(ProductDetail.class.getSimpleName())
                .append(" P WHERE P.productName LIKE :searchPhrase")
                .append(" OR P.addressName LIKE :searchPhrase")
                .append(" OR P.districtName LIKE :searchPhrase")
                .append(" OR str(P.rate) LIKE :searchPhrase")
                .append(" OR P.restaurantName LIKE :searchPhrase");

        if (sortProductName != null) {
            if (sortProductName.equals("asc")) {
                sql.append(" ORDER BY P.productName ASC");
            } else {
                sql.append(" ORDER BY P.productName DESC");
            }
        } else if (sortAddressName != null) {
            if (sortAddressName.equals("asc")) {
                sql.append(" ORDER BY P.addressName ASC");
            } else {
                sql.append(" ORDER BY P.addressName DESC");
            }
        } else if (sortDistrictName != null) {
            if (sortDistrictName.equals("asc")) {
                sql.append(" ORDER BY P.districtName ASC");
            } else {
                sql.append(" ORDER BY P.districtName DESC");
            }
        } else if (sortRate != null) {
            if (sortRate.equals("asc")) {
                sql.append(" ORDER BY P.rate ASC");
            } else {
                sql.append(" ORDER BY P.rate DESC");
            }
        } else if (sortRestaurantName != null) {
            if (sortRestaurantName.equals("asc")) {
                sql.append(" ORDER BY P.restaurantName ASC");
            } else {
                sql.append(" ORDER BY P.restaurantName DESC");
            }
        }

        Query query = getSession().createQuery(String.valueOf(sql));
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");
        query.setFirstResult(skipResult);
        query.setMaxResults(maxResult);

        List<ProductDetail> result = query.list();

        LOG.info("[getBySearchPhraseAndSort] End");
        return result;
    }

    @Override
    @Transactional
    public void insertProductAddress(ProductDetail productAddress) {
        LOG.info("[insertProductAddress] Start: productName = " + productAddress.getProductName());
        insert(productAddress);
        LOG.info("[insertProductAddress] End");
    }

    @Override
    @Transactional
    public boolean checkProductExist(ProductDetail productAddress) {
        LOG.info("[checkProductExist] Start: productName = " + productAddress.getProductName());

        String sql = String.valueOf(new StringBuilder("FROM ").append(ProductDetail.class.getSimpleName())
                .append(" P WHERE P.productName = :productName AND P.addressName = :addressName AND P.restaurantName = :restaurantName"));

        Query query = getSession().createQuery(sql);
        query.setParameter("productName", productAddress.getProductName());
        query.setParameter("addressName", productAddress.getAddressName());
        query.setParameter("restaurantName", productAddress.getRestaurantName());
        ProductDetail result = (ProductDetail) query.uniqueResult();
        if (result != null) {
            LOG.info("[checkProductExist] End");
            return true;
        }
        LOG.info("[checkProductExist] End");
        return false;
    }
}
