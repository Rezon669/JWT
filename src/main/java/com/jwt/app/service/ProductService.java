package com.jwt.app.service;

import java.util.List;

import com.jwt.app.entity.Product;
import com.jwt.app.exception.CustomException;

public interface ProductService {

	public String addProducts(Product product) throws CustomException;

	public List<Product> getProducts() throws CustomException;

	public List<Product> searchProduct(String searchkeyword) throws CustomException;
}
