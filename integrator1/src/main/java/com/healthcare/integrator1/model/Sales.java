package com.healthcare.integrator1.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

	@Entity
	@Table
	public class Sales {
	
	@Id
	@GenericGenerator(name = "salesId", strategy = "com.healthcare.customkey.SalesIdGenerator")
	@GeneratedValue(generator = "salesId")
	private String salesId;
	private String productName;
	private String genericName;
	private double price;
	private int quantity;
	private double amount;
	private double profit;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customerId")
	private Customer customer;
	
	public Sales() {}

	public Sales(String productName, String genericName, double price, int quantity, double amount, double profit) {
		
		this.productName = productName;
		this.genericName = genericName;
		this.price = price;
		this.quantity = quantity;
		this.amount = amount;
		this.profit = profit;
	}

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String saleId) {
		this.salesId = saleId;
	}
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}

