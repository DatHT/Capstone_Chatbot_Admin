/**
 *
 */
package com.psib.controller;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.psib.common.JsonParser;
import com.psib.model.District;
import com.psib.service.IProductManager;

/**
 * @author DatHT Jun 4, 2016
 */
@Controller
public class ProductController {

    private static final Logger LOG = Logger.getLogger(ProductController.class);

	private static final String ERROR = null;

    @Autowired
    private IProductManager productManager;

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ModelAndView loadProduct(@ModelAttribute(value = "addResult") String addResult,
                                    @ModelAttribute(value = "name") String name, @ModelAttribute(value = "address") String address,
                                    @ModelAttribute(value = "district") String district, @ModelAttribute(value = "rating") String rating,
                                    @ModelAttribute(value = "restaurant") String restaurant,
                                    @ModelAttribute(value = "relatedUrl") String relatedUrl,
                                    @RequestParam(value = "txtDistrict", required = false) String txtDistrict,
                                    @RequestParam(value = "txtFood", required = false) String txtFood) {
        LOG.info("[loadProduct] Start");

        ModelAndView model = new ModelAndView("product");

        List<District> districtList = productManager.getAllDistrict();
        Collections.sort(districtList);

        model.addObject("districtList", districtList);
        if (addResult.equals("")) {
            model.addObject("addResult", "1st load");
        } else {
            model.addObject(addResult);
        }
        District dis = null;
        if (txtDistrict != null && txtFood != null) {
        	dis = productManager.getDistrict(txtDistrict);
		}
        if (dis != null) {
        	model.addObject("name", txtFood);
        	model.addObject("districtId", dis.getId());
		} else {
			model.addObject("districtId", district);
			model.addObject(name);
		}
        model.addObject(address);
        model.addObject(rating);
        model.addObject(restaurant);
        model.addObject(relatedUrl);

        LOG.info("[loadProduct] End");
        return model;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addProduct(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "address") String address, @RequestParam(name = "district") String district,
                                   @RequestParam(name = "rating") String rating, @RequestParam(name = "restaurant") String restaurant,
                                   @RequestParam(name = "relatedUrl") String relatedUrl,
                                   @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        LOG.info("[addProduct] Start");
        ModelAndView model = new ModelAndView("redirect:product");
        try {
            

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

            LOG.info("[addProduct] End");
		} catch (Exception e) {
			//model.addAttribute(ERROR, e.getMessage());
		}
		return model;

    }

    @RequestMapping(value = "/loadProduct", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadProduct(@RequestParam(name = "current") int current,
                              @RequestParam(name = "rowCount") int rowCount,
                              @RequestParam(name = "searchPhrase") String searchPhrase,
                              @RequestParam(name = "sort[productName]", required = false) String sortProductName,
                              @RequestParam(name = "sort[addressName]", required = false) String sortAddressName,
                              @RequestParam(name = "sort[districtName]", required = false) String sortDistrictName,
                              @RequestParam(name = "sort[rate]", required = false) String sortRate,
                              @RequestParam(name = "sort[restaurantName]", required = false) String sortRestaurantName) {
        return JsonParser.toJson(productManager.getAllForPaging(current, rowCount, searchPhrase,
                sortProductName, sortAddressName, sortDistrictName, sortRate, sortRestaurantName));
    }
}
