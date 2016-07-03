package com.psib.service;

import java.util.List;

import com.psib.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import com.psib.model.District;

public interface IProductManager {

    ProductDto getAllForPaging(int current, int rowCount, String searchPhrase,
                               String sortProductName, String sortAddressName, String sortDistrictName,
                               String sortRate, String sortRestaurantName);

    List<District> getAllDistrict();

    District getDistrict(String districtName);

    int insertProduct(String name, String address, String district, String rating, String restaurant,
                      String relatedUrl, MultipartFile file);

    int updateProduct(String name, String address, String district, String rating, String restaurant, String relatedUrl,
                      String productId, MultipartFile file);
}
