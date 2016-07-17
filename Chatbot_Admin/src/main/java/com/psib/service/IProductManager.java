package com.psib.service;

import com.psib.dto.BootGirdDto;
import com.psib.model.District;
import com.psib.model.ProductDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductManager {

    BootGirdDto getAllForPaging(int current, int rowCount, String searchPhrase,
                                String sortProductName, String sortAddressName, String sortDistrictName,
                                String sortRate, String sortRestaurantName);

    ProductDetail getProductById(long productId);

    List<District> getAllDistrict();

    District getDistrict(String districtName);

    int insertProduct(String name, String address, String district, String rating, String restaurant,
                      String relatedUrl, MultipartFile file);

    int updateProduct(String name, String address, String district, String rating, String restaurant, String relatedUrl,
                      String productId, MultipartFile file);

    void deleteProduct(String productId);

    List<ProductDetail> getAllProductDetail();

    void calcSynonymName();
}

