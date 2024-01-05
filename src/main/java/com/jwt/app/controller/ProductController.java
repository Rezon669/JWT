package com.jwt.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jwt.app.entity.Product;
import com.jwt.app.exception.CustomException;
import com.jwt.app.repository.ProductRepository;
import com.jwt.app.service.ProductService;
import com.jwt.app.serviceimpl.ProductServiceImpl;

@Controller
//@RequestMapping("/jwt/app/secure")
public class ProductController {

	private static final Logger logger = LogManager.getLogger(ProductController.class);
	@Autowired
	ProductRepository productRepository;
	@Autowired
	private ProductService productServiceImpl;

	@GetMapping("/jwt/product/admin/addproducts")
	public String addProduct() {

		return "product";
	}

	@GetMapping("/jwt/product/public/homepage")
	public String home() {

		return "welcome";
	}

	@PostMapping("/jwt/product/admin/addproduct")
	// @PreAuthorize("hasRole('ADMIN')")
	public String addProduct(Product product, Model model) {
		try {
			productServiceImpl.addProducts(product);
			logger.info("Product details are added");
			return "thankyou";
		} catch (CustomException e) {

			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);

			return "product";

		}

	}

	@GetMapping("/jwt/product/public/getproducts")
	// @PreAuthorize("hasRole('ADMIN')")
	public ModelAndView getProducts(Model model) {
		try {
			List<Product> list = productServiceImpl.getProducts();
			ModelAndView mv = new ModelAndView("listofproducts");
			mv.addObject("list", list);
			mv.setViewName("listofproducts");
			return mv;
		} catch (CustomException e) {
			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			ModelAndView mv = new ModelAndView("listofproducts");
			mv.addObject("list", "");
			mv.setViewName("listofproducts");
			return mv;

		}

	}

	@GetMapping("/jwt/product/public/searchproduct")
	public String searchProduct() {

		return "searchproduct";

	}

	/*
	 * @GetMapping("/public/searchproducts") public ModelAndView
	 * searchProducts(Model model, @Param("searchkeyword") String searchkeyword) {
	 * 
	 * try { List<Product> list = productServiceImpl.searchProduct(searchkeyword);
	 * ModelAndView mv = new ModelAndView("listofproducts"); mv.addObject("list",
	 * list); mv.setViewName("listofproducts"); return mv; } catch (CustomException
	 * e) { model.addAttribute("errorMessage", e.getMessage()); logger.error(e);
	 * ModelAndView mv = new ModelAndView("listofproducts"); mv.addObject("list",
	 * ""); mv.setViewName("listofproducts"); return mv;
	 * 
	 * }
	 */
	@GetMapping("/jwt/product/public/searchproducts")
	public ModelAndView searchProducts(Model model, @Param("searchkeyword") String searchkeyword) {
		try {
			List<Product> list = productServiceImpl.searchProduct(searchkeyword);
			ModelAndView mv = new ModelAndView("listofproducts");

			if (list.isEmpty()) {
				model.addAttribute("errorMessage", "No products found!");
				mv.addObject("list", new ArrayList<>()); // Provide an empty list
			} else {
				model.addAttribute("errorMessage", ""); // Clear error message if the list is not empty
				mv.addObject("list", list);
			}

			return mv;
		} catch (CustomException e) {
			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			ModelAndView mv = new ModelAndView("listofproducts");
			mv.addObject("list", new ArrayList<>()); // Provide an empty list in case of exception
			return mv;
		}

	}
}