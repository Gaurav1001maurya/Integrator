package com.healthcare.integrator1.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.healthcare.model.Customer;
import com.healthcare.model.Product;
import com.healthcare.model.Sales;

@Repository
public class SalesDaoImpl implements SalesDao {
	
	@Autowired
	HibernateTemplate htemp;
	
	@Override
	public void add_sales(String customerId,String brandName,int quantity) {
		Customer cust = htemp.load(Customer.class, customerId);
		Session session = htemp.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from Product where brandName=:brandName");
		query.setParameter("brandName",brandName);
		Product product = (Product) query.uniqueResult();
		double amount = product.getSellingPrice()*quantity;
		Sales sales = new Sales(product.getBrandName(),product.getGenericName(),product.getSellingPrice(),quantity,amount,product.getProfit());
		sales.setCustomer(cust);
		htemp.save(sales);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sales> getSalesByCustomerId(String customerId){
			List<Sales> list = (List<Sales>) htemp.find("from Sales where customerId=?",customerId);
			return list;
	}
	
	@Override
	public void  deleteSalesBySalesId(String salesId){
		Sales sales = htemp.get(Sales.class,salesId);
		htemp.delete(sales);
	}
	
	@Override
	public void  deleteSalesByCustomerId(String customerId){

			htemp.getSessionFactory().getCurrentSession()
			.createQuery("delete from Sales where customerId=:customerId")
			.setParameter("customerId",customerId)
			.executeUpdate();
	}

}
