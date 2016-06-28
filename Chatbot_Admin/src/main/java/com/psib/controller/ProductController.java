/**
 *
 */
package com.psib.controller;

import com.psib.model.District;
import com.psib.model.ProductAddress;
import com.psib.service.IProductManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.annotation.MultipartConfig;
import java.util.Collections;
import java.util.List;

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
                                    @ModelAttribute(value = "relatedUrl") String relatedUrl) {
        LOG.info("[loadProduct] Start");

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
}
