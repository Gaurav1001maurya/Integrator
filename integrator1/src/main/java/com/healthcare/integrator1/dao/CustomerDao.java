package com.healthcare.integrator1.dao;

import com.healthcare.model.Customer;

public interface CustomerDao {
	
	public String add_customer(Customer customer);

	void deleteCustomerById(String customerId);

	Customer getCustomerById(String customerId);
}
