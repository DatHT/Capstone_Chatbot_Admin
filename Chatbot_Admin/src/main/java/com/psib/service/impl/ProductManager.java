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
    public int insertProduct(String name, String address, String district, double rating, String restaurant,
                             String relatedUrl, MultipartFile file) {
        LOG.info(String.valueOf(new StringBuilder("[insertProduct] Start: name = ").append(name)
                .append("; address = ").append(address)
                .append("; district = ").append(district)
                .append("; rating = ").append(rating)
                .append("; restaurant = ").append(restaurant)
                .append("; relatedUrl = ").append(relatedUrl)
                .append("; thumbnail = ").append(file.getOriginalFilename())));

        try {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setAddressName(address);
            productDetail.setDistrictName(district);

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

            productDetail.setLatitude(latitude);
            productDetail.setLongitude(longitude);
            productDetail.setProductName(name);
            productDetail.setRate(rating);
            productDetail.setRestaurantName(restaurant);
            productDetail.setThumbPath("");

            if (!productDetailDao.checkProductExist(productDetail)) {
                Address addressObj = new Address();
                addressObj.setName(address);
                addressObj.setLatitude(latitude);
                addressObj.setLongitude(longitude);
                addressObj.setRestaurantName(restaurant);
                addressObj.setDistrictId(districtDao.getDistrictByName(district).getId());

                long addressId = addressDao.checkAddressExist(addressObj);
                if (addressId == 0) {
                    addressId = addressDao.inserAddress(addressObj);
                }

                String thumbUrl = uploadThumbnail(file);

                productDetail.setAddressId(addressId);
                productDetail.setUrlRelate(relatedUrl);
                productDetail.setSource(getSourceFromUrl(relatedUrl));
                if (thumbUrl.equals("")) {
                    thumbUrl = uploadThumbnail(file);
                }
                productDetail.setThumbPath(thumbUrl);
                productDetailDao.insertProductDetail(productDetail);

                LOG.info("[insertProduct] End");
                return 1;
            }

            LOG.info("[insertProduct] End");
            return 0;
        } catch (Exception e) {
            throw new DatabaseException("Can not insert to Database", e);
        }
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
