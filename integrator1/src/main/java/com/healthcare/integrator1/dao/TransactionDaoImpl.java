package com.healthcare.integrator1.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.healthcare.model.Customer;
import com.healthcare.model.Transaction;

@Repository
public class TransactionDaoImpl implements TransactionDao {
	
	@Autowired
	HibernateTemplate htemp;
	
	@Override
	public long getNextInvoiceNumber() {
		Long invoiceNumber;
		SQLQuery query = htemp.getSessionFactory()
								.getCurrentSession()
								.createSQLQuery("select invoiceNumber from Transaction order by invoiceNumber desc limit 1");
		if(query.uniqueResult()!=null) {
				invoiceNumber = (Long) query.addScalar("invoiceNumber", StandardBasicTypes.LONG).uniqueResult();
				return invoiceNumber+1;
		}
		else {
			return (long)101;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getTransactionByDate(Date from ,Date to) {
		Query query =  htemp.getSessionFactory()
								.getCurrentSession()
								.createQuery("from Transaction tx where tx.transactionDate between :from and :to");
				query.setParameter("from",from);
				query.setParameter("to",to);
				List<Transaction> list = (List<Transaction>) query.list();
				return list;
		
	}
	
	@Override
	public void addTransaction(Transaction transaction,String customerId) {
		
		Customer customer = htemp.load(Customer.class,customerId);
		transaction.setCustomer(customer);
		htemp.save(transaction);
		
	}
	
}
