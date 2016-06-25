package com.psib.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.psib.dto.ProductDto;
import com.psib.model.District;
import com.psib.model.ProductAddress;

public interface IProductManager {

	List<ProductAddress> getAll();

	List<District> getAllDistrict();

	int insertProduct(String name, String address, String district, String rating, String restaurant,
			String relatedUrl, MultipartFile file);
}
