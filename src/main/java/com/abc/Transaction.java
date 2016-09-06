package com.abc;

import java.util.Date;

import com.abc.Utils.DateProvider;

public class Transaction {
    private final double amount;
    
  

   enum TransactionType{
	   DEPOSIT,WITHDRAW
    }

    private Date transactionDate;
    private TransactionType transactionType;

    public Date getTransactionDate() {
		return transactionDate;
	}
    
    public double getAmount() {
		return amount;
	}
    
	public TransactionType getTransactionType() {
		return transactionType;
	}

	public Transaction(double amount,TransactionType type) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType=type;
    }


	
}
