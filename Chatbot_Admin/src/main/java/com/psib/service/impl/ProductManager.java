package com.psib.service.impl;

import com.psib.common.DatabaseException;
import com.psib.dao.IAddressDao;
import com.psib.dao.IDistrictDao;
import com.psib.dao.IProductDetailDao;
import com.psib.dao.ISynonymDao;
import com.psib.dto.BootGirdDto;
import com.psib.dto.ProductDetailDto;
import com.psib.dto.ProductDetailJsonDto;
import com.psib.model.Address;
import com.psib.model.District;
import com.psib.model.ProductDetail;
import com.psib.model.Synonym;
import com.psib.service.IProductManager;
import com.psib.util.LatitudeAndLongitudeWithPincode;
import com.psib.util.SpringPropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductManager implements IProductManager {

    private static final Logger LOG = Logger.getLogger(ProductManager.class);

    private static final Integer LIMIT_RESULT_PRODUCT = 1000;
    private static final Integer LIMIT_RESULT_SYNONYM = 1000;

    @Autowired
    private IProductDetailDao productDetailDao;

    @Autowired
    private IDistrictDao districtDao;

    @Autowired
    private IAddressDao addressDao;

    @Autowired
    private ISynonymDao synonymDao;

    @Override
    public BootGirdDto getAllForPaging(int current, int rowCount, String searchPhrase,
                                       String sortProductName, String sortAddressName, String sortDistrictName,
                                       String sortRate, String sortRestaurantName) {
        LOG.info(new StringBuilder("[getAllForPaging] Start: current = ").append(current)
                .append(" ,rowCount = ").append(rowCount)
                .append(" ,searchPhrase = ").append(searchPhrase)
                .append(" ,sortProductName = ").append(sortProductName)
                .append(" ,sortAddressName = ").append(sortAddressName)
                .append(" ,sortDistrictName = ").append(sortDistrictName)
                .append(" ,sortRate = ").append(sortRate)
                .append(" ,sortRestaurantName = ").append(sortRestaurantName));

        List<ProductDetailDto> list;
        int start = current * rowCount - rowCount;

        list = productDetailDao.getBySearchPhraseAndSort(searchPhrase
                , sortProductName, sortAddressName, sortDistrictName, sortRate, sortRestaurantName, rowCount, start);

        List<ProductDetailJsonDto> productDetailJsonDtoList = new ArrayList<>();
        long size = list.size();

        for (int i = 0; i < size; i++) {
            productDetailJsonDtoList.add(new ProductDetailJsonDto((start + i + 1), list.get(i)));
        }

        BootGirdDto dto = new BootGirdDto();
        dto.setCurrent(current);
        dto.setRowCount(rowCount);
        dto.setRows(productDetailJsonDtoList);
        dto.setTotal(productDetailDao.countBySearchPhrase(searchPhrase));

        LOG.info("[getAllForPaging] End");
        return dto;
    }

    @Override
    public ProductDetail getProductById(long productId) {
        LOG.info("[getProductById] Start: " + productId);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductId(productId);
        LOG.info("[getProductById] End");
        return productDetailDao.getById(productDetail);
    }

    @Override
    public List<District> getAllDistrict() {
        LOG.info("[getAllDistrict] Start");
        LOG.info("[getAllDistrict] End");
        return districtDao.getAllDistrict();
    }


    @Override
    public int insertProduct(String name, String address, String district, String rating, String restaurant,
                             String relatedUrl, MultipartFile file) {
        LOG.info(new StringBuilder("[insertProduct] Start: name = ").append(name)
                .append("; address = ").append(address)
                .append("; district = ").append(district)
                .append("; rating = ").append(rating)
                .append("; restaurant = ").append(restaurant)
                .append("; relatedUrl = ").append(relatedUrl)
                .append("; thumbnail = ").append(file.getOriginalFilename()));

        try {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProductName(name);
            productDetail.setAddressName(address);

            if (productDetailDao.checkProductExist(productDetail) == null) {
                String latLongs[] = LatitudeAndLongitudeWithPincode.getLatLongPositions(address);
                double latitude;
                double longitude;
                if (latLongs == null) {
                    latitude = 0;
                    longitude = 0;
                } else {
                    latitude = Double.parseDouble(latLongs[0]);
                    longitude = Double.parseDouble(latLongs[1]);
                }

                Address addressObj = new Address();
                addressObj.setName(address);

                long addressId = insertAddress(addressObj, latitude, longitude, restaurant, district);

                String thumbUrl = uploadThumbnail(file, "");

                productDetail.setDistrictName(district);
                productDetail.setLatitude(latitude);
                productDetail.setLongitude(longitude);
                if (!rating.equals("")) {
                    productDetail.setRate(Double.parseDouble(rating));
                }
                productDetail.setRestaurantName(restaurant);
                productDetail.setAddressId(addressId);
                productDetail.setUrlRelate(relatedUrl);
                productDetail.setSource(getSourceFromUrl(relatedUrl));
                productDetail.setThumbPath(thumbUrl);
                productDetailDao.insertProductDetail(productDetail);

                LOG.info("[insertProduct] End");
                return 1;
            }

            LOG.info("[insertProduct] End");
            return 0;
        } catch (Exception e) {
            LOG.error("[insertProduct] Exception: " + e.getMessage());
            throw new DatabaseException("Can not insert to Database", e);
        }
    }

    @Override
    public int updateProduct(String name, String address, String district, String rating, String restaurant,
                             String relatedUrl, String productId, MultipartFile file) {
        LOG.info(new StringBuilder("[updateProduct] Start: name = ").append(name)
                .append("; address = ").append(address)
                .append("; district = ").append(district)
                .append("; rating = ").append(rating)
                .append("; restaurant = ").append(restaurant)
                .append("; relatedUrl = ").append(relatedUrl)
                .append("; thumbnail = ").append(file.getOriginalFilename()));

        try {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProductName(name);
            productDetail.setAddressName(address);

            ProductDetail tmpProductDetail = productDetailDao.checkProductExist(productDetail);

            if (tmpProductDetail != null) {
                if (tmpProductDetail.getProductId() != Long.parseLong(productId)) {
                    LOG.info("[insertProduct] End");
                    return 0;
                }
            }

            productDetail.setProductId(Long.parseLong(productId));
            tmpProductDetail = productDetailDao.getById(productDetail);

            String latLongs[] = LatitudeAndLongitudeWithPincode.getLatLongPositions(address);
            double latitude;
            double longitude;
            if (latLongs == null) {
                latitude = 0;
                longitude = 0;
            } else {
                latitude = Double.parseDouble(latLongs[0]);
                longitude = Double.parseDouble(latLongs[1]);
            }

            Address addressObj = new Address();
            addressObj.setName(address);

            long tmpAddressId = insertAddress(addressObj, latitude, longitude, restaurant, district);

            String thumbUrl;

            if (tmpProductDetail.getThumbPath() == null) {
                thumbUrl = uploadThumbnail(file, "");
            } else {
                thumbUrl = uploadThumbnail(file, tmpProductDetail.getThumbPath());
            }


            productDetail.setProductId(Long.parseLong(productId));
            productDetail.setDistrictName(district);
            productDetail.setLatitude(latitude);
            productDetail.setLongitude(longitude);
            if (rating.equals("")) {
                productDetail.setRate(0);
            } else {
                productDetail.setRate(Double.parseDouble(rating));
            }
            productDetail.setRestaurantName(restaurant);
            productDetail.setAddressId(tmpAddressId);
            productDetail.setUrlRelate(relatedUrl);
            productDetail.setSource(getSourceFromUrl(relatedUrl));

            if (!thumbUrl.equals("")) {
                productDetail.setThumbPath(thumbUrl);
            } else {
                if (tmpProductDetail.getThumbPath() != null) {
                    productDetail.setThumbPath(tmpProductDetail.getThumbPath());
                } else {
                    productDetail.setThumbPath("");
                }
            }

            productDetailDao.updateProductDetail(productDetail);

            LOG.info("[insertProduct] End");
            return 1;
        } catch (Exception e) {
            LOG.error("[updateProduct] Exception: " + e.getMessage());
            throw new DatabaseException("Can not insert to Database", e);
        }
    }

    @Override
    public void deleteProduct(String productId) {
        LOG.info("[deleteProduct] Start: productId = " + productId);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductId(Long.parseLong(productId));
        productDetailDao.deleteById(productDetail);
        LOG.info("[deleteProduct] End");
    }

    @Override
    public void calcSynonymName() {
        LOG.info("[calcSynonymName] Start");
        int skipResultProduct = 0;
        int skipResultSynonym = 0;
        int productListSize = -1;
        int synonymListSize = -1;
        int replaceSynonymListSize = -1;
        List<ProductDetail> productList;
        List<Synonym> synonymList;
        List<String> replaceSynonymList = new ArrayList<>();
        ProductDetail productDetail;
        Synonym synonym;
        String productName;
        String synonymName;
        String productSynonym;
        String tmp;
        int i;
        int j;
        int k;
        List<String> list;
        List<String> list2;
        Set<String> lookup;
        int lookupSize;

        while (productListSize != 0) {
            // get product name
            productList = productDetailDao.getProductSortById(skipResultProduct, LIMIT_RESULT_PRODUCT);
            productListSize = productList.size();

            for (i = 0; i < productListSize; i++) {
                productDetail = productList.get(i);
                productName = productDetail.getProductName().toLowerCase().trim();
                productSynonym = productName + ";";
                skipResultSynonym = 0;
                synonymListSize = -1;

                while (synonymListSize != 0) {
                    // get synonym
                    synonymList = synonymDao.getSynonymNameSortById(skipResultSynonym, LIMIT_RESULT_SYNONYM);
                    synonymListSize = synonymList.size();

                    for (j = 0; j < synonymListSize; j++) {
                        synonym = synonymList.get(j);
                        synonymName = synonym.getName().toLowerCase().trim();
                        if (productName.contains(synonymName)) {
                            replaceSynonymList = synonymDao.getByIdAndSynonymId(synonym.getId(), synonym.getSynonymId());
                            replaceSynonymListSize = replaceSynonymList.size();

                            for (k = 0; k < replaceSynonymListSize; k++) {
                                tmp = productName.replace(synonymName, replaceSynonymList.get(k).trim());
                                productSynonym += tmp + ";";
                            }
                        }
                    }

                    skipResultSynonym += LIMIT_RESULT_SYNONYM;
                }

                productDetail.setSynonymName(productSynonym);
                productDetailDao.updateProductDetail(productDetail);
            }

            skipResultProduct += LIMIT_RESULT_PRODUCT;
        }
        LOG.info("[calcSynonymName] End");
    }

    private long insertAddress(Address address, double latitude, double longitude, String restaurant, String district) {
        long addressId = addressDao.checkAddressExist(address);
        if (addressId == 0) {
            address.setLatitude(latitude);
            address.setLongitude(longitude);
            address.setRestaurantName(restaurant);
            address.setDistrictId(districtDao.getDistrictByName(district).getId());
            addressId = addressDao.inserAddress(address);
        }
        return addressId;
    }

    private String uploadThumbnail(MultipartFile file, String oldThumb) {
        LOG.info("[uploadThumbnail] Start: thumbnail = " + file.getOriginalFilename());

        try {
            if (!file.isEmpty()) {

                if (!oldThumb.equals("")) {
                    oldThumb = StringUtils.substringAfter(oldThumb, "\\");
                    oldThumb = StringUtils.substringAfter(oldThumb, "\\");
                    oldThumb = SpringPropertiesUtil.getProperty("file_server_thumb") + "\\" + oldThumb;
                    File tmpFile = new File(oldThumb);
                    tmpFile.delete();
                }

                byte[] bytes;

                bytes = file.getBytes();

                String folderUrl = SpringPropertiesUtil.getProperty("file_server_thumb");

                File dir = new File(folderUrl);

                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileType = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
                String fileName = String.valueOf(new StringBuilder(StringUtils.substringBeforeLast(file.getOriginalFilename(), "."))
                        .append("_").append(System.currentTimeMillis()).append(".").append(fileType));
                String url = String.valueOf(new StringBuilder(dir.getAbsolutePath()).append(File.separator)
                        .append(fileName));

                File serverFile = new File(url);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                LOG.info("[uploadThumbnail] End");
                return SpringPropertiesUtil.getProperty("file_server_thumb_url_prefix") + fileName;
            }
        } catch (IOException e) {
            LOG.error("[uploadThumbnail] IOException: " + e.getMessage());
            return "";
        }

        LOG.info("[uploadThumbnail] End");
        return "";
    }

    @Override
    public District getDistrict(String districtName) {
        return districtDao.getDistrictByName(districtName);
    }

    private String getSourceFromUrl(String url) {
        System.out.println(url);
        String tmp1 = StringUtils.substringAfter(url, "//");
        System.out.println(tmp1);
        String tmp2 = StringUtils.substringBefore(tmp1, "/");
        System.out.println(tmp2);
        if (tmp2.equals("")) {
            return "";
        }
        return "http://" + tmp2;
    }
}
