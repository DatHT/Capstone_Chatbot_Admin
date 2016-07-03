package com.psib.service.impl;

import com.psib.common.DatabaseException;
import com.psib.dao.IAddressDao;
import com.psib.dao.IDistrictDao;
import com.psib.dao.IFileServerDao;
import com.psib.dao.IProductDetailDao;
import com.psib.dto.ProductDetailDto;
import com.psib.dto.ProductDto;
import com.psib.model.Address;
import com.psib.model.District;
import com.psib.model.ProductDetail;
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

@Service
public class ProductManager implements IProductManager {

    private static final Logger LOG = Logger.getLogger(ProductManager.class);

    @Autowired
    private IProductDetailDao productDetailDao;

    @Autowired
    private IDistrictDao districtDao;

    @Autowired
    private IAddressDao addressDao;

    @Autowired
    private IFileServerDao fileServerDao;

    @Override
    public ProductDto getAllForPaging(int current, int rowCount, String searchPhrase,
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

        List<ProductDetail> list;
        int start = current * rowCount - rowCount;

        list = productDetailDao.getBySearchPhraseAndSort(searchPhrase
                , sortProductName, sortAddressName, sortDistrictName, sortRate, sortRestaurantName, rowCount, start);

        List<ProductDetailDto> productDetailDtoList = new ArrayList<>();
        long size = list.size();

        for (int i = 0; i < size; i++) {
            productDetailDtoList.add(new ProductDetailDto((start + i + 1), list.get(i)));
        }

        ProductDto dto = new ProductDto();
        dto.setCurrent(current);
        dto.setRowCount(rowCount);
        dto.setRows(productDetailDtoList);
        dto.setTotal(productDetailDao.countBySearchPhrase(searchPhrase));

        LOG.info("[getAllForPaging] End");
        return dto;
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

            if (productDetailDao.checkProductExist(productDetail) == 0) {
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

                String thumbUrl = uploadThumbnail(file);

                productDetail.setDistrictName(district);
                productDetail.setLatitude(latitude);
                productDetail.setLongitude(longitude);
                productDetail.setRate(Double.parseDouble(rating));
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

            long tmpProductId = productDetailDao.checkProductExist(productDetail);

            if (tmpProductId != Long.parseLong(productId) && tmpProductId != 0) {
                LOG.info("[insertProduct] End");
                return 0;
            }

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

            String thumbUrl = uploadThumbnail(file);
            productDetail.setProductId(Long.parseLong(productId));
            productDetail.setDistrictName(district);
            productDetail.setLatitude(latitude);
            productDetail.setLongitude(longitude);
            productDetail.setRate(Double.parseDouble(rating));
            productDetail.setRestaurantName(restaurant);
            productDetail.setAddressId(tmpAddressId);
            productDetail.setUrlRelate(relatedUrl);
            productDetail.setSource(getSourceFromUrl(relatedUrl));
            productDetail.setThumbPath(thumbUrl);
            productDetailDao.updateProductDetail(productDetail);

            LOG.info("[insertProduct] End");
            return 1;
        } catch (Exception e) {
            LOG.error("[updateProduct] Exception: " + e.getMessage());
            throw new DatabaseException("Can not insert to Database", e);
        }
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

    private String uploadThumbnail(MultipartFile file) {
        LOG.info("[uploadThumbnail] Start: thumbnail = " + file.getOriginalFilename());

        try {
            if (!file.isEmpty()) {

                byte[] bytes;

                bytes = file.getBytes();

                String folderUrl = fileServerDao.getByName(SpringPropertiesUtil.getProperty("file_server_thumb")).getUrl();

                File dir = new File(folderUrl);

                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileType = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

                String url = String.valueOf(new StringBuilder(dir.getAbsolutePath()).append(File.separator)
                        .append(StringUtils.substringBeforeLast(file.getOriginalFilename(), ".")).append("_")
                        .append(System.currentTimeMillis()).append(".").append(fileType));

                File serverFile = new File(url);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                LOG.info("[uploadThumbnail] End");
                return url;
            }
        } catch (IOException e) {
            LOG.error("[uploadThumbnail] IOException: " + e.getMessage());
            return null;
        }

        LOG.info("[uploadThumbnail] End");
        return null;
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
