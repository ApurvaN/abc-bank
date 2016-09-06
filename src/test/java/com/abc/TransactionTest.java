package com.abc;

import org.junit.Test;

import com.abc.Transaction.TransactionType;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5,TransactionType.DEPOSIT);
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void transactionDeposit() {
        Transaction t = new Transaction(5,TransactionType.DEPOSIT);
        assertTrue(t.getTransactionType()==TransactionType.DEPOSIT);
    }
    
    @Test
    public void transactionWithdraw() {
        Transaction t = new Transaction(5,TransactionType.WITHDRAW);
        assertTrue(t.getTransactionType()==TransactionType.WITHDRAW);
    }
    
}
