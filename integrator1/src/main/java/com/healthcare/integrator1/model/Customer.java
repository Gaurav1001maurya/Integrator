package com.healthcare.integrator1.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Customer {
	
	@Id
	@GenericGenerator(name = "customerId", strategy = "com.healthcare.customkey.CustomerIdGenerator")
	@GeneratedValue(generator = "customerId") 
	private String customerId;
	private String customerName;
	private String phone;
	
	@OneToMany(mappedBy ="customer")
	private Set<Sales> sales;
	
	@OneToMany(mappedBy ="customer")
	private Set<Transaction> transaction;
	
	
	public Customer() {}

	public Customer(String customerName, String phone) {
		
		this.customerName = customerName;
		this.phone = phone;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Sales> getSales() {
		return sales;
	}

	public void setSales(Set<Sales> sales) {
		this.sales = sales;
	}

	public Set<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(Set<Transaction> transaction) {
		this.transaction = transaction;
	}
	
	
	
	
}
