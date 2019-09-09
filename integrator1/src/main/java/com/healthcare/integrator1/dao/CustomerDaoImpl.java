package com.healthcare.integrator1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.healthcare.model.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	HibernateTemplate htemp;
	
	@Override
	public String  add_customer(Customer customer) {
		String customerId = (String) htemp.save(customer);
		return customerId;
	}
		
	@Override
	public void  deleteCustomerById(String  customerId) {
		Customer cust = htemp.get(Customer.class,customerId);
		htemp.delete(cust);
	}
	
	@Override
	public Customer   getCustomerById(String  customerId) {
		Customer cust = htemp.get(Customer.class,customerId);
		return cust;
		
	}
}
