package com.healthcare.integrator1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SortNatural;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table
public class Transaction implements Comparable {
	@Id
	@GenericGenerator(name = "transactionId", strategy = "com.healthcare.customkey.TransactionIdGenerator")
	@GeneratedValue(generator = "transactionId") 
	private String transactionId;
	@Temporal(TemporalType.DATE)
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@SortNatural
	private Date transactionDate;
	private String customerName;
	private long invoiceNumber;
	private double totalAmount;
	private double billAmount;
	private double taxAmount;
	private double profit;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="customerId") 
	 private Customer customer;
	 
	
	public Transaction() {}


	public Transaction(Date transactionDate, String customerName, long invoiceNumber, double totalAmount,
			double billAmount, double taxAmount, double profit) {
		this.transactionDate = transactionDate;
		this.customerName = customerName;
		this.invoiceNumber = invoiceNumber;
		this.totalAmount = totalAmount;
		this.billAmount = billAmount;
		this.taxAmount = taxAmount;
		this.profit = profit;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public Date getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public long getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public double getBillAmount() {
		return billAmount;
	}


	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}


	public double getTaxAmount() {
		return taxAmount;
	}


	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
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


	 @Override
	  public int compareTo(Object obj) {
		 
		      Transaction tx  = (Transaction)obj;
			  return getTransactionDate().compareTo(tx.getTransactionDate());
		 
		 
		
	    
	  }

	
	
	
	
	
	
	
	
	

}
