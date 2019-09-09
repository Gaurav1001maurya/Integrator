package com.healthcare.integrator1.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcare.dao.CustomerDao;
import com.healthcare.dao.ProductDao;
import com.healthcare.dao.SalesDao;
import com.healthcare.dao.TransactionDao;
import com.healthcare.model.Customer;
import com.healthcare.model.Product;
import com.healthcare.model.Sales;
import com.healthcare.model.Transaction;
import com.healthcare.to.CustomerTO;
import com.healthcare.to.ProductTO;

	
@Service
public class PharmacyServiceImpl implements PharmacyService {
	
	@Autowired 
	ProductDao productDao;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	SalesDao salesDao;
	
	@Autowired
	TransactionDao transactionDao;
	
	
	@Transactional(readOnly=false)
	@Override
	public void addProduct(ProductTO productto) {
		Product  product = new Product(productto.getBrandName(),productto.getGenericName(),productto.getFormulation(),
				productto.getIndication(),productto.getArrivalDate(),productto.getExpiryDate(),productto.getSellingPrice()
				,productto.getOriginalPrice(),	productto.getProfit(),productto.getSupplier(),productto.getQuantity());
	
		productDao.addProduct(product);
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<Product> getAllProduct() {
		List<Product> list = productDao.getAllProduct();
		return list;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<String> getAllProductName() {
		List<String> list = productDao.getAllProductName();
		return list;
	}
	
	@Transactional(readOnly=false)
	@Override
	public void deleteProductById(String productId) {
		productDao.deleteProductById(productId);
	}
	@Transactional(readOnly=false)
	@Override
	public void updateProductById(ProductTO productto, String productId) {
		
		productDao.updateProductById(productto, productId);
		
	}

	@Override
	@Transactional(readOnly=false)
	public String add_customer(CustomerTO customerto) {
		Customer cust = new Customer(customerto.getCustomerName(),customerto.getPhone());
		String customerId = customerDao.add_customer(cust);
		return customerId;
	}	
	
	@Override
	@Transactional(readOnly=true)
	public Customer   getCustomerById(String  customerId) {
			return customerDao.getCustomerById(customerId);
		
	}
	
	@Override
	@Transactional(readOnly=false)
	public void  deleteCustomerById(String  customerId) {
		customerDao.deleteCustomerById(customerId);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void add_sales(String customerId,String brandName,int quantity) {
		salesDao.add_sales(customerId, brandName, quantity);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void  deleteSalesByCustomerId(String customerId){
				salesDao.deleteSalesByCustomerId(customerId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Sales> getSalesByCustomerId(String customerId){
		List<Sales> list = salesDao.getSalesByCustomerId(customerId);
		return list;
}
	

	@Override
	@Transactional(readOnly=false)
	public void  deleteSalesBySalesId(String salesId){
		salesDao.deleteSalesBySalesId(salesId);
	}
	
	
	@Transactional(readOnly=false)
	@Override
	public void addTransaction(Customer customer,List<Sales> sales,double totalAmount,double taxAmount,double billAmount,double totalProfit) throws ParseException {
		
		
		long invoiceNumber = transactionDao.getNextInvoiceNumber();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date= sdf.parse(sdf.format(new Date()));
		Transaction transaction = new Transaction(date,customer.getCustomerName(),invoiceNumber,totalAmount,billAmount,taxAmount,totalProfit);
		transactionDao.addTransaction(transaction, customer.getCustomerId());
		
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Transaction> getTransactionByDate(Date from ,Date to) {
		List<Transaction> list = transactionDao.getTransactionByDate(from, to);
		return list;
		
	}
	
	

}
