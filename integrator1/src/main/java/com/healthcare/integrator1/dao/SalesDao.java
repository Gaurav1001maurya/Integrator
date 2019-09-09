package com.healthcare.integrator1.dao;
	
import java.util.List;
	
import com.healthcare.model.Sales;
	
   public interface SalesDao {
	
	
	void add_sales(String customerId, String brandName, int quantity);
	
	List<Sales> getSalesByCustomerId(String customerId);

	void deleteSalesBySalesId(String salesId);

	void deleteSalesByCustomerId(String customerId);
	
	}
