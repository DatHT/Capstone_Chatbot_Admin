package com.psib.service.impl;

import com.psib.common.DatabaseException;
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

    private static final int SORT_PRODUCT_NAME_CODE = 0;
    private static final int SORT_ADDRESS_NAME_CODE = 1;
    private static final int SORT_DISTRICT_NAME_CODE = 2;
    private static final int SORT_RATE = 3;
    private static final int SORT_RESTAURANT_NAME_CODE = 4;

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

        if (searchPhrase.equals("")) {
            list = productAddressDao.getAllItem();
        } else {
            list = productAddressDao.getBySearchPhrase(searchPhrase);
        }

        if (sortProductName != null) {
            list = sortResult(list, sortProductName, SORT_PRODUCT_NAME_CODE);
        } else if (sortAddressName != null) {
            list = sortResult(list, sortAddressName, SORT_ADDRESS_NAME_CODE);
        } else if (sortDistrictName != null) {
            list = sortResult(list, sortDistrictName, SORT_DISTRICT_NAME_CODE);
        } else if (sortRate != null) {
            list = sortResult(list, sortRate, SORT_RATE);
        } else if (sortRestaurantName != null) {
            list = sortResult(list, sortRestaurantName, SORT_RESTAURANT_NAME_CODE);
        }

        List<ProductAddressDto> productAddressDtoList = new ArrayList<>();
        long size = list.size();
        int start = current * rowCount - rowCount;
        int end = current * rowCount;
        if (end > size) {
            end = Math.toIntExact(size);
        }

        for (int i = start; i < end; i++) {
            productAddressDtoList.add(new ProductAddressDto((i + 1), list.get(i)));
        }

        ProductDto dto = new ProductDto();
        dto.setCurrent(current);
        dto.setRowCount(rowCount);
        dto.setRows(productAddressDtoList);
        dto.setTotal(size);

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
                             String relatedUrl, MultipartFile file){
        LOG.info(String.valueOf(new StringBuilder("[insertProduct] Start: name = ").append(name)
                .append("; address = ").append(address)
                .append("; district = ").append(district)
                .append("; rating = ").append(rating)
                .append("; restaurant = ").append(restaurant)
                .append("; relatedUrl = ").append(relatedUrl)
                .append("; thumbnail = ").append(file.getOriginalFilename())));

        try{
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
        }catch(Exception e) {
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

    private List<ProductAddress> sortResult(List<ProductAddress> list, String sortField, int sortCode) {
        LOG.info(new StringBuilder("[sortResult] Start: list size = ").append(list.size())
                .append(" ,sortField = ").append(sortField));


        Collections.sort(list, new Comparator<ProductAddress>() {
            public int compare(ProductAddress o1, ProductAddress o2) {
                if (sortCode == SORT_PRODUCT_NAME_CODE) {
                    if (sortField.equals("asc")) {
                        return o1.getProductName().compareTo(o2.getProductName());
                    }
                    return o2.getProductName().compareTo(o1.getProductName());
                } else if (sortCode == SORT_ADDRESS_NAME_CODE) {
                    if (sortField.equals("asc")) {
                        return o1.getAddressName().compareTo(o2.getAddressName());
                    }
                    return o2.getAddressName().compareTo(o1.getAddressName());
                } else if (sortCode == SORT_DISTRICT_NAME_CODE) {
                    if (sortField.equals("asc")) {
                        return o1.getDistrictName().compareTo(o2.getDistrictName());
                    }
                    return o2.getDistrictName().compareTo(o1.getDistrictName());
                } else if (sortCode == SORT_RATE) {
                    if (sortField.equals("asc")) {
                        return o1.getRate().compareTo(o2.getRate());
                    }
                    return o2.getRate().compareTo(o1.getRate());
                } else if (sortCode == SORT_RESTAURANT_NAME_CODE) {
                    if (sortField.equals("asc")) {
                        return o1.getRestaurantName().compareTo(o2.getRestaurantName());
                    }
                    return o2.getRestaurantName().compareTo(o1.getRestaurantName());
                }
                return 0;
            }
        });

        LOG.info("[sortResult] End");
        return list;
    }

}
