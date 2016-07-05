package com.psib.dao.impl;

import com.psib.dao.IProductDetailDao;
import com.psib.dto.ProductDetailDto;
import com.psib.model.ProductDetail;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDetailDao extends BaseDao<ProductDetail, Long> implements IProductDetailDao {

    private static final Logger LOG = Logger.getLogger(ProductDetailDao.class);

    public ProductDetailDao(Class<ProductDetail> clazz) {
        super(clazz);
    }

    public ProductDetailDao() {
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
                .append(" OR P.restaurantName LIKE :searchPhrase")
                .append(" ORDER BY P.productId DESC"));

        Query query = getSession().createQuery(sql);
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");

        Long count = (Long) query.uniqueResult();

        LOG.info("[countBySearchPhrase] End");
        return count;
    }

    @Override
    @Transactional
    public List<ProductDetailDto> getBySearchPhraseAndSort(String searchPhrase, String sortProductName, String sortAddressName
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

        StringBuilder sql = new StringBuilder("SELECT P.productId, P.productName, P.addressName, P.rate, P.restaurantName FROM ")
                .append(ProductDetail.class.getSimpleName())
                .append(" P WHERE P.productName LIKE :searchPhrase")
                .append(" OR P.addressName LIKE :searchPhrase")
                .append(" OR P.districtName LIKE :searchPhrase")
                .append(" OR str(P.rate) LIKE :searchPhrase")
                .append(" OR P.restaurantName LIKE :searchPhrase")
                .append(" ORDER BY P.productId DESC");

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

        List<Object[]> rows = query.list();
        List<ProductDetailDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            ProductDetailDto dto = new ProductDetailDto();
            dto.setProductId(Long.parseLong(String.valueOf(row[0])));
            dto.setProductName(String.valueOf(row[1]));
            dto.setAddressName(String.valueOf(row[2]));
            dto.setRate(Double.parseDouble(String.valueOf(row[3])));
            dto.setRestaurantName(String.valueOf(row[4]));
            result.add(dto);
        }

        LOG.info("[getBySearchPhraseAndSort] End");
        return result;
    }

    @Override
    @Transactional
    public ProductDetail getById(ProductDetail productDetail) {
        LOG.info("[getById] Start: id = " + productDetail.getProductId());
        String sql = "FROM " + ProductDetail.class.getSimpleName() + " P WHERE P.productId = :productId";
        Query query = getSession().createQuery(sql);
        query.setParameter("productId", productDetail.getProductId());
        ProductDetail result = (ProductDetail) query.uniqueResult();
        LOG.info("[getById] End");
        return result;
    }

    @Override
    @Transactional
    public void insertProductDetail(ProductDetail productDetail) {
        LOG.info("[insertProductDetail] Start: productName = " + productDetail.getProductName());
        insert(productDetail);
        LOG.info("[insertProductDetail] End");
    }

    @Override
    @Transactional
    public void updateProductDetail(ProductDetail productDetail) {
        LOG.info("[updateProductDetail] Start: name = " + productDetail.getProductName());
        update(productDetail);
        LOG.info("[updateProductDetail] End");
    }

    @Override
    @Transactional
    public void deleteProductDetail(ProductDetail productDetail) {
        LOG.info("[deleteProductDetail] Start: id = " + productDetail.getProductId());
        delete(productDetail);
        LOG.info("[deleteProductDetail] End");
    }

    @Override
    @Transactional
    public void deleteById(ProductDetail productDetail) {
        LOG.info("[deleteById] Start: id = " + productDetail.getProductId());
        String sql = "DELETE FROM " + ProductDetail.class.getSimpleName() + " P WHERE P.productId = :productId";
        Query query = getSession().createQuery(sql);
        query.setParameter("productId", productDetail.getProductId());
        query.executeUpdate();
        LOG.info("[deleteById] End");
    }

    @Override
    @Transactional
    public ProductDetail checkProductExist(ProductDetail productDetail) {
        LOG.info(new StringBuilder("[checkProductExist] Start: productName = ").append(productDetail.getProductName())
                .append(" , addressName = ").append(productDetail.getAddressName()));
        String sql = String.valueOf(new StringBuilder("FROM ").append(ProductDetail.class.getSimpleName())
                .append(" P WHERE P.productName = :productName AND P.addressName = :addressName"));

        Query query = getSession().createQuery(sql);
        query.setParameter("productName", productDetail.getProductName());
        query.setParameter("addressName", productDetail.getAddressName());
        ProductDetail result = (ProductDetail) query.uniqueResult();
        if (result != null) {
            LOG.info("[checkProductExist] End");
            return result;
        }
        LOG.info("[checkProductExist] End");
        return null;
    }
}
