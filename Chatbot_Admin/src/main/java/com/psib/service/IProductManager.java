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

    int insertProduct(String name, String address, String district, double rating, String restaurant,
                      String relatedUrl, MultipartFile file);
}
