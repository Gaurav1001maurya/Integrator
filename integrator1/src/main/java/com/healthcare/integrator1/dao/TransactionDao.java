package com.healthcare.integrator1.dao;

import java.util.Date;
import java.util.List;

import com.healthcare.model.Transaction;

public interface TransactionDao {


	public void addTransaction(Transaction transaction, String CustomerId);

	public long getNextInvoiceNumber();

	List<Transaction> getTransactionByDate(Date from, Date to);

}
