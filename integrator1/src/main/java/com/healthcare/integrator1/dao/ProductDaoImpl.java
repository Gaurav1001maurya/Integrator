package com.healthcare.integrator1.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.healthcare.model.Product;
import com.healthcare.to.ProductTO;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	HibernateTemplate htemp;
	
	@Override
	public void addProduct(Product product) {
		htemp.save(product);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProduct() {
		List<Product> list = (List<Product>) htemp.find("from Product");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllProductName() {
		List<String> list =  (List<String>) htemp.find("select brandName from Product");
		return list;
	}
	
	@Override
	public void deleteProductById(String productId) {
		Product product = htemp.get(Product.class,productId);
		htemp.delete(product);
	}
	

	@Override
	public void updateProductById(ProductTO productto, String productId) {
		
		Product product = htemp.get(Product.class, productId);
		product.setBrandName(productto.getBrandName());
		product.setGenericName(productto.getGenericName());
		product.setFormulation(productto.getFormulation());
		product.setIndication(productto.getIndication());
		product.setArrivalDate(productto.getArrivalDate());
		product.setExpiryDate(productto.getExpiryDate());
		product.setSellingPrice(productto.getSellingPrice());
		product.setOriginalPrice(productto.getOriginalPrice());
		product.setProfit(productto.getProfit());
		product.setSupplier(productto.getSupplier());
		product.setQuantity(productto.getQuantity());
		htemp.save(product);
		
	}	


}
