package com.healthcare.integrator1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Product {
	@Id
	@GenericGenerator(name = "productId", strategy = "com.healthcare.customkey.ProductIdGenerator")
	@GeneratedValue(generator = "productId")
	private String productId;
	private String brandName;
	private String genericName;
	private String formulation;
	private String indication;
	private String arrivalDate;
	@Temporal(TemporalType.DATE)
	private Date expiryDate;
	private double sellingPrice;
	private double originalPrice;
	private double profit;
	private String supplier;
	private int quantity;
	
	public Product() {}


	public Product(String brandName, String genericName, String formulation, String indication,
			String arrivalDate, Date expiryDate, double sellingPrice, double originalPrice, double profit,
			String supplier, int quantity) {
		super();
		this.brandName = brandName;
		this.genericName = genericName;
		this.formulation = formulation;
		this.indication = indication;
		this.arrivalDate = arrivalDate;
		this.expiryDate = expiryDate;
		this.sellingPrice = sellingPrice;
		this.originalPrice = originalPrice;
		this.profit = profit;
		this.supplier = supplier;
		this.quantity = quantity;
	}
	

	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getFormulation() {
		return formulation;
	}


	public void setFormulation(String formulation) {
		this.formulation = formulation;
	}


	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
