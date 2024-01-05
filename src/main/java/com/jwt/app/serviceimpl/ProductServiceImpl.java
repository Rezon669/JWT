package com.jwt.app.serviceimpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.app.entity.Product;
import com.jwt.app.exception.CustomException;
import com.jwt.app.repository.ProductRepository;
import com.jwt.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
	@Autowired
	ProductRepository productRepository;

	public String addProducts(Product product) throws CustomException {
		if (product.getProductname().isEmpty() || product.getCategory().isEmpty()
				|| product.getSearchkeyword().isEmpty() || product.getPrice() <= 0 || product.getQuantity() <= 0) {
			logger.warn("All the fields are mandatory fields");
			throw new CustomException("All the fields are mandatory fields");
		} else if (product.getCategory().equalsIgnoreCase("select one")) {
			throw new CustomException("Please select the category type");
		}

		productRepository.save(product);
		return "Product Details are added";

	}

	public List<Product> getProducts() throws CustomException {

		List<Product> list = productRepository.findAll();
		if (list.isEmpty()) {
			throw new CustomException("No Products are found");
		} else {
			return list;
		}
	}

	public List<Product> searchProduct(String searchkeyword) throws CustomException {
		List<Product> list = productRepository.search(searchkeyword);
		if (list.isEmpty()) {
			throw new CustomException("No Products are found with the given search keyword");
		} else {
			return list;
		}

	}

}
