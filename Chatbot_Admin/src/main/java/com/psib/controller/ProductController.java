/**
 * 
 */
package com.psib.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.psib.model.District;
import com.psib.model.ProductAddress;
import com.psib.service.IProductManager;

/**
 * @author DatHT Jun 4, 2016
 */
@Controller
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private IProductManager productManager;

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ModelAndView loadProduct(@ModelAttribute(value = "addResult") String addResult,
			@ModelAttribute(value = "name") String name, @ModelAttribute(value = "address") String address,
			@ModelAttribute(value = "district") String district, @ModelAttribute(value = "rating") String rating,
			@ModelAttribute(value = "restaurant") String restaurant,
			@ModelAttribute(value = "relatedUrl") String relatedUrl) {
		logger.info("[loadProduct] Start");

		ModelAndView model = new ModelAndView("product");
		List<ProductAddress> productList = productManager.getAll();
		List<District> districtList = productManager.getAllDistrict();
		Collections.sort(districtList);

		model.addObject("productList", productList);
		model.addObject("districtList", districtList);
		if (addResult.equals("")) {
			model.addObject("addResult", "1st load");
		} else {
			model.addObject(addResult);
		}
		model.addObject(name);
		model.addObject(address);
		model.addObject("districtId", district);
		model.addObject(rating);
		model.addObject(restaurant);
		model.addObject(relatedUrl);

		logger.info("[loadProduct] End");
		return model;
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView addProduct(@RequestParam(name = "name") String name,
			@RequestParam(name = "address") String address, @RequestParam(name = "district") String district,
			@RequestParam(name = "rating") String rating, @RequestParam(name = "restaurant") String restaurant,
			@RequestParam(name = "relatedUrl") String relatedUrl, 
			@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) {
		logger.info("[addProduct] Start");

		ModelAndView model = new ModelAndView("redirect:product");

		int result = productManager.insertProduct(name, address, district, rating, restaurant, relatedUrl, file);

		if (result == 0) {
			redirectAttributes.addFlashAttribute("addResult", false);
			redirectAttributes.addFlashAttribute("name", name);
			redirectAttributes.addFlashAttribute("address", address);
			redirectAttributes.addFlashAttribute("district", district);
			redirectAttributes.addFlashAttribute("rating", rating);
			redirectAttributes.addFlashAttribute("restaurant", restaurant);
			redirectAttributes.addFlashAttribute("relatedUrl", relatedUrl);
		} else {
			redirectAttributes.addFlashAttribute("addResult", true);
		}

		logger.info("[addProduct] End");
		return model;
	}
}
