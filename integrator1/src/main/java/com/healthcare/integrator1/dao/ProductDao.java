package com.healthcare.integrator1.dao;

import java.util.List;

import com.healthcare.model.Product;
import com.healthcare.to.ProductTO;

public interface ProductDao {
	
	public void addProduct(Product product);

	List<Product> getAllProduct();

	void deleteProductById(String productId);

	void updateProductById(ProductTO productto, String productId);

	List<String> getAllProductName();
		
}
