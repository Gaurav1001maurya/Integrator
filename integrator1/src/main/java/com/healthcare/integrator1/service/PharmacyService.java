package com.healthcare.integrator1.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.healthcare.model.Customer;
import com.healthcare.model.Product;
import com.healthcare.model.Sales;
import com.healthcare.model.Transaction;
import com.healthcare.to.CustomerTO;
import com.healthcare.to.ProductTO;

public interface PharmacyService {
	
	public void addProduct(ProductTO productto);
	
	public List<Product> getAllProduct();
	
	public void deleteProductById(String productId);
	
	public void updateProductById(ProductTO productto, String productId);
	
	public String add_customer(CustomerTO customerto);

	List<Sales> getSalesByCustomerId(String customerId);

	public void add_sales(String customerId, String brandName, int quantity);

	public void deleteSalesBySalesId(String salesId);

	public List<String> getAllProductName();

	public void deleteCustomerById(String customerId);

	public Customer getCustomerById(String customerId);

	public void deleteSalesByCustomerId(String customerId);



	public void addTransaction(Customer customer, List<Sales> sales, double totalAmount, double taxAmount, double billAmount,
			double totalProfit) throws ParseException;

	List<Transaction> getTransactionByDate(Date from, Date to);
}
