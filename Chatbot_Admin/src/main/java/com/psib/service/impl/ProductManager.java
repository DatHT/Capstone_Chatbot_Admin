package com.psib.service.impl;

import com.psib.dao.*;
import com.psib.dto.ProductAddressDto;
import com.psib.dto.ProductDto;
import com.psib.model.Address;
import com.psib.model.District;
import com.psib.model.Product;
import com.psib.model.ProductAddress;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductManager implements IProductManager {

    private static final Logger LOG = Logger.getLogger(ProductManager.class);

    @Autowired
    private IProductAddressDao productAddressDao;

    @Autowired
    private IDistrictDao districtDao;

    @Autowired
    private IAddressDao addressDao;

    @Autowired
    private IProductDao productDao;

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

        List<ProductAddress> list;
        int start = current * rowCount - rowCount;

        list = productAddressDao.getBySearchPhraseAndSort(searchPhrase
                , sortProductName, sortAddressName, sortDistrictName, sortRate, sortRestaurantName, rowCount, start);

        List<ProductAddressDto> productAddressDtoList = new ArrayList<>();
        long size = list.size();

        for (int i = 0; i < size; i++) {
            productAddressDtoList.add(new ProductAddressDto((start + i + 1), list.get(i)));
        }

        ProductDto dto = new ProductDto();
        dto.setCurrent(current);
        dto.setRowCount(rowCount);
        dto.setRows(productAddressDtoList);
        dto.setTotal(productAddressDao.countBySearchPhrase(searchPhrase));

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
        LOG.info(String.valueOf(new StringBuilder("[insertProduct] Start: name = ").append(name)
                .append("; address = ").append(address)
                .append("; district = ").append(district)
                .append("; rating = ").append(rating)
                .append("; restaurant = ").append(restaurant)
                .append("; relatedUrl = ").append(relatedUrl)
                .append("; thumbnail = ").append(file.getOriginalFilename())));

        ProductAddress productAddress = new ProductAddress();
        productAddress.setAddressName(address);
        productAddress.setDistrictName(districtDao.getDistrictById(Long.parseLong(district)).getName());

        String latLongs[] = LatitudeAndLongitudeWithPincode.getLatLongPositions(address);

        if (latLongs == null) {
            productAddress.setLatitude(0);
            productAddress.setLongitude(0);
        } else {
            productAddress.setLatitude(Double.parseDouble(latLongs[0]));
            productAddress.setLongitude(Double.parseDouble(latLongs[1]));
        }

        productAddress.setProductName(name);
        productAddress.setRate(rating);
        productAddress.setRestaurantName(restaurant);
        productAddress.setThumbPath("");

        if (!productAddressDao.checkProductExist(productAddress)) {
            Address addressObj = new Address();
            addressObj.setName(address);
            addressObj.setLatitude(0);
            addressObj.setLongitude(0);
            addressObj.setRestaurantName(restaurant);
            addressObj.setDistrictId(Long.parseLong(district));

            long addressId = addressDao.checkAddressExist(addressObj);
            if (addressId == 0) {
                addressId = addressDao.inserAddress(addressObj);
            }

            Product product = new Product();
            product.setName(name);
            product.setRate(rating);
            product.setThumbPath("");
            product.setUrlRelate(relatedUrl);

            String thumbUrl = "";

            long productId = productDao.checkProductExist(product);
            if (productId == 0) {
                thumbUrl = uploadThumbnail(file);
                product.setThumbPath(thumbUrl);
                productId = productDao.insertProduct(product);
            }

            productAddress.setProductId(productId);
            productAddress.setAddressId(addressId);
            productAddress.setUrlRelate(relatedUrl);

            if (thumbUrl.equals("")) {
                thumbUrl = uploadThumbnail(file);
            }
            productAddress.setThumbPath(thumbUrl);
            productAddressDao.insertProductAddress(productAddress);

            LOG.info("[insertProduct] End");
            return 1;
        }

        LOG.info("[insertProduct] End");
        return 0;
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
}
